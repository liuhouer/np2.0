import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class SurveyDataGenerator {
    private static final Random random = new Random();
    private static final int TOTAL_SURVEYS = 366;
    private static final int VALID_SURVEYS = 362;

    public static void main(String[] args) {
        List<String[]> surveys = generateSurveys();
        writeToCSV(surveys, "survey_data.csv");
    }

    private static List<String[]> generateSurveys() {
        List<String[]> surveys = new ArrayList<>();

        // 生成有效问卷
        for (int i = 0; i < VALID_SURVEYS; i++) {
            surveys.add(generateValidSurvey());
        }

        // 生成无效问卷
        //四种无效问卷分别为：
        //全部填1分
        //规律性答题（1,2,3,4,5循环）
        //基本信息矛盾（频率与时长不匹配）
        //关键指标严重偏离（极端值）
//        surveys.add(generateAllOnesInvalidSurvey());
//        surveys.add(generatePatternInvalidSurvey());
//        surveys.add(generateContradictoryInvalidSurvey());
//        surveys.add(generateExtremeInvalidSurvey());

        // 打乱顺序
        Collections.shuffle(surveys);

        // 添加序号
        for (int i = 0; i < surveys.size(); i++) {
            String[] survey = surveys.get(i);
            survey[0] = String.valueOf(i + 1);
        }

        return surveys;
    }

    private static String[] generateValidSurvey() {
        String[] survey = new String[56]; // 修改为56列：1(序号) + 3(基本信息) + 48(评分项) + 4(总体评价)

        // 序号（临时，后面会重新赋值）
        survey[0] = "0";

        // 基本信息
        survey[1] = generateWorkRole();
        survey[2] = generateFrequency();
        survey[3] = generateDuration();

        // 生成评分项（正态分布，保持相关性）
        generateScores(survey, 4);

        // 总体评价
        generateOverallEvaluation(survey, 52);

        return survey;
    }

    private static void generateScores(String[] survey, int startIndex) {
        double baseMean = 3.5 + random.nextGaussian() * 0.3;

        for (int i = 0; i < 48; i++) {
            double score = baseMean + random.nextGaussian() * 0.5;
            score = Math.min(5, Math.max(1, Math.round(score)));
            survey[startIndex + i] = String.valueOf((int)score);
        }
    }

    private static String generateWorkRole() {
        String[] roles = {"需求提出方", "需求管理方", "IT开发方", "其他"};
        double[] weights = {0.4, 0.3, 0.2, 0.1};
        return weightedRandomChoice(roles, weights);
    }

    private static String generateFrequency() {
        String[] frequencies = {"每天", "每周3次以上", "每周1-2次", "每月几次", "几乎不用"};
        double[] weights = {0.3, 0.35, 0.2, 0.1, 0.05};
        return weightedRandomChoice(frequencies, weights);
    }

    private static String generateDuration() {
        String[] durations = {"3个月以下", "3-6个月", "6-12个月", "1年以上"};
        double[] weights = {0.1, 0.2, 0.3, 0.4};
        return weightedRandomChoice(durations, weights);
    }

    private static void generateOverallEvaluation(String[] survey, int startIndex) {
        // 总体满意度
        double avgScore = Arrays.stream(survey)
                .skip(4)
                .limit(48)
                .mapToInt(s -> Integer.parseInt(s))
                .average()
                .orElse(3);
        survey[startIndex] = String.valueOf((int)Math.round(avgScore));

        // 改进方面
        String[] improvements = {"系统质量", "需求管理效率", "需求质量", "服务支持"};
        List<String> selectedImprovements = new ArrayList<>();
        while (selectedImprovements.size() < 2) {
            String improvement = improvements[random.nextInt(improvements.length)];
            if (!selectedImprovements.contains(improvement)) {
                selectedImprovements.add(improvement);
            }
        }
        survey[startIndex + 1] = selectedImprovements.get(0);
        survey[startIndex + 2] = selectedImprovements.get(1);

        // 是否愿意访谈
        survey[startIndex + 3] = random.nextDouble() < 0.3 ? "是" : "否";
    }

    private static String[] generateAllOnesInvalidSurvey() {
        String[] survey = generateValidSurvey();
        for (int i = 4; i < 52; i++) {
            survey[i] = "@@@1";
        }
        return survey;
    }

    private static String[] generatePatternInvalidSurvey() {
        String[] survey = generateValidSurvey();
        for (int i = 4; i < 52; i++) {
            survey[i] = "@@@"+String.valueOf((i - 3) % 5 + 1);
        }
        return survey;
    }

    private static String[] generateContradictoryInvalidSurvey() {
        String[] survey = generateValidSurvey();
        survey[2] = "@@@每天";
        survey[3] = "@@@3个月以下";
        return survey;
    }

    private static String[] generateExtremeInvalidSurvey() {
        String[] survey = generateValidSurvey();
        for (int i = 4; i < 52; i++) {
            survey[i] = random.nextBoolean() ?  "@@@"+"1" :  "@@@"+"5";
        }
        return survey;
    }

    private static String weightedRandomChoice(String[] options, double[] weights) {
        double total = Arrays.stream(weights).sum();
        double r = random.nextDouble() * total;
        double cumsum = 0;
        for (int i = 0; i < options.length; i++) {
            cumsum += weights[i];
            if (r <= cumsum) {
                return options[i];
            }
        }
        return options[options.length - 1];
    }

    private static void writeToCSV(List<String[]> surveys, String filename) {
        try (FileWriter fw = new FileWriter(filename);
             BufferedWriter bw = new BufferedWriter(fw)) {

            // 写入表头
            String header = "序号,工作角色,使用频率,使用时长," +
                    String.join(",", generateColumnHeaders()) + "," +
                    "总体满意度,改进方面1,改进方面2,愿意访谈";
            bw.write(header);
            bw.newLine();

            // 写入数据
            for (String[] survey : surveys) {
                bw.write(String.join(",", survey));
                bw.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String> generateColumnHeaders() {
        List<String> headers = new ArrayList<>();
        String[] sections = {"系统质量", "需求管理效率", "需求质量", "服务支持"};
        for (String section : sections) {
            for (int i = 1; i <= 4; i++) {
                for (int j = 1; j <= 3; j++) {
                    headers.add(String.format("%s_%d.%d", section, i, j));
                }
            }
        }
        return headers;
    }
}