package cn.northpark.YI.Bazisuanming.shensha;


import cn.northpark.YI.Bazisuanming.domain.Bazi;
import cn.northpark.YI.Bazisuanming.domain.DiZhi;
import cn.northpark.YI.Bazisuanming.domain.ShenSha;
import cn.northpark.YI.Bazisuanming.domain.TianGan;

/**
 * Created by Jeey
 * 文昌贵人 甲乙巳午报君知, 丙戊申宫丁己鸡.庚猪辛鼠壬逢虎,癸人见卯入云梯. 查法: 以年干或日干为主, 凡四柱中地支所见者为是
 */
public class WenChang implements ShenSha {

    private static final String CHEATS="年日,甲乙见巳午,丙戊见申,丁己见酉,庚见亥,辛见子,壬见寅,癸见卯";

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
        return "文昌贵人";
    }

}
