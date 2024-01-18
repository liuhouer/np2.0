package cn.northpark.YI.Bazisuanming.body;

import java.util.Date;

/**
 * Created by Jeey
 */
public class JieQi {
    private String jieQiName;
    private Date jieQiDate;

    public JieQi(String jieQiName, Date jieQiDate) {
        this.jieQiName = jieQiName;
        this.jieQiDate = jieQiDate;
    }

    public String getJieQiName() {
        return jieQiName;
    }

    public Date getJieQiDate() {
        return jieQiDate;
    }
}
