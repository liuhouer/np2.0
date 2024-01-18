package cn.northpark.YI.Bazisuanming.domain;

/**
 * Created by Jeey
 * 五行
 */
public enum WuXing {
    金(1),
    木(2),
    水(3),
    火(4),
    土(5);

    private int id;


    WuXing(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
