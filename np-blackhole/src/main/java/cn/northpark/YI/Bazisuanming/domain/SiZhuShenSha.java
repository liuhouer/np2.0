package cn.northpark.YI.Bazisuanming.domain;

import java.util.List;

/**
 * Created by Jeey
 * 四柱神煞
 */
public class SiZhuShenSha {
    private List<ShenSha> yearShenShas;
    private List<ShenSha> monthShenShas;
    private List<ShenSha> dayShenShas;
    private List<ShenSha> timeShenShas;

    public List<ShenSha> getYearShenShas() {
        return yearShenShas;
    }

    public List<ShenSha> getMonthShenShas() {
        return monthShenShas;
    }

    public List<ShenSha> getDayShenShas() {
        return dayShenShas;
    }

    public List<ShenSha> getTimeShenShas() {
        return timeShenShas;
    }
}
