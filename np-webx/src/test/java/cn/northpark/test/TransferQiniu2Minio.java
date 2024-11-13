package cn.northpark.test;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.northpark.utils.MinioUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author bruce
 * @date 2024年11月09日 16:39:33
 */
@Slf4j
public class TransferQiniu2Minio {
    // 创建固定大小的线程池
    private static final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    /**
     * 异步并发同步七牛云文件到Minio
     * @param sourceDir 源目录
     * @param bucketName Minio目标bucket名称
     */
    public static void syncQiniuToMinioAsync(String sourceDir, String bucketName) throws Exception {
        // 确保bucket存在
        MinioUtils.createBucket(bucketName);
        // 获取所有文件路径
        List<Path> filePaths = Files.walk(Paths.get(sourceDir))
                .filter(Files::isRegularFile)
                .collect(Collectors.toList());
        // 使用CompletableFuture并发处理文件上传
        List<CompletableFuture<Void>> futures = filePaths.stream()
                .map(filePath -> CompletableFuture.runAsync(() -> {
                    try {
                        // 获取相对路径
                        Path relativePath = Paths.get(sourceDir).relativize(filePath);
                        String objectName = relativePath.toString().replace("\\", "/");
                        // 上传文件到Minio
                        try (FileInputStream inputStream = new FileInputStream(filePath.toFile())) {
                            MinioUtils.putObject(
                                    bucketName,
                                    objectName,
                                    inputStream,
                                    Files.size(filePath),
                                    Files.probeContentType(filePath)
                            );
                            log.info("成功上传文件: {}", objectName);
                        }
                    } catch (Exception e) {
                        log.error("上传文件失败: " + filePath, e);
                    }
                }, executorService))
                .collect(Collectors.toList());
        // 等待所有任务完成
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    }


    /**
     * 查找失败的文件
     * @param sourceDir 源目录
     * @param bucketName Minio bucket名称
     * @return 失败文件的路径列表
     */
    public static List<String> findFailedFiles(String sourceDir, String bucketName) throws Exception {
        List<String> failedFiles = new CopyOnWriteArrayList<>();

        // 获取所有文件路径
        List<Path> filePaths = Files.walk(Paths.get(sourceDir))
                .filter(Files::isRegularFile)
                .collect(Collectors.toList());

        // 使用CompletableFuture并发处理文件
        List<CompletableFuture<Void>> futures = filePaths.stream()
                .map(filePath -> CompletableFuture.runAsync(() -> {

                    try {
                        // 获取相对路径
                        Path relativePath = Paths.get(sourceDir).relativize(filePath);
                        String objectName = null;

                        if("soft".equals(bucketName)){
                            objectName = "soft/"+ relativePath.toString().replace("\\", "/");
                        }else {
                            objectName = relativePath.toString().replace("\\", "/");
                        }

                        // 检查文件是否存在于Minio
                        try {
                            MinioUtils.getObjectInfo(bucketName, objectName);
                        } catch (Exception e) {
                            // 如果文件不存在，添加到失败列表
                            failedFiles.add(filePath.toString());
                            log.info("找到失败的文件: {}", filePath);
                        }
                    } catch (Exception e) {
                        log.error("检查文件失败: " + filePath, e);
                    }

                }, executorService))
                .collect(Collectors.toList());
        // 等待所有任务完成
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        return failedFiles;
    }

    /**
     * 重新上传失败的文件并重命名
     * @param failedFiles 失败文件列表
     * @param bucketName Minio bucket名称
     * @return 文件映射关系 (原路径 -> 新路径)
     */
    public static Map<String, String> reuploadFailedFiles(List<String> failedFiles, String bucketName) throws Exception {
        Map<String, String> pathMapping = new ConcurrentHashMap<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        AtomicInteger counter = new AtomicInteger(0);

        List<CompletableFuture<Void>> futures = failedFiles.stream()
                .map(filePath -> CompletableFuture.runAsync(() -> {
                    try {
                        File file = new File(filePath);
                        String extension = filePath.substring(filePath.lastIndexOf('.'));

                        // 生成新的文件名
                        String newFileName = dateFormat.format(new Date()) + counter.incrementAndGet() + extension;
                        String newObjectName = "2024-11-9/" + newFileName;

                        // 上传文件到Minio
                        try (FileInputStream inputStream = new FileInputStream(file)) {
                            MinioUtils.putObject(
                                    bucketName,
                                    newObjectName,
                                    inputStream,
                                    file.length(),
                                    Files.probeContentType(file.toPath())
                            );

                            // 添加映射关系
                            String newPath = bucketName + "/" + newObjectName;
                            pathMapping.put(filePath, newPath);
                            log.info("文件重新上传成功: {} -> {}", filePath, newPath);
                        }
                    } catch (Exception e) {
                        log.error("重新上传文件失败: " + filePath, e);
                    }
                }, executorService))
                .collect(Collectors.toList());

        // 等待所有任务完成
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        return pathMapping;
    }


