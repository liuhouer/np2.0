package cn.northpark.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 根据 B 站分 P 信息（200.json）重命名 BilibiliDown 下载的视频文件。
 *
 * @author zhangyang
 * @date 2026年6月5日20:38:13
 */
public class test3 {

    private static final String DOWNLOAD_DIR =
            "C:\\Users\\Bruce\\Downloads\\BilibiliDown.v6.41.win_x64_jre11.release\\download\\无损音乐合集";

    /** 匹配如：...最高音质-p01-80.mp4 */
    private static final Pattern PAGE_IN_NAME = Pattern.compile("-p(\\d+)-", Pattern.CASE_INSENSITIVE);

    public static void main(String[] args) throws IOException {
        Path jsonFile = resolveJsonFile();
        Map<Integer, String> pageToPart = loadPageParts(jsonFile);
        System.out.println("已加载 " + pageToPart.size() + " 个分 P 标题，来源: " + jsonFile);

        renameByPageParts(Paths.get(DOWNLOAD_DIR), pageToPart);
    }

    static Map<Integer, String> loadPageParts(Path jsonFile) throws IOException {
        String json = new String(Files.readAllBytes(jsonFile), StandardCharsets.UTF_8);
        JSONObject root = JSONObject.parseObject(json);
        JSONArray pages = root.getJSONObject("data").getJSONArray("pages");

        Map<Integer, String> pageToPart = new HashMap<>();
        for (int i = 0; i < pages.size(); i++) {
            JSONObject pageObj = pages.getJSONObject(i);
            int page = pageObj.getIntValue("page");
            String part = pageObj.getString("part");
            pageToPart.put(page, part);
        }
        return pageToPart;
    }

    static void renameByPageParts(Path directory, Map<Integer, String> pageToPart) {
        if (!Files.isDirectory(directory)) {
            System.out.println("目录不存在: " + directory);
            return;
        }

        File[] files = directory.toFile().listFiles();
        if (files == null || files.length == 0) {
            System.out.println("目录为空: " + directory);
            return;
        }

        int renamed = 0;
        int skipped = 0;
        int failed = 0;

        for (File file : files) {
            if (!file.isFile()) {
                continue;
            }
            String name = file.getName();
            Matcher matcher = PAGE_IN_NAME.matcher(name);
            if (!matcher.find()) {
                skipped++;
                continue;
            }

            int page = Integer.parseInt(matcher.group(1));
            String part = pageToPart.get(page);
            if (part == null) {
                System.out.println("未找到 page=" + page + " 的 part，跳过: " + name);
                failed++;
                continue;
            }

            String ext = name.substring(name.lastIndexOf('.'));
            String newName = sanitizeFileName(part) + ext;
            if (name.equals(newName)) {
                skipped++;
                continue;
            }

            File target = new File(directory.toFile(), newName);
            if (target.exists() && !target.equals(file)) {
                System.out.println("目标已存在，跳过: " + name + " -> " + newName);
                failed++;
                continue;
            }

            if (file.renameTo(target)) {
                System.out.println("OK: " + name);
                System.out.println(" -> " + newName);
                renamed++;
            } else {
                System.out.println("重命名失败: " + name + " -> " + newName);
                failed++;
            }
        }

        System.out.println("完成。重命名 " + renamed + " 个，跳过 " + skipped + " 个，失败 " + failed + " 个。");
    }

    private static String sanitizeFileName(String name) {
        return name.replaceAll("[\\\\/:*?\"<>|]", "_").trim();
    }

    private static Path resolveJsonFile() {
        Path userDir = Paths.get(System.getProperty("user.dir"));
        Path[] candidates = new Path[]{
                userDir.resolve("src/test/java/200.json"),
                userDir.resolve("np-webx/src/test/java/200.json"),
                Paths.get("d:/APP/workspace/np2/np-webx/src/test/java/200.json")
        };
        for (Path candidate : candidates) {
            if (Files.isRegularFile(candidate)) {
                return candidate.toAbsolutePath().normalize();
            }
        }
        throw new IllegalStateException("找不到 200.json，请将工作目录设为 np-webx 或项目根目录后重试");
    }
}
