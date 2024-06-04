package cn.northpark.test.study;

import lombok.extern.slf4j.Slf4j;

import java.sql.*;

/**
 * @author bruce
 * @date 2024年05月31日 19:33:47
 *
 *----------------------------------------------------------------------
 *
 *
 * -- 读书类查询
 * select uid,name,count(uid) as nums from user_reading
 * where tag= '科技科普'
 * and uid in (SELECT uid from v_hd)
 * GROUP BY uid,name
 *
 * -- 活动类查询
 * select uid,name,count(uid) as nums from user_event
 * where event_type = '公益'
 * and uid in (SELECT uid from v_hd)
 * GROUP BY uid,name
 *
 * -- 社区类查询
 *
 * select uid,name,count(uid) as nums from user_sq
 * where sq_type = '游戏'
 * and uid in (SELECT uid from v_hd)
 * GROUP BY uid,name
 *
 */
@Slf4j
public class T0 {
    private static final String DATABASE_NAME = "dd1";//TODO 1 数据库名
    private static final String V_NAME = "v_hdhd";      //TODO 2 视图名

    //TODO 3 视图查询语句
    private static final String selectSQL =
            "select uid,name,count(uid) as nums from user_reading\n" +
                    "where tag= '科技科普'\n" +
                    "and uid in (SELECT uid from v_hd)\n" +
                    "GROUP BY uid,name";

    private static final String kpiName = "读书-科技科普 T0 活动互动\n\n";//TODO  4 指标名称

    //TODO 5 检查T6~ T1的左右数值区间

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/" + DATABASE_NAME + "?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=UTC";
        String username = "root";
        String password = "123456";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);

            // 创建视图 v_qdtg
            createView(connection, V_NAME);

            // 查询各个级别人数并打印结果

            int t6Count = getLevelCount(connection, V_NAME, 12, 13);

            int t5Count = getLevelCount(connection, V_NAME, 10, 11);

            int t4Count = getLevelCount(connection, V_NAME, 8, 9);

            int t3Count = getLevelCount(connection, V_NAME, 6, 7);

            int t2Count = getLevelCount(connection, V_NAME, 4, 5);

            int t1Count = getLevelCount(connection, V_NAME, 2, 3);

            int t0Count = getT0Count(connection, t6Count, t5Count, t4Count, t3Count, t2Count, t1Count);


            System.out.println(kpiName);
            System.out.println("=====================================");
            System.out.println("T6人数: " + t6Count);
            System.out.println("T5人数: " + t5Count);
            System.out.println("T4人数: " + t4Count);
            System.out.println("T3人数: " + t3Count);
            System.out.println("T2人数: " + t2Count);
            System.out.println("T1人数: " + t1Count);
            System.out.println("T0人数: " + t0Count);



            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createView(Connection connection, String viewName) throws SQLException {
        String createViewQuery = "CREATE VIEW " + viewName + " AS " + selectSQL;

        log.info("createViewQuerySQL===【\n{}{}",createViewQuery,";\n】");
        executeUpdate(connection, createViewQuery);
    }

    private static int getLevelCount(Connection connection, String viewName, int lowerBound, int upperBound) throws SQLException {
        String query = "SELECT COUNT(uid) FROM " + viewName + " WHERE nums >= " + lowerBound + " AND nums <= " + upperBound;

        log.info("querySQL===【{}{}",query,";】");
        return executeCountQuery(connection, query);
    }

    private static int getT0Count(Connection connection, int t6Count, int t5Count, int t4Count, int t3Count, int t2Count, int t1Count) throws SQLException {
        String query = "SELECT (COUNT(DISTINCT uid) - " + t6Count + " - " + t5Count + " - " + t4Count + " - " + t3Count + " - " + t2Count + " - " + t1Count + ") AS T0 FROM v_hd";

        log.info("queryT0CountSQL===【{}{}",query,";】");
        return executeCountQuery(connection, query);
    }

    private static int executeCountQuery(Connection connection, String query) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }

    private static int executeUpdate(Connection connection, String query) throws SQLException {
        Statement statement = connection.createStatement();
        return statement.executeUpdate(query);
    }
}
