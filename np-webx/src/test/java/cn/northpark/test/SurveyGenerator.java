package cn.northpark.test;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
/**
 * @author bruce
 * @date 2024年11月25日 21:10:30
 */
public class SurveyGenerator {
    private static final Random random = new Random();

    // 定义基础数据分布
    private static final String[] UNITS = {"A", "B", "C", "D", "E", "F", "G"};
    private static final String[] EDUCATION = {"A", "B", "C", "D"};
    private static final String[] EXPERIENCE = {"A", "B", "C", "D"};

    // 单位类型的权重分布
    private static final double[] UNIT_WEIGHTS = {0.2, 0.15, 0.2, 0.15, 0.15, 0.1, 0.05};
    // 学历的权重分布
    private static final double[] EDUCATION_WEIGHTS = {0.15, 0.35, 0.4, 0.1};
    // 工作经验的权重分布
    private static final double[] EXPERIENCE_WEIGHTS = {0.3, 0.35, 0.25, 0.1};

    public static void main(String[] args) {
        List<String[]> surveyData = generateSurveyData(220);
        writeToCsv(surveyData, "D:\\surveys-LHY.csv");
    }

    private static List<String[]> generateSurveyData(int count) {
        List<String[]> data = new ArrayList<>();

        // 添加表头
        String[] header = createHeader();
        data.add(header);

        // 生成数据行
        for (int i = 0; i < count; i++) {
            String[] row = generateSurveyRow();
            data.add(row);
        }

        return data;
    }

    private static String[] createHeader() {
        List<String> headers = new ArrayList<>();
        headers.add("序号");
        headers.add("单位类型");
        headers.add("学历");
        headers.add("工作经验");
        // 添加27个风险因素的表头
        for (int i = 1; i <= 27; i++) {
            headers.add("风险因素" + i);
        }
        return headers.toArray(new String[0]);
    }

    private static String[] generateSurveyRow() {
        List<String> row = new ArrayList<>();

        // 生成基础信息
        row.add(String.valueOf(random.nextInt(1000))); // 序号
        row.add(weightedRandomChoice(UNITS, UNIT_WEIGHTS));
        row.add(weightedRandomChoice(EDUCATION, EDUCATION_WEIGHTS));
        row.add(weightedRandomChoice(EXPERIENCE, EXPERIENCE_WEIGHTS));

        // 生成27个风险因素的评分
        double[] baseScores = generateBaseScores();
        for (int i = 0; i < 27; i++) {
            row.add(String.valueOf(adjustScore(baseScores[i])));
        }

        return row.toArray(new String[0]);
    }

    private static double[] generateBaseScores() {
        // 使用正态分布生成基础分数
        double[] scores = new double[27];
        for (int i = 0; i < 27; i++) {
            // 生成一个均值为3，标准差为0.8的正态分布随机数
            scores[i] = random.nextGaussian() * 0.8 + 3;
        }

        // 添加相关性
        for (int i = 0; i < 27; i++) {
            // 相邻风险因素间添加一定相关性
            if (i > 0) {
                scores[i] = scores[i] * 0.7 + scores[i-1] * 0.3;
            }
        }

        return scores;
    }

    private static int adjustScore(double score) {
        // 将分数限制在1-5之间，并四舍五入
        return Math.min(5, Math.max(1, (int) Math.round(score)));
    }

    private static String weightedRandomChoice(String[] options, double[] weights) {
        double total = Arrays.stream(weights).sum();
        double r = random.nextDouble() * total;
        double sum = 0;

        for (int i = 0; i < options.length; i++) {
            sum += weights[i];
            if (r <= sum) {
                return options[i];
            }
        }

        return options[options.length - 1];
    }

    private static void writeToCsv(List<String[]> data, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String[] row : data) {
                writer.write(String.join(",", row));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

