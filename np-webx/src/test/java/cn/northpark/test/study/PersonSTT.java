package cn.northpark.test.study;

import java.sql.*;

/**
 * @author bruce
 * @date 2024年05月31日 19:33:47
 */
public class PersonSTT {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/day77?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=UTC";
        String username = "root";
        String password = "123456";


        String keyword1 = "嗨购节";
        String keyword2 = "亿佳惠";
        String keyword3 = "达西Darcy";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);

            // 创建视图
            String createViewQuery = "create view v_hd as ( " +
                    " select t.uid as uid FROM user_sq as t " +
                    " LEFT JOIN user_sq as t1 ON t.uid=t1.uid " +
                    " LEFT JOIN user_sq as t2 ON t.uid=t2.uid " +
                    " where " +
                    " t.sq_name like '%" + keyword1 + "%' " +
                    " and " +
                    " t1.sq_name like '%" + keyword2 + "%' " +
                    " and " +
                    " t2.sq_name like '%" + keyword3 + "%' " +
                    " and " +
                    " t.uid in ( " +
                    "     SELECT uid FROM user_info WHERE gender IN ('小雌兔','女','女士','0','female') " +
                    " ) " +
                    " )";
            executeUpdate(connection, createViewQuery);

            // 统计有效人数（去重）
            String countQuery = "select count(distinct uid) from v_hd;";
            ResultSet resultSet4 = executeQuery(connection, countQuery);
            if (resultSet4.next()) {
                int count = resultSet4.getInt(1);
                System.out.println("有效去重人数: " + count);
            }

            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private static ResultSet executeQuery(Connection connection, String query) throws SQLException {
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }

    private static int executeUpdate(Connection connection, String query) throws SQLException {
        Statement statement = connection.createStatement();
        return statement.executeUpdate(query);
    }
}