    /**
     * 修正文件名并上传到Minio（并发版本）
     * @param sourceDir 源目录
     * @param bucketName Minio bucket名称
     * @return 文件映射关系 (原文件名 -> 新文件名)
     */
    public static Map<String, String> fixFileNamesAndUpload(String sourceDir, String bucketName) throws Exception {
        // 使用线程安全的并发Map
        Map<String, String> pathMapping = new ConcurrentHashMap<>();

        // 确保bucket存在
        MinioUtils.createBucket(bucketName);
        // 获取所有文件路径
        List<Path> filePaths = Files.walk(Paths.get(sourceDir))
                .filter(Files::isRegularFile)
                .collect(Collectors.toList());
        // 使用CompletableFuture并发处理文件
        List<CompletableFuture<Void>> futures = filePaths.stream()
                .map(filePath -> CompletableFuture.runAsync(() -> {
                    try {
                        String fileName = filePath.getFileName().toString();
                        String newFileName = fixImageFileName(fileName);

                        Path newPath = filePath;
                        // 如果文件名发生变化，重命名文件
                        if (!fileName.equals(newFileName)) {
                            newPath = filePath.getParent().resolve(newFileName);
                            Files.move(filePath, newPath);
                            log.info("文件重命名: {} -> {}", fileName, newFileName);

                            // 上传到Minio
                            uploadToMinio(newPath, bucketName, newFileName);

                            // 记录映射关系
                            pathMapping.put(fileName, newFileName);
                        }


                    } catch (Exception e) {
                        log.error("处理文件失败: " + filePath, e);
                    }
                }, executorService))
                .collect(Collectors.toList());
        // 等待所有任务完成
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        return pathMapping;
    }
    /**
     * 修正图片文件名
     * @param fileName 原文件名
     * @return 修正后的文件名
     */
    private static String fixImageFileName(String fileName) {

        // 处理JPEG结尾但缺少点的情况
        if ( (fileName.endsWith("JPEG") || fileName.endsWith("jpeg")) &&  (!fileName.contains(".")) ){
            return fileName.substring(0, fileName.length() - 4) + ".jpeg";
        }

        // 其他可能的图片格式处理
        String[] imageExtensions = {"JPG", "jpg", "PNG", "png", "GIF", "gif"};
        for (String ext : imageExtensions) {
            if (fileName.endsWith(ext) && !fileName.contains(".")) {
                return fileName.substring(0, fileName.length() - ext.length()) + "." + ext.toLowerCase();
            }
        }

        // 如果文件名没有扩展名，添加.jpg
        if (!fileName.contains(".")) {
            return fileName + ".jpg";
        }


        return fileName;
    }

    /**
     * 上传文件到Minio
     */
    private static void uploadToMinio(Path filePath, String bucketName, String fileName) throws Exception {
        try (FileInputStream inputStream = new FileInputStream(filePath.toFile())) {
            MinioUtils.putObject(
                    bucketName,
                    fileName,
                    inputStream,
                    Files.size(filePath),
                    Files.probeContentType(filePath)
            );
            log.info("文件上传成功: {}", fileName);
        }
    }

    public static void main(String[] args) {

        //=======================第一次上传=======================
//        try {
//            String sourceDir = "C:\\Users\\Bruce\\Desktop\\qiniu\\movies";
//            String bucketName = "movies";
//
//            long startTime = System.currentTimeMillis();
//            syncQiniuToMinioAsync(sourceDir, bucketName);
//            long endTime = System.currentTimeMillis();
//
//            System.out.println("文件同步完成，耗时：" + (endTime - startTime) + "ms");
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            // 关闭线程池
//            executorService.shutdown();
//        }

        //=======================查找上传失败的=======================
        try {
            String sourceDir = "C:\\Users\\Bruce\\Desktop\\qiniu\\soft";
            String bucketName = "soft";

            // 1. 找出失败的文件
            log.info("开始查找失败的文件...");
            List<String> failedFiles = findFailedFiles(sourceDir, bucketName);
            FileUtil.appendLines(failedFiles,new File("C:\\Users\\Bruce\\Desktop\\softFailedFiles.txt"),"UTF-8");
            log.info("找到 {} 个失败的文件", failedFiles.size());

            // 2. 重新上传失败的文件
//            if (!failedFiles.isEmpty()) {
//                log.info("开始重新上传失败的文件...");
//                Map<String, String> pathMapping = reuploadFailedFiles(failedFiles, bucketName);
//
//                // 打印映射关系
//                log.info("文件映射关系:");
//                pathMapping.forEach((oldPath, newPath) ->
//                        log.info("原文件: {} -> 新文件: {}", oldPath, newPath));
//            }

        } catch (Exception e) {
            log.error("处理失败", e);
        } finally {
            executorService.shutdown();
        }
        //=======================重新处理有问题的文件============================
//        try {
//            String sourceDir = "C:\\Users\\Bruce\\Desktop\\qiniu\\soft";
//            String bucketName = "soft";
//
//            long startTime = System.currentTimeMillis();
//            log.info("开始处理文件...");
//
//            Map<String, String> pathMapping = fixFileNamesAndUpload(sourceDir, bucketName);
//
//            // 打印文件名映射关系
//            log.info("文件处理完成，映射关系如下：");
//            pathMapping.forEach((oldName, newName) ->
//                    log.info("原文件名: {} -> 新文件名: {}", oldName, newName));
//            long endTime = System.currentTimeMillis();
//            log.info("总耗时：{}ms", endTime - startTime);
//        } catch (Exception e) {
//            log.error("处理失败", e);
//        } finally {
//            // 关闭线程池
//            executorService.shutdown();
//        }

    }
}
