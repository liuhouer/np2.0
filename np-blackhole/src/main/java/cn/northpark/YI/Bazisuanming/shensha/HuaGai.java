package cn.northpark.YI.Bazisuanming.shensha;


import cn.northpark.YI.Bazisuanming.domain.Bazi;
import cn.northpark.YI.Bazisuanming.domain.DiZhi;
import cn.northpark.YI.Bazisuanming.domain.ShenSha;
import cn.northpark.YI.Bazisuanming.domain.TianGan;

import static cn.northpark.YI.Bazisuanming.domain.DiZhi.*;
/**
 * Created by Jeey
 * 华盖 寅午戌见戌, 亥卯未见未,申子辰见辰, 巳酉丑见丑. 以年支或日支不主, 凡四柱中所见者为有华盖星.
 */
public class HuaGai implements ShenSha {

    @Override
    public boolean isLiuNianShenSha(Bazi bazi, int year) {
        return false;
    }

    @Override
    public boolean isShensha(Bazi bazi, DiZhi diZhi, TianGan tianGan) {
        String yearZhi = bazi.getYearZhi().toString();
        String dayZhi = bazi.getDayZhi().toString();
        if(("申子辰".contains(yearZhi)||"申子辰".contains(dayZhi))&&dayZhi.equals(辰)){
            return true;
        }
        if(("寅午戌".contains(yearZhi)||"寅午戌".contains(dayZhi))&&dayZhi.equals(戌)){
            return true;
        }
        if(("巳酉丑".contains(yearZhi)||"巳酉丑".contains(dayZhi))&&dayZhi.equals(丑)){
            return true;
        }
        if(("亥卯未".contains(yearZhi)||"亥卯未".contains(dayZhi))&&dayZhi.equals(未)){
            return true;
        }
        return false;
    }

    @Override
    public String getDescription() {
        return "福星贵人";
    }

}
