package cn.northpark.test.dataclean;

import cn.northpark.utils.EnvCfgUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;

/**
 * 同步最新的需求到本地数据库
 * @author bruce
 * @date 2024年04月12日 16:13:45
 */
@Slf4j
public class FetchReqAndSaveData {
    private static final String URL = EnvCfgUtil.getValByCfgName("REQ_HOST")+"/api/req/reqRequest/queryReqRequestList";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/req?serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "123456";

    public static void main(String[] args) throws Exception{
        int pageNum = 1;
        int pageSize = 20;

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            while (true) {
                String requestUrl = URL + "?pageNum=" + pageNum + "&pageSize=" + pageSize;
                String jsonResponse = sendGetRequest(requestUrl);

                JSONObject jsonObject = JSONObject.parseObject(jsonResponse);
                int total = jsonObject.getIntValue("total");
                JSONArray rows = jsonObject.getJSONArray("rows");

                if (rows.isEmpty()) {
                    break;
                }

                upsertToDatabase(conn, rows);

                if (rows.size() < pageSize) {
                    break;
                }

                pageNum++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static String sendGetRequest(String urlString) throws Exception {
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        return result.toString();
    }


    private static void upsertToDatabase(Connection conn, JSONArray rows) {
        String insertSql = "INSERT INTO requests (reqCode, reqName, reqDesc, businessPurpose, reqAuthorId, reqAuthor, reqAuthorDept, reqProjectId, reqProject, reqProductId, reqProduct, reqType, reqSource, reqSubmitTime, reqEstimatedEffort, reqExpEndTime, reqPriority, reqStatus, reqProductTypeId, reqProductTypeName, reqEvaluatorId, reqEvaluator, reqModule, reqStatusChangeTime, reqCcList, reqCcListName, reqRel, pCode, xmjlId, xmjlName, syncCd, syncRes, syncMsg, cdId, reqUpTime, cdPubVersion, evalPostDict, evalPostId, extInfo, var1, var2, reqOwnerId, reqOwner, publisherId, publisher, endPoint, buDeptName, taskHandleDeptName, satisfaction, showLabel)  " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)";
        String updateSql = "UPDATE requests SET reqName=?, reqDesc=?, businessPurpose=?, reqAuthorId=?, reqAuthor=?, reqAuthorDept=?, reqProjectId=?, reqProject=?, reqProductId=?, reqProduct=?, reqType=?, reqSource=?, reqSubmitTime=?, reqEstimatedEffort=?, reqExpEndTime=?, reqPriority=?, reqStatus=?, reqProductTypeId=?, reqProductTypeName=?, reqEvaluatorId=?, reqEvaluator=?, reqModule=?, reqStatusChangeTime=?, reqCcList=?, reqCcListName=?, reqRel=?, pCode=?, xmjlId=?, xmjlName=?, syncCd=?, syncRes=?, syncMsg=?, cdId=?, reqUpTime=?, cdPubVersion=?, evalPostDict=?, evalPostId=?, extInfo=?, var1=?, var2=?, reqOwnerId=?, reqOwner=?, publisherId=?, publisher=?, endPoint=?, buDeptName=?, taskHandleDeptName=?, satisfaction=?, showLabel=? WHERE reqCode=? " ;

        try (PreparedStatement insertStmt = conn.prepareStatement(insertSql);
             PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
            for (int i = 0; i < rows.size(); i++) {
                JSONObject row = rows.getJSONObject(i);
                String reqCode = row.getString("reqCode");

                try {
                    // Check if the record with the given reqCode already exists
                    String checkSql = "SELECT COUNT(*) FROM requests WHERE reqCode = ?";
                    PreparedStatement checkStmt = conn.prepareStatement(checkSql);
                    checkStmt.setString(1, reqCode);
                    ResultSet rs = checkStmt.executeQuery();
                    rs.next();
                    int count = rs.getInt(1);
                    rs.close();
                    checkStmt.close();

                    //INSERT INTO `requests` (`reqCode`, `reqName`, `reqDesc`, `businessPurpose`, `reqAuthorId`, `reqAuthor`, `reqAuthorDept`, `reqProjectId`, `reqProject`, `reqProductId`, `reqProduct`, `reqType`, `reqSource`, `reqSubmitTime`, `reqEstimatedEffort`, `reqExpEndTime`, `reqPriority`, `reqStatus`, `reqProductTypeId`, `reqProductTypeName`, `reqEvaluatorId`, `reqEvaluator`,




                    //VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)

                    if (count > 0) {
                        // Update the existing record
                       updateStmt.setString(1, row.getString("reqName"));
                       updateStmt.setString(2, row.getString("reqDesc"));
                       updateStmt.setString(3, row.getString("businessPurpose"));
                       updateStmt.setString(4, row.getString("reqAuthorId"));
                       updateStmt.setString(5, row.getString("reqAuthor"));
                       updateStmt.setString(6, row.getString("reqAuthorDept"));
                       updateStmt.setString(7, row.getString("reqProjectId"));
                       updateStmt.setString(8, row.getString("reqProject"));
                       updateStmt.setString(9,  row.getString("reqProductId"));
                       updateStmt.setString(10, row.getString("reqProduct"));
                       updateStmt.setString(11, row.getString("reqType"));
                       updateStmt.setString(12, row.getString("reqSource"));
                       updateStmt.setString(13, row.getString("reqSubmitTime"));
                       updateStmt.setInt   (14, row.getIntValue("reqEstimatedEffort"));
                       updateStmt.setString(15, row.getString("reqExpEndTime"));
                       updateStmt.setString(16, row.getString("reqPriority"));
                       updateStmt.setString(17, row.getString("reqStatus"));
                       updateStmt.setString(18, row.getString("reqProductTypeId"));
                       updateStmt.setString(19, row.getString("reqProductTypeName"));
                       updateStmt.setString(20, row.getString("reqEvaluatorId"));
                       updateStmt.setString(21, row.getString("reqEvaluator"));

                        updateStmt.setString(22, row.getString("reqModule"));
                        updateStmt.setString(23, row.getString("reqStatusChangeTime"));
                        updateStmt.setString(24, row.getString("reqCcList"));
                        updateStmt.setString(25, row.getString("reqCcListName"));
                        updateStmt.setString(26, row.getString("reqRel"));
                        updateStmt.setString(27, row.getString("pCode"));
                        updateStmt.setString(28, row.getString("xmjlId"));
                        updateStmt.setString(29, row.getString("xmjlName"));
                        updateStmt.setString(30, row.getString("syncCd"));
                        updateStmt.setString(31, row.getString("syncRes"));
                        updateStmt.setString(32, row.getString("syncMsg"));
                        updateStmt.setString(33, row.getString("cdId"));
                        updateStmt.setString(34, row.getString("reqUpTime"));
                        updateStmt.setString(35, row.getString("cdPubVersion"));
                        updateStmt.setString(36, row.getString("evalPostDict"));
                        updateStmt.setString(37, row.getString("evalPostId"));
                        updateStmt.setString(38, row.getString("extInfo"));
                        updateStmt.setString(39, row.getString("var1"));
                        updateStmt.setString(40, row.getString("var2"));
                        updateStmt.setString(41, row.getString("reqOwnerId"));
                        updateStmt.setString(42, row.getString("reqOwner"));
                        updateStmt.setString(43, row.getString("publisherId"));
                        updateStmt.setString(44, row.getString("publisher"));
                        updateStmt.setString(45, row.getString("endPoint"));
                        updateStmt.setString(46, row.getString("buDeptName"));
                        updateStmt.setString(47, row.getString("taskHandleDeptName"));
                        updateStmt.setString(48, row.getString("satisfaction"));
                        updateStmt.setString(49, row.getString("showLabel"));

                        updateStmt.setString(50, row.getString("reqCode"));

                       updateStmt.addBatch();
                    } else {
                        // Insert a new record
                        insertStmt.setString(1, row.getString("reqCode"));
                        insertStmt.setString(2, row.getString("reqName"));
                        insertStmt.setString(3, row.getString("reqDesc"));
                        insertStmt.setString(4, row.getString("businessPurpose"));
                        insertStmt.setString(5, row.getString("reqAuthorId"));
                        insertStmt.setString(6, row.getString("reqAuthor"));
                        insertStmt.setString(7, row.getString("reqAuthorDept"));
                        insertStmt.setString(8, row.getString("reqProjectId"));
                        insertStmt.setString(9, row.getString("reqProject"));
                        insertStmt.setString(10, row.getString("reqProductId"));
                        insertStmt.setString(11, row.getString("reqProduct"));
                        insertStmt.setString(12, row.getString("reqType"));
                        insertStmt.setString(13, row.getString("reqSource"));
                        insertStmt.setString(14, row.getString("reqSubmitTime"));
                        insertStmt.setInt(15, row.getIntValue("reqEstimatedEffort"));
                        insertStmt.setString(16, row.getString("reqExpEndTime"));
                        insertStmt.setString(17, row.getString("reqPriority"));
                        insertStmt.setString(18, row.getString("reqStatus"));
                        insertStmt.setString(19, row.getString("reqProductTypeId"));
                        insertStmt.setString(20, row.getString("reqProductTypeName"));
                        insertStmt.setString(21, row.getString("reqEvaluatorId"));
                        insertStmt.setString(22, row.getString("reqEvaluator"));

                        // `reqModule`, `reqStatusChangeTime`, `reqCcList`, `reqCcListName`, `reqRel`, `pCode`,
                        insertStmt.setString(23, row.getString("reqModule"));
                        insertStmt.setString(24, row.getString("reqStatusChangeTime"));
                        insertStmt.setString(25, row.getString("reqCcList"));
                        insertStmt.setString(26, row.getString("reqCcListName"));
                        insertStmt.setString(27, row.getString("reqRel"));
                        insertStmt.setString(28, row.getString("pCode"));
                        // `xmjlId`, `xmjlName`, `syncCd`, `syncRes`, `syncMsg`, `cdId`, `reqUpTime`, `cdPubVersion`,
                        insertStmt.setString(29, row.getString("xmjlId"));
                        insertStmt.setString(30, row.getString("xmjlName"));
                        insertStmt.setString(31, row.getString("syncCd"));
                        insertStmt.setString(32, row.getString("syncRes"));
                        insertStmt.setString(33, row.getString("syncMsg"));
                        insertStmt.setString(34, row.getString("cdId"));
                        insertStmt.setString(35, row.getString("reqUpTime"));
                        insertStmt.setString(36, row.getString("cdPubVersion"));

                        // `evalPostDict`, `evalPostId`, `extInfo`, `var1`, `var2`, `reqOwnerId`, `reqOwner`,
                        insertStmt.setString(37, row.getString("evalPostDict"));
                        insertStmt.setString(38, row.getString("evalPostId"));
                        insertStmt.setString(39, row.getString("extInfo"));
                        insertStmt.setString(40, row.getString("var1"));
                        insertStmt.setString(41, row.getString("var2"));
                        insertStmt.setString(42, row.getString("reqOwnerId"));
                        insertStmt.setString(43, row.getString("reqOwner"));

                        // `publisherId`, `publisher`, `endPoint`, `buDeptName`, `taskHandleDeptName`, `satisfaction`, `showLabel`)
                        insertStmt.setString(44, row.getString("publisherId"));
                        insertStmt.setString(45, row.getString("publisher"));
                        insertStmt.setString(46, row.getString("endPoint"));
                        insertStmt.setString(47, row.getString("buDeptName"));
                        insertStmt.setString(48, row.getString("taskHandleDeptName"));
                        insertStmt.setString(49, row.getString("satisfaction"));
                        insertStmt.setString(50, row.getString("showLabel"));

                        insertStmt.addBatch();
                    }
                } catch (SQLException e) {
                    System.out.println("Error processing record with reqCode: " + reqCode);
                    log.error("Error processing record with reqCode: " + reqCode);
                    e.printStackTrace();
                }
            }

            insertStmt.executeBatch();
            updateStmt.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
