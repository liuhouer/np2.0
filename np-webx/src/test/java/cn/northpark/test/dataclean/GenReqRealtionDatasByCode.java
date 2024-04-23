package cn.northpark.test.dataclean;

import cn.northpark.threadPool.MultiThread;
import cn.northpark.utils.EnvCfgUtil;
import cn.northpark.utils.JsonUtil;
import cn.northpark.utils.NPQuery;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.GenerousBeanProcessor;
import org.apache.commons.dbutils.RowProcessor;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

/**
 * @author bruce
 * @date 2024年04月23日 13:40:02
 */
@Slf4j
public class GenReqRealtionDatasByCode {
    public static void main(String[] args) throws Exception{
        NPQuery nQuery = new NPQuery("local.properties");

        List<Map<String, Object>> lists = nQuery.query("select distinct reqCode from requests", new MapListHandler());

        //log.info("lists,------------------>{}",lists);

        //202404180005

        //202401040021

        MultiThread<Map<String, Object>, Integer> multiThread = new MultiThread<Map<String, Object>, Integer>(lists) {
            @Override
            public Integer outExecute(int currentThread, Map<String, Object> data) {

                System.err.println("currentThread===>"+currentThread);
                System.err.println("req id ===>"+data);

                String reqCode = data.get("reqCode")==null?"":data.get("reqCode").toString();
                if(StringUtils.isNotBlank(reqCode)){
                    try {
                        //处理需求分发 &任务列表
                        handleDistsTasks(nQuery, reqCode);

                        Thread.sleep(200);

                        //处理需求发布
                        handlePub(nQuery, reqCode);

                        Thread.sleep(200);

                        //需求验证
                        handleValidation(nQuery, reqCode);

                        Thread.sleep(200);

                        //需求关闭
                        handleClosed(nQuery, reqCode);

                        Thread.sleep(200);

                        //获取需求变更列表
                        handleChangeLog(nQuery, reqCode);

                        Thread.sleep(200);

                        //获取需求评论
                        handleComment(nQuery, reqCode);

                        Thread.sleep(200);

                        //获取需求评估
                        handleEV(nQuery, reqCode);
                    }catch ( Exception e){
                        e.printStackTrace();
                    }

                }

                return currentThread;
            }
        };

        try {
            System.err.println("线程"+multiThread.getResult()+"正在执行");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }



        //处理需求分发 &任务列表
        //handleDistsTasks(nQuery, reqCode);

        //处理需求发布
        //handlePub(nQuery, reqCode);

        //需求验证
        //handleValidation(nQuery, reqCode);

        //需求关闭
        //handleClosed(nQuery, reqCode);

        //获取需求变更列表
        //handleChangeLog(nQuery, reqCode);

        //获取需求评论
        //handleComment(nQuery, reqCode);

        //获取需求评估
        //handleEV(nQuery, reqCode);


    }

