package cn.northpark.YI.Bazisuanming.shensha;

import cn.northpark.YI.Bazisuanming.domain.Bazi;
import cn.northpark.YI.Bazisuanming.domain.DiZhi;
import cn.northpark.YI.Bazisuanming.domain.ShenSha;
import cn.northpark.YI.Bazisuanming.domain.TianGan;


/**
 * Created by Jeey
 * 太极贵人 甲乙生人子午中, 丙丁鸡兔定亨通,戊己两干临四季,庚辛寅亥禄丰隆,壬癸巳申偏喜美, 值此应当福气 钟,更须贵格来相扶,候封万户到三公.
 */
public class TaiJi implements ShenSha {

    private static final String CHEATS="年日,甲乙见子午,丙丁见酉卯,壬癸见申巳,庚辛见寅亥,戊己见丑辰未戌";

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
        return "太极贵人";
    }

}
