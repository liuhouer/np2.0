
package cn.northpark.test.dataclean;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import lombok.Data;

@Generated("net.hexar.json2pojo")
@Data
public class ReqArtifact {
    @Expose
    private Long id;
    @Expose
    private String attachId;
    @Expose
    private String attachName;
    @Expose
    private String attachftp;
    @Expose
    private String attachpath;
    @Expose
    private String artifactType;

    @Expose
    private String starttime;
    @Expose
    private String endtime;

    @Expose
    private String projectManager;
    @Expose
    private String projectManagerId;
    @Expose
    private String projectName;
    @Expose
    private String publishTime;
    @Expose
    private String publisher;
    @Expose
    private String publisherId;
    @Expose
    private String relsize;
    @Expose
    private String reqRel;
    @Expose
    private String subCategory;

}
