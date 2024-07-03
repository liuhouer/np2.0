package cn.northpark.test.dataclean;

import cn.northpark.utils.MinioUtils;
import cn.northpark.utils.NPQueryRunner;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 批量迁移富文本
 */
@Slf4j
public class transSoftMinio {

    public static void main(String[] args) {
        String countSql = "SELECT COUNT(*) FROM bc_soft where content2 is null";
        int totalCount = NPQueryRunner.query(countSql, new ScalarHandler<Long>()).intValue();

        int batchSize = 100;
        int totalBatches = (int) Math.ceil((double) totalCount / batchSize);

        ExecutorService executorService = Executors.newFixedThreadPool(20);

        for (int batchNumber = 0; batchNumber < totalBatches; batchNumber++) {
            int offset = batchNumber * batchSize;

            String sql = "SELECT id, content FROM bc_soft where content2 is null ORDER BY id ASC LIMIT " + offset + "," + batchSize;
            List<Map<String, Object>> maps = NPQueryRunner.query(sql, new MapListHandler());

            for (Map<String, Object> item : maps) {
                executorService.execute(() -> {
                    log.info("线程" + Thread.currentThread().getId() + "开始处理---" );
                    String id = item.get("id").toString();
                    String content = item.get("content").toString();
                    // 富文本上传
                    String minioId = MinioUtils.uploadText(content);
                    String up_sql = "UPDATE bc_soft SET content2 = '" + minioId + "' WHERE id = " + id;
                    NPQueryRunner.update(up_sql);
                    log.info("线程" + Thread.currentThread().getId() + "处理完成---" );
                });
            }
        }

        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            log.error("线程池等待被中断", e);
            Thread.currentThread().interrupt();
        }
    }


    SELECT t.stu_no, COALESCE(a.name, '') AS name
    FROM (
            SELECT 'PS20223112504' AS stu_no UNION ALL
            SELECT 'PS20233112458' UNION ALL
            SELECT 'PS20233112459' UNION ALL
            SELECT 'PS20233112466' UNION ALL
            SELECT 'PS20233112470' UNION ALL
            SELECT 'PS20233112482' UNION ALL
            SELECT 'PS20233112489' UNION ALL
            SELECT 'PS20233112518' UNION ALL
            SELECT 'PS20233112521' UNION ALL
            SELECT 'PS20233112523' UNION ALL
            SELECT 'PS20233112525' UNION ALL
            SELECT 'PS20233112526' UNION ALL
            SELECT 'PS20233112533' UNION ALL
            SELECT 'PS20233112542' UNION ALL
            SELECT 'PS20233112550' UNION ALL
            SELECT 'PS20233112553' UNION ALL
            SELECT 'PS20233112574' UNION ALL
            SELECT 'PS20233112603' UNION ALL
            SELECT 'pS20233112607' UNION ALL
            SELECT 'PS20233112609' UNION ALL
            SELECT 'PS20233112626' UNION ALL
            SELECT 'PS20233112670' UNION ALL
            SELECT 'PS20233112680'
    ) AS t
    LEFT JOIN mba_student a ON t.stu_no = a.stu_no;

}