    /**
     * 需求评估
     * @param nQuery
     * @param reqCode
     * @throws Exception
     */
    private static void handleEV(NPQuery nQuery, String reqCode) throws Exception {
        String evUrl = EnvCfgUtil.getValByCfgName("REQ_HOST")+ "/api/req/reqRequest/queryReqEvaluation?reqCode="+ reqCode;
        String jsonResponse = sendGetRequest(evUrl);

        JSONObject jsonObject = JSONObject.parseObject(jsonResponse);
        int code = jsonObject.getIntValue("code");
        Object data = jsonObject.get("rows");
        if(Objects.nonNull(data)) {
            //ReqEvaluation
            //================================处理 ReqEvaluation====================================================
            List<ReqEvaluation> reqEvaluations = JsonUtil.json2list(JsonUtil.object2json(data), ReqEvaluation.class);
            if(CollectionUtils.isNotEmpty(reqEvaluations)){
                for (ReqEvaluation reqEv : reqEvaluations) {
                    //开启驼峰映射
                    BeanProcessor bean = new GenerousBeanProcessor();
                    RowProcessor processor = new BasicRowProcessor(bean);
                    ReqEvaluation byId = nQuery.findById("req_evaluation", reqEv.getId().intValue(),new BeanHandler<>(ReqEvaluation.class,processor));
                    if(Objects.nonNull(byId)){
                        //更新
                        try {
                            String upSQL = "UPDATE req_evaluation SET req_code=?, req_evaluator=?, req_evaluator_id=?, req_exp_end_time=?, req_owner=?, req_owner_id=?, req_rel=?, req_transfer=?, req_transfer_id=?, transfer_reason=?, eval_opinion=?, reject_reason=?, eval_time=?, pre_estimated_effort=?, cur_estimated_effort=?, save_status=?, notes=?, dept_id=?, est_end_time=?, notice_id=?, sync_cd=?, upt_time=? WHERE id=?";

                            nQuery.update(upSQL,

                                    reqEv.getReqCode(),
                                    reqEv.getReqEvaluator(),
                                    reqEv.getReqEvaluatorId(),
                                    reqEv.getReqExpEndTime(),

                                    reqEv.getReqOwner(),
                                    reqEv.getReqOwnerId(),
                                    reqEv.getReqRel(),
                                    reqEv.getReqTransfer(),
                                    reqEv.getReqTransferId(),
                                    reqEv.getTransferReason(),
                                    reqEv.getEvalOpinion(),

                                    reqEv.getRejectReason(),
                                    reqEv.getEvalTime(),
                                    reqEv.getPreEstimatedEffort(),
                                    reqEv.getCurEstimatedEffort(),
                                    reqEv.getSaveStatus(),
                                    reqEv.getNotes(),
                                    reqEv.getDeptId(),

                                    reqEv.getEstEndTime(),
                                    reqEv.getNoticeId(),
                                    reqEv.getSyncCd(),
                                    reqEv.getUptTime(),
                                    reqEv.getId()

                            );
                        }catch ( Exception e){
                            log.error("UPDATE req_evaluation xxxxxxxx",e);
                            e.printStackTrace();
                        }


                    }else{
                        try {
                            //新增
                            String insSQL = "INSERT INTO req_evaluation (id, req_code, req_evaluator, req_evaluator_id, req_exp_end_time," +
                                    " req_owner, req_owner_id, req_rel, req_transfer, req_transfer_id, transfer_reason, eval_opinion, " +
                                    "reject_reason, eval_time, pre_estimated_effort, cur_estimated_effort, save_status, notes, dept_id, " +
                                    "est_end_time, notice_id, sync_cd, upt_time) " +
                                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                            nQuery.insert(insSQL,
                                    reqEv.getId(),
                                    reqEv.getReqCode(),
                                    reqEv.getReqEvaluator(),
                                    reqEv.getReqEvaluatorId(),
                                    reqEv.getReqExpEndTime(),

                                    reqEv.getReqOwner(),
                                    reqEv.getReqOwnerId(),
                                    reqEv.getReqRel(),
                                    reqEv.getReqTransfer(),
                                    reqEv.getReqTransferId(),
                                    reqEv.getTransferReason(),
                                    reqEv.getEvalOpinion(),

                                    reqEv.getRejectReason(),
                                    reqEv.getEvalTime(),
                                    reqEv.getPreEstimatedEffort(),
                                    reqEv.getCurEstimatedEffort(),
                                    reqEv.getSaveStatus(),
                                    reqEv.getNotes(),
                                    reqEv.getDeptId(),

                                    reqEv.getEstEndTime(),
                                    reqEv.getNoticeId(),
                                    reqEv.getSyncCd(),
                                    reqEv.getUptTime()

                            );
                        }catch ( Exception e){
                            log.error("INSERT req_evaluation xxxxxxxx",e);
                            e.printStackTrace();
                        }

                    }
                }
            }
            //================================处理 ReqChangeLog====================================================
        }
    }

