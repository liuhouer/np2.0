package cn.northpark.test.study;

import lombok.extern.slf4j.Slf4j;

import java.sql.*;

/**
 * @author bruce
 * @date 2024年05月31日 19:33:47
 * ----------------
 * 用户统计字段对应
 * ----------------
 * 姓名	name
 * 性别	gender
 * 现居地址	address
 * 职业	job
 * 关注了N人	follows
 * 被N人关注	followers
 * 发布动态数	pubs
 * 获赞数	likers
 * 回应数	resps
 * 影评数	mov_cmts
 * 书评数	book_cmts
 * 对N本书感兴趣	book_inter
 * 组织活动数	org_acts
 * 参与活动数	part_acts
 * 读书数	readings
 * 加入社区数	sq_joins
 * -------------------
 */
@Slf4j
public class T2 {
    private static final String DATABASE_NAME = "dd1";  //TODO 1 数据库名
    private static final String VIEW_NAME = "v_qdtg";    //TODO 2 视图名称
    private static final String FIELD_NAME = "followers";   //TODO 3 统计字段名称

    //TODO 4 修改 T6 ~ T1的左右区间

    private static final String kpiName = "获得关注数 T2 渠道推广";//TODO  5 指标名称

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/" + DATABASE_NAME + "?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=UTC";
        String username = "root";
        String password = "123456";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement()) {

            // 创建视图
            String createViewQuery = "CREATE VIEW " + VIEW_NAME + " AS SELECT uid, " + FIELD_NAME + " AS nums FROM user_info WHERE uid IN (SELECT uid FROM v_hd)";

            log.info("createViewQuerySQL===【\n{}{}",createViewQuery,";\n】");
            statement.executeUpdate(createViewQuery);

            //0~90 91~181 182~272 273~363 364~454 455~545 546~636
            // 统计各个级别的人数
            int t6Count = getCount(statement, VIEW_NAME, 546, 636);
            int t5Count = getCount(statement, VIEW_NAME, 455, 545);
            int t4Count = getCount(statement, VIEW_NAME, 364, 454);
            int t3Count = getCount(statement, VIEW_NAME, 273, 363);
            int t2Count = getCount(statement, VIEW_NAME, 182, 272);
            int t1Count = getCount(statement, VIEW_NAME, 91 , 181);

            // 计算T0
            int t0Count = getTotalCount(statement, "v_hd") - t6Count - t5Count - t4Count - t3Count - t2Count - t1Count;

            // 打印结果
            // 打印结果
            System.out.println(kpiName);
            System.out.println("=====================================");
            System.out.println("T6: " + t6Count);
            System.out.println("T5: " + t5Count);
            System.out.println("T4: " + t4Count);
            System.out.println("T3: " + t3Count);
            System.out.println("T2: " + t2Count);
            System.out.println("T1: " + t1Count);
            System.out.println("T0: " + t0Count);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static int getCount(Statement statement, String viewName, int lowerBound, int upperBound) throws SQLException {
        String query = "SELECT COUNT(uid) FROM " + viewName + " WHERE nums >= " + lowerBound + " AND nums <= " + upperBound;
        log.info("querySQL===【{}{}",query,";】");
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();
        return resultSet.getInt(1);
    }

    private static int getTotalCount(Statement statement, String viewName) throws SQLException {
        String query = "SELECT COUNT(DISTINCT uid) FROM " + viewName;
        log.info("queryTotalSQL===【{}{}",query,";】");
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();
        return resultSet.getInt(1);
    }
}
