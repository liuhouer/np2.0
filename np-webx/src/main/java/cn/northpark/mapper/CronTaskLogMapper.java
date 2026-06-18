package cn.northpark.mapper;

import cn.northpark.model.CronTaskLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 定时任务执行日志Mapper
 * @author bruce
 */
@Mapper
public interface CronTaskLogMapper {

    /**
     * 分页查询任务执行日志
     */
    List<CronTaskLog> findByTaskId(@Param("taskId") Long taskId, 
                                     @Param("offset") Integer offset, 
                                     @Param("limit") Integer limit);

    /**
     * 查询任务执行日志总数
     */
    int countByTaskId(@Param("taskId") Long taskId);

    /**
     * 根据ID查询日志
     */
    CronTaskLog findById(@Param("id") Long id);

    /**
     * 新增执行日志
     */
    int insert(CronTaskLog cronTaskLog);

    /**
     * 更新执行日志
     */
    int update(CronTaskLog cronTaskLog);

    /**
     * 删除日志
     */
    int deleteById(@Param("id") Long id);
}