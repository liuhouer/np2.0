package cn.northpark.np5.utils;

import cn.hutool.core.io.FileUtil;
import cn.northpark.np5.utils.EnvCfgUtil;
import io.minio.*;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.nio.charset.StandardCharsets;

@Slf4j
public class MinioUtils {

    public static String uploadText(String text) {
        String oid = System.currentTimeMillis() + "_" + (int)(Math.random() * 1000) + ".txt";

        File tempFile = FileUtil.newFile(oid);
        FileUtil.writeString(text, tempFile, StandardCharsets.UTF_8);

        MinioClient client = buildClient();

        try {
            client.uploadObject(UploadObjectArgs.builder()
                    .bucket(EnvCfgUtil.getValByCfgName("TEXT_BUCKET"))
                    .object(oid)
                    .filename(tempFile.getAbsolutePath())
                    .build());

            tempFile.delete();
        } catch (Exception e) {
            log.error("MINIO-uploadText--->,{}", e);
        }

        return oid;
    }

    public static String readText(String oid) {
        MinioClient client = buildClient();

        try {
            File tempFile = FileUtil.newFile(oid);

            client.downloadObject(DownloadObjectArgs.builder()
                    .bucket(EnvCfgUtil.getValByCfgName("TEXT_BUCKET"))
                    .object(oid)
                    .filename(tempFile.getAbsolutePath())
                    .build());

            String text = FileUtil.readString(tempFile, StandardCharsets.UTF_8);

            tempFile.delete();

            return text;
        } catch (Exception e) {
            log.error("MINIO-readText--->,{}", e);
        }

        return "";
    }

    public static MinioClient buildClient() {
        return MinioClient.builder()
                .endpoint(EnvCfgUtil.getValByCfgName("MINIO_API_"))
                .credentials(EnvCfgUtil.getValByCfgName("MINIO_ACCESS_KEY"), EnvCfgUtil.getValByCfgName("MINIO_SECRET_KEY"))
                .build();
    }
}