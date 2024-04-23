
package cn.northpark.test.dataclean;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import lombok.Data;

@Generated("net.hexar.json2pojo")
@Data
public class ReqClosed {
    @Expose
    private Long id;
    @Expose
    private String reqCode;
    @Expose
    private String closeReason;
    @Expose
    private String note;
    @Expose
    private String operationTime;
    @Expose
    private String operator;
    @Expose
    private String operatorId;

}
