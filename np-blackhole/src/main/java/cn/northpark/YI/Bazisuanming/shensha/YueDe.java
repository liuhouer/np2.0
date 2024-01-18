package cn.northpark.YI.Bazisuanming.shensha;

import cn.northpark.YI.Bazisuanming.domain.Bazi;
import cn.northpark.YI.Bazisuanming.domain.DiZhi;
import cn.northpark.YI.Bazisuanming.domain.ShenSha;
import cn.northpark.YI.Bazisuanming.domain.TianGan;


/**
 * Created by Jeey
 *  月德贵人 寅午戌月生者见丙, 申子辰月生者见壬,亥卯未月生者见甲,巳酉丑月生者见庚. 凡柱中年月日时干上见者为有月德贵人.
 */
public class YueDe implements ShenSha {

    private static final String CHEATS="月,寅午戌见丙,申子辰见壬,亥卯未见甲,巳酉丑见庚";

    @Override
    public boolean isLiuNianShenSha(Bazi bazi, int year) {
        return false;
    }

    @Override
    public boolean isShensha(Bazi bazi, DiZhi diZhi, TianGan tianGan) {
        return isShenSha(bazi,diZhi,tianGan,CHEATS);
    }

    @Override
    public String getDescription() {
        return "月德贵人";
    }

}
