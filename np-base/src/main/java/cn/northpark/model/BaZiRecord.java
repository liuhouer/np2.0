package cn.northpark.model;

import lombok.Data;
import java.util.Date;

/**
 * 八字排盘记录
 */
@Data
public class BaZiRecord {

    private Long id;
    private Integer userId;
    private String userName;
    /** 微信用户 openId */
    private String openId;
    /** 姓名/备注（可选） */
    private String name;
    /** 性别：1=男，0=女 */
    private Integer gender;
    private Integer birthYear;
    private Integer birthMonth;
    private Integer birthDay;
    private Integer birthHour;
    /** 出生分，默认0 */
    private Integer birthMinute;
    /** 排盘结果 */
    private String panResult;
    /** 运势结果 */
    private String yunResult;
    private String ip;
    /** 是否免费：1=免费，0=付费 */
    private Integer isFree;
    private Date createTime;
}
