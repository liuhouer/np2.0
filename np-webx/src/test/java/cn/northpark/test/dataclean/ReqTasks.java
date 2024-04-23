
package cn.northpark.test.dataclean;

import com.google.gson.annotations.Expose;
import lombok.Data;

@Data
public class ReqTasks {
    @Expose
    private Long id;
    @Expose
    private String reqCode;
    @Expose
    private Long reqEstimatedEffort;
    @Expose
    private String reqRequest;
    @Expose
    private String taskDesc;
    @Expose
    private String taskName;
    @Expose
    private String taskNotes;
    @Expose
    private String taskNumber;
    @Expose
    private String taskStatus;
    @Expose
    private String uptTime;
    @Expose
    private String addTime;
    @Expose
    private String assignee;
    @Expose
    private String assigneeId;
    @Expose
    private String changers;
    @Expose
    private String distributor;
    @Expose
    private String distributorId;
    @Expose
    private String handledate;
    @Expose
    private String isDel;
    @Expose
    private String isHis;
    @Expose
    private String notes;
    @Expose
    private String plannedEndDate;
    @Expose
    private String plannedStartDate;
    @Expose
    private String practicalend;
    @Expose
    private String practicalstart;

}
