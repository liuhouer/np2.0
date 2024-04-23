
package cn.northpark.test.dataclean;

import com.google.gson.annotations.Expose;
import lombok.Data;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@Data
public class ReqValidation {

    @Expose
    private Long id;
    @Expose
    private String note;
    @Expose
    private String reqCode;
    @Expose
    private String satisfaction;
    @Expose
    private String validationStatus;
    @Expose
    private String validationTime;
    @Expose
    private String validator;
    @Expose
    private String validatorId;

}
