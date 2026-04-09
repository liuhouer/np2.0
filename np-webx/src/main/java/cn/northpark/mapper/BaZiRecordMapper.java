package cn.northpark.mapper;

import cn.northpark.model.BaZiRecord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BaZiRecordMapper {

    void insert(BaZiRecord record);
}
