package cn.northpark.test.study;

import java.sql.*;

/**
 * @author bruce
 * @date 2024年05月31日 19:33:47
 */
public class PersonTag {
    private static final String DATABASE_NAME = "day77";//TODO 1 数据库名

    //TODO 2 检查中高级的名称是否变动

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/" + DATABASE_NAME + "?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=UTC";
        String username = "root";
        String password = "123456";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);

            // 查询高级人数
            String highQuery = "SELECT COUNT(1) FROM user_info WHERE job IN ('企划经理','销售经理','总工程师') AND uid IN (SELECT uid FROM v_hd);";
            int highCount = executeCountQuery(connection, highQuery);
            System.out.println("高级人数: " + highCount);

            // 查询中级人数
            String midQuery = "SELECT COUNT(1) FROM user_info WHERE job IN ('企划主管','销售主管','工程师') AND uid IN (SELECT uid FROM v_hd);";
            int midCount = executeCountQuery(connection, midQuery);
            System.out.println("中级人数: " + midCount);

            // 查询低级人数
            String lowQuery = "SELECT COUNT(1) FROM user_info WHERE job IN ('企划专员','销售业务员','技术员') AND uid IN (SELECT uid FROM v_hd);";
            int lowCount = executeCountQuery(connection, lowQuery);
            System.out.println("低级人数: " + lowCount);

            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private static int executeCountQuery(Connection connection, String query) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }
}
