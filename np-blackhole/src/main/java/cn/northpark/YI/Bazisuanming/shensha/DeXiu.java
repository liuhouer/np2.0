package cn.northpark.YI.Bazisuanming.shensha;


import cn.northpark.YI.Bazisuanming.domain.Bazi;
import cn.northpark.YI.Bazisuanming.domain.DiZhi;
import cn.northpark.YI.Bazisuanming.domain.ShenSha;
import cn.northpark.YI.Bazisuanming.domain.TianGan;

import java.util.List;

import static cn.northpark.YI.Bazisuanming.domain.TianGan.*;



/**
 * Created by Jeey
 * 德秀贵人 寅午戌月，丙丁为德，戊癸为秀。申子辰月，壬癸戊己为德，丙辛甲己为秀。巳酉丑月，庚辛为德，乙庚为秀。亥卯未月，甲乙为德，丁壬为秀
 */
public class DeXiu implements ShenSha {


    @Override
    public boolean isLiuNianShenSha(Bazi bazi, int year) {
        return false;
    }

    @Override
    public boolean isShensha(Bazi bazi, DiZhi diZhi, TianGan tianGan) {
        String month = bazi.getMonthZhi().toString();
        List<TianGan> tianGans =  bazi.getAllTianGan();
        if ("寅午戌".contains(month)) {
            return (tianGans.contains(丙)||tianGans.contains(丁))&&(tianGans.contains(戊)||tianGans.contains(癸));
        }
        if ("申子辰".contains(month)) {
            return (tianGans.contains(壬)||tianGans.contains(癸)||tianGans.contains(戊)||tianGans.contains(己))&&(tianGans.contains(丙)||tianGans.contains(辛)||tianGans.contains(甲)||tianGans.contains(己));
        }
        if ("巳酉丑".contains(month)) {
            return (tianGans.contains(庚)||tianGans.contains(辛))&&(tianGans.contains(乙)||tianGans.contains(庚));
        }
        if ("亥卯未".contains(month)) {
            return (tianGans.contains(甲)||tianGans.contains(乙))&&(tianGans.contains(丁)||tianGans.contains(壬));
        }
        return false;
    }

    @Override
    public String getDescription() {
        return "德秀贵人";
    }

}
