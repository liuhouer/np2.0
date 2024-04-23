
package cn.northpark.test.dataclean;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import lombok.Data;

@Generated("net.hexar.json2pojo")
@Data
public class ReqComment {
    @Expose
    private Long id;
    @Expose
    private String reqCode;

    @Expose
    private String comments;

    @Expose
    private String commenterId;
    @Expose
    private String commenterName;

    @Expose
    private String commentTime;

    @Expose
    private String reqCcList;
    @Expose
    private String reqCcListName;


}
