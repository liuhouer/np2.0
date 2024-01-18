package cn.northpark.YI.Bazisuanming.shensha;


import cn.northpark.YI.Bazisuanming.domain.Bazi;
import cn.northpark.YI.Bazisuanming.domain.DiZhi;
import cn.northpark.YI.Bazisuanming.domain.ShenSha;
import cn.northpark.YI.Bazisuanming.domain.TianGan;

import static cn.northpark.YI.Bazisuanming.domain.DiZhi.巳;
import static cn.northpark.YI.Bazisuanming.domain.TianGan.辛;

/**
 * Created by Jeey
 * 学堂 金命见巳，辛巳为正；木命见亥，己亥为正；水命见申，甲申为正；土命见申，戊申为正；火命见寅，丙寅为正
 */
public class XueTang implements ShenSha {

    @Override
    public boolean isLiuNianShenSha(Bazi bazi, int year) {
        return false;
    }

    @Override
    public boolean isShensha(Bazi bazi, DiZhi diZhi, TianGan tianGan) {
        if(bazi.getYearGan().getWuXing().equals("金")||bazi.getDayGan().getWuXing().equals("金")){
            if(tianGan.equals(辛)||diZhi.equals(巳)){

            }
        }
        return false;
    }

    @Override
    public String getDescription() {
        return "福星贵人";
    }

}
