
package cn.northpark.test.dataclean;

import com.google.gson.annotations.Expose;
import lombok.Data;

@Data
public class ReqDists {

    @Expose
    private Long id;
    @Expose
    private String notes;
    @Expose
    private String plannedEndDate;
    @Expose
    private String plannedStartDate;
    @Expose
    private String processingStatus;
    @Expose
    private String processingTime;
    @Expose
    private Object rejectionReason;
    @Expose
    private String reqCode;
    @Expose
    private Object reqRel;
    @Expose
    private String saveStatus;
    @Expose
    private String uptTime;

}
