
package cn.northpark.test.dataclean;

import com.google.gson.annotations.Expose;
import lombok.Data;

@Data
public class ReqPublish {
    @Expose
    private Long id;
    @Expose
    private String addTime;
    @Expose
    private String projectName;
    @Expose
    private String publishTime;
    @Expose
    private String publisher;
    @Expose
    private String publisherId;
    @Expose
    private String releaseNotes;
    @Expose
    private String reqCode;
    @Expose
    private String reqRel;
    @Expose
    private String testType;
    @Expose
    private String uptTime;


}
