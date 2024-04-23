
package cn.northpark.test.dataclean;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import lombok.Data;

@Generated("net.hexar.json2pojo")
@Data
public class ReqChangeLog {
    @Expose
    private Long changeId;
    @Expose
    private String reqCode;
    @Expose
    private String changeDesc;
    @Expose
    private String changeTime;
    @Expose
    private String preStatus;

    @Expose
    private String newStatus;

    @Expose
    private String operatorId;
    @Expose
    private String operatorName;
    @Expose
    private String operatorRole;

    @Expose
    private String linkCode;
    @Expose
    private String linkName;




}
