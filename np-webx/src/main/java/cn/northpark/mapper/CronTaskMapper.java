package cn.northpark.mapper;

import cn.northpark.model.CronTask;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 定时任务配置Mapper
 * @author bruce
 */
@Mapper
public interface CronTaskMapper {

    /**
     * 查询所有定时任务
     */
    List<CronTask> findAll();

    /**
     * 根据ID查询定时任务
     */
    CronTask findById(@Param("id") Long id);

    /**
     * 根据类名和方法名查询
     */
    CronTask findByClassAndMethod(@Param("taskClass") String taskClass, @Param("taskMethod") String taskMethod);

    /**
     * 新增定时任务
     */
    int insert(CronTask cronTask);

    /**
     * 更新定时任务
     */
    int update(CronTask cronTask);

    /**
     * 删除定时任务
     */
    int deleteById(@Param("id") Long id);
}