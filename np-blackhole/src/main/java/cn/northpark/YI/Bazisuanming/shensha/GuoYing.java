package cn.northpark.YI.Bazisuanming.shensha;


import cn.northpark.YI.Bazisuanming.domain.Bazi;
import cn.northpark.YI.Bazisuanming.domain.DiZhi;
import cn.northpark.YI.Bazisuanming.domain.ShenSha;
import cn.northpark.YI.Bazisuanming.domain.TianGan;

/**
 * Created by Jeey on 2017/11/17.
 * 国印贵人 甲见戌, 乙见亥, 丙见丑, 丁见寅,戊见丑, 己见寅, 庚见辰, 辛见巳.壬见未, 癸见申
 */
public class GuoYing implements ShenSha {

    private static final String CHEATS="年日,甲见戌,乙见亥,丙见丑,丁见寅,戊见丑,己见寅,庚见辰,辛见巳,壬见未,癸见申";

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
        return "国印贵人";
    }

}