    /**
     * 需求评论
     * @param nQuery
     * @param reqCode
     * @throws Exception
     */
    private static void handleComment(NPQuery nQuery, String reqCode) throws Exception {
        String changeUrl = EnvCfgUtil.getValByCfgName("REQ_HOST")+ "/api/req/reqRequest/queryReqComments?reqCode="+ reqCode;
        String jsonResponse = sendGetRequest(changeUrl);

        JSONObject jsonObject = JSONObject.parseObject(jsonResponse);
        int code = jsonObject.getIntValue("code");
        Object data = jsonObject.get("rows");
        if(Objects.nonNull(data)) {
            //ReqComment
            //================================处理 ReqComment====================================================
            List<ReqComment> reqComments = JsonUtil.json2list(JsonUtil.object2json(data), ReqComment.class);
            if(CollectionUtils.isNotEmpty(reqComments)){
                for (ReqComment comment : reqComments) {
                    //开启驼峰映射
                    BeanProcessor bean = new GenerousBeanProcessor();
                    RowProcessor processor = new BasicRowProcessor(bean);
                    ReqComment byId = nQuery.findById("req_comment", comment.getId().intValue(),new BeanHandler<>(ReqComment.class,processor));
                    if(Objects.nonNull(byId)){
                        //更新

//                        try {
//                            String upSQL = "UPDATE req_comment SET req_code=?, comments=?, commenter_id=?, commenter_name=?," +
//                                    " comment_time=?, req_cc_list=?, req_cc_list_name=? WHERE id=? ";
//
//                            nQuery.update(upSQL,
//
//
//                                    comment.getReqCode(),
//                                    comment.getComments(),
//                                    comment.getCommenterId(),
//                                    comment.getCommenterName(),
//                                    comment.getCommentTime(),
//                                    comment.getReqCcList(),
//                                    comment.getReqCcListName(),
//                                    comment.getId()
//
//                            );
//                        }catch ( Exception e){
//                            log.error("UPDATE req_comment xxxxxxxx",e);
//                            e.printStackTrace();
//                        }


                    }else{
                        try {
                            //新增
                            String insSQL = "INSERT INTO req_comment (id, req_code, comments, commenter_id, commenter_name, comment_time, " +
                                    " req_cc_list, req_cc_list_name) " +
                                    " VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
                            nQuery.insert(insSQL,
                                    comment.getId(),
                                    comment.getReqCode(),
                                    comment.getComments(),
                                    comment.getCommenterId(),
                                    comment.getCommenterName(),
                                    comment.getCommentTime(),
                                    comment.getReqCcList(),
                                    comment.getReqCcListName()

                            );
                        }catch ( Exception e){
                            log.error("INSERT req_comment xxxxxxxx",e);
                            e.printStackTrace();
                        }

                    }
                }
            }
            //================================处理 ReqChangeLog====================================================
        }
    }

