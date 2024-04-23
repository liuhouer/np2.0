
package cn.northpark.test.dataclean;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import lombok.Data;

@Generated("net.hexar.json2pojo")
@Data
public class ReqEvaluation {
    @Expose
    private Long id;

    @Expose
    private String reqCode;
    @Expose
    private String reqEvaluator;
    @Expose
    private String reqEvaluatorId;
    @Expose
    private String reqExpEndTime;
    @Expose
    private String reqOwner;
    @Expose
    private String reqOwnerId;
    @Expose
    private String reqRel;
    @Expose
    private String reqTransfer;
    @Expose
    private String reqTransferId;

    @Expose
    private String transferReason;

    @Expose
    private String evalOpinion;
    @Expose
    private String rejectReason;
    @Expose
    private String evalTime;

    @Expose
    private Long preEstimatedEffort;
    @Expose
    private Long curEstimatedEffort;

    @Expose
    private String saveStatus;
    @Expose
    private String notes;
    @Expose
    private String deptId;
    @Expose
    private String estEndTime;

    @Expose
    private String noticeId;

    @Expose
    private String syncCd;

    @Expose
    private String uptTime;


}
