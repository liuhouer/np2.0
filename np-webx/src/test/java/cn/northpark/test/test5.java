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
 * 根据 B 站分 P 信息（200-zh.json）重命名【一人一首成名曲】200 首合集。
 *
 * @author zhangyang
 * @date 2026年6月5日
 */
public class test5 {

    private static final String DOWNLOAD_DIR =
            "C:\\Users\\Bruce\\Downloads\\BilibiliDown.v6.41.win_x64_jre11.release\\download\\无损音乐合集";

    private static final String FILE_PREFIX =
            "【一人一首成名曲】致曾经耳熟能详的旋律  精选200首合集 8090后珍藏歌单 怀旧金曲";

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
            if (!name.startsWith(FILE_PREFIX)) {
                skipped++;
                continue;
            }

            Matcher matcher = PAGE_IN_NAME.matcher(name);
            if (!matcher.find()) {
                System.out.println("无法解析分 P，跳过: " + name);
                failed++;
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
                System.out.println("OK: p" + String.format("%02d", page) + " -> " + newName);
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
                userDir.resolve("src/test/java/200-zh.json"),
                userDir.resolve("np-webx/src/test/java/200-zh.json"),
                Paths.get("d:/APP/workspace/np2/np-webx/src/test/java/200-zh.json")
        };
        for (Path candidate : candidates) {
            if (Files.isRegularFile(candidate)) {
                return candidate.toAbsolutePath().normalize();
            }
        }
        throw new IllegalStateException("找不到 200-zh.json，请将工作目录设为 np-webx 或项目根目录后重试");
    }
}