    /**
     * 获取需求变更列表
     * @param nQuery
     * @param reqCode
     * @throws Exception
     */
    private static void handleChangeLog(NPQuery nQuery, String reqCode) throws Exception {
        String changeUrl = EnvCfgUtil.getValByCfgName("REQ_HOST")+ "/api/req/reqRequest/queryReqChangeLog?reqCode="+ reqCode;
        String jsonResponse = sendGetRequest(changeUrl);

        JSONObject jsonObject = JSONObject.parseObject(jsonResponse);
        int code = jsonObject.getIntValue("code");
        Object data = jsonObject.get("rows");
        if(Objects.nonNull(data)) {
            //ReqChangeLog
            //================================处理 ReqChangeLog====================================================
            List<ReqChangeLog> reqChangeLogs = JsonUtil.json2list(JsonUtil.object2json(data), ReqChangeLog.class);
            if(CollectionUtils.isNotEmpty(reqChangeLogs)){
                for (ReqChangeLog reqChangeLog : reqChangeLogs) {
                    //开启驼峰映射
                    BeanProcessor bean = new GenerousBeanProcessor();
                    RowProcessor processor = new BasicRowProcessor(bean);
                    List<ReqChangeLog> byId = nQuery.findByCondition("req_change_log", " change_id = "+reqChangeLog.getChangeId(),new BeanListHandler<>(ReqChangeLog.class,processor));
                    if( CollectionUtils.isNotEmpty(byId) && Objects.nonNull(byId.get(0)) ){
                        //更新

//                        try {
//                            String upSQL = "UPDATE req_change_log SET req_code=?, change_desc=?, change_time=?, pre_status=?, new_status=?," +
//                                    " operator_id=?, operator_name=?, operator_role=?, link_code=?, link_name=? WHERE change_id=? ";
//
//                            nQuery.update(upSQL,
//
//                                    reqChangeLog.getReqCode(),
//                                    reqChangeLog.getChangeDesc(),
//                                    reqChangeLog.getChangeTime(),
//                                    reqChangeLog.getPreStatus(),
//                                    reqChangeLog.getNewStatus(),
//                                    reqChangeLog.getOperatorId(),
//                                    reqChangeLog.getOperatorName(),
//                                    reqChangeLog.getOperatorRole(),
//                                    reqChangeLog.getLinkCode(),
//                                    reqChangeLog.getLinkName(),
//                                    reqChangeLog.getChangeId()
//
//                            );
//                        }catch ( Exception e){
//                            log.error("UPDATE req_change_log xxxxxxxx",e);
//                            e.printStackTrace();
//                        }


                    }else{
                        try {
                            //新增
                            String insSQL = "INSERT INTO req_change_log (change_id, req_code, change_desc, change_time, pre_status, new_status, " +
                                    " operator_id, operator_name, operator_role, link_code, link_name) " +
                                    " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                            nQuery.insert(insSQL,
                                    reqChangeLog.getChangeId(),
                                    reqChangeLog.getReqCode(),
                                    reqChangeLog.getChangeDesc(),
                                    reqChangeLog.getChangeTime(),
                                    reqChangeLog.getPreStatus(),
                                    reqChangeLog.getNewStatus(),
                                    reqChangeLog.getOperatorId(),
                                    reqChangeLog.getOperatorName(),
                                    reqChangeLog.getOperatorRole(),
                                    reqChangeLog.getLinkCode(),
                                    reqChangeLog.getLinkName()

                            );
                        }catch ( Exception e){
                            log.error("INSERT req_change_log xxxxxxxx",e);
                            e.printStackTrace();
                        }

                    }
                }
            }
            //================================处理 ReqChangeLog====================================================
        }
    }

    /**
     * 需求关闭
     * @param nQuery
     * @param reqCode
     * @throws Exception
     */
    private static void handleClosed(NPQuery nQuery, String reqCode) throws Exception {
        String closedUrl = EnvCfgUtil.getValByCfgName("REQ_HOST")+ "/api/req/reqRequest/queryReqClosed?reqCode="+ reqCode;
        String jsonResponse = sendGetRequest(closedUrl);

        JSONObject jsonObject = JSONObject.parseObject(jsonResponse);
        int code = jsonObject.getIntValue("code");
        Object data = jsonObject.get("rows");
        if(Objects.nonNull(data)) {
            //req_closed
            //================================处理 req_closed====================================================
            List<ReqClosed> reqCloseds = JsonUtil.json2list(JsonUtil.object2json(data), ReqClosed.class);
            if(CollectionUtils.isNotEmpty(reqCloseds)){
                for (ReqClosed closed : reqCloseds) {
                    //开启驼峰映射
                    BeanProcessor bean = new GenerousBeanProcessor();
                    RowProcessor processor = new BasicRowProcessor(bean);
                    ReqClosed byId = nQuery.findById("req_closed", closed.getId().intValue(),new BeanHandler<>(ReqClosed.class,processor));
                    if(Objects.nonNull(byId)){
                        //更新

//                        try {
//                            String upSQL = "UPDATE req_closed SET req_code=?, close_reason=?, note=?, " +
//                                    "operation_time=?, operator=?, operator_id=? WHERE id=?";
//
//                            nQuery.update(upSQL,
//
//                                    closed.getReqCode(),
//                                    closed.getCloseReason(),
//                                    closed.getNote(),
//                                    closed.getOperationTime(),
//                                    closed.getOperator(),
//                                    closed.getOperatorId(),
//                                    closed.getId()
//                            );
//                        }catch ( Exception e){
//                            log.error("UPDATE req_closed xxxxxxxx",e);
//                            e.printStackTrace();
//                        }


                    }else{
                        try {
                            //新增
                            String insSQL = "INSERT INTO req_closed (id, req_code, close_reason, note, operation_time, operator, operator_id) " +
                                    "VALUES(?, ?, ?, ?, ?, ?, ?)";
                            nQuery.insert(insSQL,
                                    closed.getId(),
                                    closed.getReqCode(),
                                    closed.getCloseReason(),
                                    closed.getNote(),
                                    closed.getOperationTime(),
                                    closed.getOperator(),
                                    closed.getOperatorId()

                            );
                        }catch ( Exception e){
                            log.error("INSERT req_closed xxxxxxxx",e);
                            e.printStackTrace();
                        }

                    }
                }
            }
            //================================处理 req_closed====================================================

        }
    }

    /**
     * 需求验证
     * @param nQuery
     * @param reqCode
     * @throws Exception
     */
    private static void handleValidation(NPQuery nQuery, String reqCode) throws Exception {
        String vUrl = EnvCfgUtil.getValByCfgName("REQ_HOST")+ "/api/req/reqRequest/queryReqReqValidation?reqCode="+ reqCode;
        String jsonResponse = sendGetRequest(vUrl);

        JSONObject jsonObject = JSONObject.parseObject(jsonResponse);
        int code = jsonObject.getIntValue("code");
        Object data = jsonObject.get("rows");
        if(Objects.nonNull(data)) {
            //reqValidation
            //================================处理reqValidation====================================================
            List<ReqValidation> reqValidations = JsonUtil.json2list(JsonUtil.object2json(data), ReqValidation.class);
            if(CollectionUtils.isNotEmpty(reqValidations)){
                for (ReqValidation reqV : reqValidations) {
                    //开启驼峰映射
                    BeanProcessor bean = new GenerousBeanProcessor();
                    RowProcessor processor = new BasicRowProcessor(bean);
                    ReqValidation byId = nQuery.findById("req_validation", reqV.getId().intValue(),new BeanHandler<>(ReqValidation.class,processor));
                    if(Objects.nonNull(byId)){
                        //更新

//                        try {
//                            String upSQL = "UPDATE req_validation SET req_code=?, note=?, satisfaction=?, validation_status=?, " +
//                                    "validation_time=?, validator=?, validator_id=? WHERE id=?" ;
//                            nQuery.update(upSQL,
//                                    reqV.getReqCode(),
//                                    reqV.getNote(),
//                                    reqV.getSatisfaction(),
//                                    reqV.getValidationStatus(),
//                                    reqV.getValidationTime(),
//                                    reqV.getValidator(),
//                                    reqV.getValidatorId(),
//                                    reqV.getId()
//                            );
//                        }catch ( Exception e){
//                            log.error("UPDATE req_validation xxxxxxxx",e);
//                            e.printStackTrace();
//                        }


                    }else{
                        try {
                            //新增
                            String insSQL = "INSERT INTO req_validation (id, req_code, note, satisfaction, validation_status," +
                                    " validation_time, validator, validator_id) VALUES(?, ?, ?, ?, ?, ?, ?, ?)" ;
                            nQuery.insert(insSQL,
                                    reqV.getId(),
                                    reqV.getReqCode(),
                                    reqV.getNote(),
                                    reqV.getSatisfaction(),
                                    reqV.getValidationStatus(),
                                    reqV.getValidationTime(),
                                    reqV.getValidator(),
                                    reqV.getValidatorId()

                            );
                        }catch ( Exception e){
                            log.error("INSERT req_validation xxxxxxxx",e);
                            e.printStackTrace();
                        }

                    }
                }
            }
            //================================处理reqValidation====================================================

        }
    }

    /**
     * 处理需求发布
     * @param nQuery
     * @param reqCode
     * @throws Exception
     */
    private static void handlePub(NPQuery nQuery, String reqCode) throws Exception {
        String pubUrl = EnvCfgUtil.getValByCfgName("REQ_HOST")+ "/api/req/reqRequest/queryReqPublish?reqCode="+ reqCode;
        String jsonResponse = sendGetRequest(pubUrl);

        JSONObject jsonObject = JSONObject.parseObject(jsonResponse);
        int code = jsonObject.getIntValue("code");
        JSONObject data = jsonObject.getJSONObject("data");
        if(Objects.nonNull(data)) {
            //reqPublishes
            //================================处理reqPublishes====================================================
            List<ReqPublish> reqPublishes = JsonUtil.json2list(JsonUtil.object2json(data.get("reqPublishes")), ReqPublish.class);

            if(CollectionUtils.isNotEmpty(reqPublishes)){
                for (ReqPublish reqPublish : reqPublishes) {
                    //开启驼峰映射
                    BeanProcessor bean = new GenerousBeanProcessor();
                    RowProcessor processor = new BasicRowProcessor(bean);
                    ReqPublish byId = nQuery.findById("req_publish", reqPublish.getId().intValue(),new BeanHandler<>(ReqPublish.class,processor));
                    if(Objects.nonNull(byId)){
                        //更新

                        try {
                            String upSQL = "UPDATE req_publish SET req_code=?, add_time=?, project_name=?, publish_time=?," +
                                    " publisher=?, publisher_id=?, release_notes=?, req_rel=?, test_type=?, upt_time=? WHERE id=?" ;
                            nQuery.update(upSQL,
                                    reqPublish.getReqCode(),
                                    reqPublish.getAddTime(),
                                    reqPublish.getProjectName(),
                                    reqPublish.getPublishTime(),
                                    reqPublish.getPublisher(),
                                    reqPublish.getPublisherId(),
                                    reqPublish.getReleaseNotes(),
                                    reqPublish.getReqRel(),
                                    reqPublish.getTestType(),
                                    reqPublish.getUptTime(),
                                    reqPublish.getId()
                            );
                        }catch ( Exception e){
                            log.error("UPDATE req_publish xxxxxxxx",e);
                            e.printStackTrace();
                        }


                    }else{
                        try {
                            //新增
                            String insSQL = "INSERT INTO req_publish (id, req_code, add_time, project_name, publish_time, publisher, " +
                                    "publisher_id, release_notes, req_rel, test_type, upt_time)" +
                                    " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                            nQuery.insert(insSQL,
                                    reqPublish.getId(),
                                    reqPublish.getReqCode(),
                                    reqPublish.getAddTime(),
                                    reqPublish.getProjectName(),
                                    reqPublish.getPublishTime(),
                                    reqPublish.getPublisher(),
                                    reqPublish.getPublisherId(),
                                    reqPublish.getReleaseNotes(),
                                    reqPublish.getReqRel(),
                                    reqPublish.getTestType(),
                                    reqPublish.getUptTime()
                            );
                        }catch ( Exception e){
                            log.error("INSERT req_publish xxxxxxxx",e);
                            e.printStackTrace();
                        }

                    }
                }
            }

            //================================处理reqPublishes====================================================
        }
    }

    /**
     * 处理需求分发 &任务列表
     * @param nQuery
     * @param reqCode
     * @throws Exception
     */
    private static void handleDistsTasks(NPQuery nQuery, String reqCode) throws Exception {
        String distUrl = EnvCfgUtil.getValByCfgName("REQ_HOST")+ "/api/req/reqRequest/queryReqDistribution?reqCode="+ reqCode;
        String jsonResponse = sendGetRequest(distUrl);

        JSONObject jsonObject = JSONObject.parseObject(jsonResponse);
        int code = jsonObject.getIntValue("code");
        JSONObject data = jsonObject.getJSONObject("data");
        if(Objects.nonNull(data)){

            //================================处理dists====================================================
            List<ReqDists> reqDists = JsonUtil.json2list(JsonUtil.object2json(data.get("reqDists")), ReqDists.class);

            if(CollectionUtils.isNotEmpty(reqDists)){
                for (ReqDists reqDist : reqDists) {
                    //开启驼峰映射
                    BeanProcessor bean = new GenerousBeanProcessor();
                    RowProcessor processor = new BasicRowProcessor(bean);
                    ReqDists byId = nQuery.findById("req_dists", reqDist.getId().intValue(),new BeanHandler<>(ReqDists.class,processor));
                    if(Objects.nonNull(byId)){
                        //更新

                        try {
                            String upSQL = "UPDATE req_dists SET req_code=?, notes=?, planned_end_date=?, planned_start_date=?," +
                                    " processing_status=?, processing_time=?, save_status=?, upt_time=? WHERE id=?";
                            nQuery.update(upSQL,
                                    reqDist.getReqCode(),
                                    reqDist.getNotes(),
                                    reqDist.getPlannedEndDate(),
                                    reqDist.getPlannedStartDate(),
                                    reqDist.getProcessingStatus(),
                                    reqDist.getProcessingTime(),
                                    reqDist.getSaveStatus(),
                                    reqDist.getUptTime(),
                                    reqDist.getId()
                            );
                        }catch ( Exception e){
                            log.error("UPDATE req_dists xxxxxxxx",e);
                            e.printStackTrace();
                        }


                    }else{
                        try {
                            //新增
                            String insSQL = "INSERT INTO req_dists (id, req_code, notes, planned_end_date, planned_start_date, processing_status," +
                                    " processing_time, save_status, upt_time) " +
                                    " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)" ;
                            nQuery.insert(insSQL,
                                    reqDist.getId(),
                                    reqDist.getReqCode(),
                                    reqDist.getNotes(),
                                    reqDist.getPlannedEndDate(),
                                    reqDist.getPlannedStartDate(),
                                    reqDist.getProcessingStatus(),
                                    reqDist.getProcessingTime(),
                                    reqDist.getSaveStatus(),
                                    reqDist.getUptTime()
                            );
                        }catch ( Exception e){
                            log.error("INSERT req_dists xxxxxxxx",e);
                            e.printStackTrace();
                        }

                    }
                }
            }

            //================================处理dists====================================================


            //================================处理tasks====================================================
            List<ReqTasks> reqTasks = JsonUtil.json2list(JsonUtil.object2json(data.get("reqTasks")), ReqTasks.class);

            if(CollectionUtils.isNotEmpty(reqTasks)){
                for (ReqTasks reqTask : reqTasks) {
                    //开启驼峰映射
                    BeanProcessor bean = new GenerousBeanProcessor();
                    RowProcessor processor = new BasicRowProcessor(bean);
                    ReqTasks byId = nQuery.findById("req_tasks", reqTask.getId().intValue(),new BeanHandler<>(ReqTasks.class,processor));
                    if(Objects.nonNull(byId)){
                        //更新

                        try {
                            String upSQL = "UPDATE req_tasks SET req_code=?, req_estimated_effort=?, req_request=?, task_desc=?, " +
                                    "task_name=?, task_notes=?, task_number=?, task_status=?, upt_time=?, add_time=?, assignee=?, assignee_id=?, changers=?, " +
                                    "distributor=?, distributor_id=?, handledate=?, is_del=?, is_his=?, notes=?, planned_end_date=?, planned_start_date=?, " +
                                    "practicalend=?, practicalstart=? WHERE id=?";
                            nQuery.update(upSQL,

                                    reqTask.getReqCode(),
                                    reqTask.getReqEstimatedEffort(),
                                    reqTask.getReqRequest(),
                                    reqTask.getTaskDesc(),

                                    reqTask.getTaskName(),
                                    reqTask.getTaskNotes(),
                                    reqTask.getTaskNumber(),
                                    reqTask.getTaskStatus(),
                                    reqTask.getUptTime(),
                                    reqTask.getAddTime(),
                                    reqTask.getAssignee(),
                                    reqTask.getAssigneeId(),

                                    reqTask.getChangers(),
                                    reqTask.getDistributor(),
                                    reqTask.getDistributorId(),
                                    reqTask.getHandledate(),
                                    reqTask.getIsDel(),
                                    reqTask.getIsHis(),
                                    reqTask.getNotes(),
                                    reqTask.getPlannedEndDate(),

                                    reqTask.getPlannedStartDate(),
                                    reqTask.getPracticalend(),
                                    reqTask.getPracticalstart(),

                                    reqTask.getId()
                            );
                        }catch ( Exception e){
                            log.error("UPDATE req_tasks xxxxxxxx",e);
                            e.printStackTrace();
                        }


                    }else{
                        try {
                            //新增
                            String insSQL = "INSERT INTO req_tasks (id, req_code, req_estimated_effort, req_request, task_desc, " +
                                    " task_name, task_notes, task_number, task_status, upt_time, add_time, assignee, assignee_id, " +
                                    " changers, distributor, distributor_id, handledate, is_del, is_his, notes, planned_end_date, " +
                                    " planned_start_date, practicalend, practicalstart) " +
                                    " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                            nQuery.insert(insSQL,
                                    reqTask.getId(),
                                    reqTask.getReqCode(),
                                    reqTask.getReqEstimatedEffort(),
                                    reqTask.getReqRequest(),
                                    reqTask.getTaskDesc(),

                                    reqTask.getTaskName(),
                                    reqTask.getTaskNotes(),
                                    reqTask.getTaskNumber(),
                                    reqTask.getTaskStatus(),
                                    reqTask.getUptTime(),
                                    reqTask.getAddTime(),
                                    reqTask.getAssignee(),
                                    reqTask.getAssigneeId(),

                                    reqTask.getChangers(),
                                    reqTask.getDistributor(),
                                    reqTask.getDistributorId(),
                                    reqTask.getHandledate(),
                                    reqTask.getIsDel(),
                                    reqTask.getIsHis(),
                                    reqTask.getNotes(),
                                    reqTask.getPlannedEndDate(),

                                    reqTask.getPlannedStartDate(),
                                    reqTask.getPracticalend(),
                                    reqTask.getPracticalstart()
                            );
                        }catch ( Exception e){
                            log.error("INSERT req_tasks xxxxxxxx",e);
                            e.printStackTrace();
                        }

                    }
                }
            }

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
}
