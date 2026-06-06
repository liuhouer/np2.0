package cn.northpark.test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 将 BilibiliDown 下载的【欧美R&B】歌单分 P 文件按固定曲名重命名。
 *
 * @author zhangyang
 * @date 2026年6月5日
 */
public class test4 {

    private static final String DOWNLOAD_DIR =
            "C:\\Users\\Bruce\\Downloads\\BilibiliDown.v6.41.win_x64_jre11.release\\download\\无损音乐合集";

    /** 仅处理该歌单前缀的文件，如 ...-p01-80.mp4 */
    private static final String FILE_PREFIX =
            "【欧美R&B】R&B脑袋必听的仙品歌单 _ 精选50首欧美R&B _ 学习_通勤_洗澡_放松_工作_做饭";

    private static final Pattern PAGE_IN_NAME = Pattern.compile("-p(\\d+)-", Pattern.CASE_INSENSITIVE);

    private static final String[] TITLES = {
            "《Paris in the Rain》",
            "《Deep Green》",
            "《I Wanted You》",
            "《Right Now》",
            "《Shut up My Moms Calling》",
            "《Fallin' Out》",
            "《So Sick》",
            "《Leave The Door Open》",
            "《Die For You》",
            "《Call You Tonight》",
            "《50 Feet》",
            "《HEARTBREAK ANNIVERSARY》",
            "《Baby Powder》",
            "《Snooze》",
            "《Redbone》",
            "《Glow》",
            "《Gravity》",
            "《Love Songs》",
            "《Booty Music》",
            "《Baby》",
            "《Take A Bow》",
            "《Watch Me Work》",
            "《Gimme More》",
            "《Love Yourself》",
            "《Uptown Funk》",
            "《Attention》",
            "《double take》",
            "《Bleeding Love》",
            "《After Hours》",
            "《Bye Bye》",
            "《Break Up In A Small Town》",
            "《Dilemma》",
            "《Because Of You》",
            "《Landslide》",
            "《Fatal Love》",
            "《Off The Hook》",
            "《Insomnia》",
            "《Love you like I do》",
            "《I Wanna Be》",
            "《This Could Be You》",
            "《Sing You To Sleep》",
            "《Popular》",
            "《Peaches》",
            "《EX》",
            "《wyd》",
            "《Out At Sea》",
            "《Come Around Me》",
            "《BABYDOLL》",
            "《Bitter Pill》",
            "《Stay Calm》"
    };

    public static void main(String[] args) {
        Map<Integer, String> pageToTitle = buildPageMap();
        System.out.println("已加载 " + pageToTitle.size() + " 首曲名");
        renameByPageTitles(Paths.get(DOWNLOAD_DIR), pageToTitle);
    }

    static Map<Integer, String> buildPageMap() {
        Map<Integer, String> map = new HashMap<>();
        for (int i = 0; i < TITLES.length; i++) {
            map.put(i + 1, TITLES[i]);
        }
        return map;
    }

    static void renameByPageTitles(Path directory, Map<Integer, String> pageToTitle) {
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
            String title = pageToTitle.get(page);
            if (title == null) {
                System.out.println("未找到 page=" + page + " 的曲名，跳过: " + name);
                failed++;
                continue;
            }

            String ext = name.substring(name.lastIndexOf('.'));
            String newName = sanitizeFileName(title) + ext;
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
}
