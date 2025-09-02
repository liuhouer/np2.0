import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/**
 * @author bruce
 * @date 2025年03月30日 14:45:16
 */
public class WHSurveyDataGenerator {
    // 生成符合分布的随机打乱列表
    private static List<Integer> createShuffledList(int[] counts) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            int value = 5 - i; // 5=很满意, 4=较满意, ..., 1=不满意
            for (int j = 0; j < counts[i]; j++) {
                list.add(value);
            }
        }
        Collections.shuffle(list);
        return list;
    }

    public static void main(String[] args) throws IOException {
        // 定义表头，与真实数据一致
        String[] headers = {
                "序号", "提交答卷时间", "所用时间", "来源", "来源详情", "来自IP", "总分",
                "1、您是否曾向《供应链管理》投稿？",
                "2、当前《供应链管理》为AMI核心期刊，被人大复印报刊资料中心收录，尚未入选南大核心和北大核心。当前的这一评级，是否符合您的期望？",
                "3、根据知网的数据，《供应链管理》当前复合影响因子1.083，综合影响因子0.505。这一数据是否符合您的期望？",
                "4、我刊当前的整体编校加工质量是否符合您的期望？",
                "5、我刊目前仍专注于物流、供应链领域，偶尔会涉及宏观经济内容，但占比极少。您期望我刊的收稿范围更加聚焦还是更加宽泛些？（喜欢聚焦小范围or喜欢宽泛大范围）",
                "6、我刊目前仍专注于物流、供应链领域，偶尔会涉及宏观经济内容，但占比极少。这样的聚焦策略是否符合您的期望？（期望“聚焦”）",
                "7、我刊目前仍专注于物流、供应链领域，偶尔会涉及宏观经济内容，但占比极少。这样的聚焦策略是否符合您的期望？（期望“宽泛”）",
                "8、在与编辑的沟通中，您的整体体验是否舒适？",
                "9、在与编辑的沟通中，编辑所体现出的学术素养水平是否符合您的期望？",
                "10、期刊目前在持续向相与编辑的沟通过程中，尤其是审稿的沟通中，编辑对您进一步提高写作水平提供了多大程度的帮助?",
                "11、向我刊投稿时，稿件自投稿之日起到确定最终审稿结果所用的时长是否符合您的期望？",
                "12、当稿件确定录用后，最终的实际见刊时间是否符合您的期望？",
                "13、当您向我刊投稿时，需要支付的相关费用是否在您能够接受的范围内？",
                "14、当您向我刊投稿时，投稿渠道是否便捷？",
                "15、我刊当前的审稿要求中，对投稿作者的身份是否应该严格要求？（喜欢高标准or宽标准）",
                "16、我刊当前的审稿要求中，对投稿作者的身份是否严格？（期望“高标准”）",
                "17、我刊当前的审稿要求中，对投稿作者的身份是否严苛？（期望“宽标准”）",
                "18、我刊当前对稿件内容的审核是否足够严格？",
                "19、如有其他针对期刊投稿体验优化的建议，欢迎在此补充。",
                "20、如有其他任何建议，欢迎在此补充。"
        };

        // 定义独立问题的分布
        String[] independentQs = {"q2", "q3", "q4", "q8", "q9", "q10", "q11", "q12", "q13", "q14", "q18"};
        int[][] dists = {
                {52, 47, 30, 3, 1},  // q2: A1
                {51, 53, 27, 1, 1},  // q3: A2
                {52, 44, 27, 4, 6},  // q4: A3
                {15, 26, 43, 41, 8}, // q8: B1
                {55, 47, 28, 2, 1},  // q9: B2
                {41, 30, 51, 10, 1}, // q10: B3
                {29, 34, 44, 23, 3}, // q11: C1
                {31, 33, 41, 21, 7}, // q12: C2
                {28, 42, 39, 19, 5}, // q13: C3
                {52, 39, 32, 7, 3},  // q14: D1
                {40, 37, 50, 5, 1}   // q18: D3
        };

        // 生成独立问题的答案列表
        Map<String, List<Integer>> qLists = new HashMap<>();
        for (int i = 0; i < independentQs.length; i++) {
            qLists.put(independentQs[i], createShuffledList(dists[i]));
        }

        // 生成问题 5、6、7 的数据（A4）
        int[] q6Dist = {10, 17, 27, 9, 1}; // q5=1 时 q6 的分布
        int[] q7Dist = {14, 18, 31, 5, 1}; // q5=2 时 q7 的分布
        List<Integer> q6ForQ5_1 = createShuffledList(q6Dist);
        List<Integer> q7ForQ5_2 = createShuffledList(q7Dist);
        List<int[]> q567List = new ArrayList<>();
        for (int i = 0; i < 64; i++) { // 64 人选择聚焦
            q567List.add(new int[]{1, q6ForQ5_1.get(i), 0});
        }
        for (int i = 0; i < 69; i++) { // 69 人选择宽泛
            q567List.add(new int[]{2, 0, q7ForQ5_2.get(i)});
        }
        Collections.shuffle(q567List);

        // 生成问题 15、16、17 的数据（D2）
        int[] q16Dist = {29, 31, 24, 3, 0}; // q15=1 时 q16 的分布
        int[] q17Dist = {9, 7, 18, 8, 4};   // q15=2 时 q17 的分布
        List<Integer> q16ForQ15_1 = createShuffledList(q16Dist);
        List<Integer> q17ForQ15_2 = createShuffledList(q17Dist);
        List<int[]> q151617List = new ArrayList<>();
        for (int i = 0; i < 87; i++) { // 87 人选择高标准
            q151617List.add(new int[]{1, q16ForQ15_1.get(i), 0});
        }
        for (int i = 0; i < 46; i++) { // 46 人选择低要求
            q151617List.add(new int[]{2, 0, q17ForQ15_2.get(i)});
        }
        Collections.shuffle(q151617List);

        // 生成 133 份问卷数据
        int numRespondents = 133;
        List<List<Object>> data = new ArrayList<>();
        for (int i = 0; i < numRespondents; i++) {
            List<Object> row = new ArrayList<>();
            row.add(i + 1);                       // 序号
            row.add("2025/1/27 15:" + String.format("%02d", i % 60) + ":00"); // 提交答卷时间
            row.add("120秒");                     // 所用时间
            row.add("微信");                      // 来源
            row.add("N/A");                       // 来源详情
            row.add("192.168.1.1");               // 来自IP
            row.add("");                          // 总分（未知计算规则，留空）
            row.add(1);                           // q1（假设都投稿）
            row.add(qLists.get("q2").get(i));     // q2
            row.add(qLists.get("q3").get(i));     // q3
            row.add(qLists.get("q4").get(i));     // q4
            int[] q567 = q567List.get(i);
            row.add(q567[0]);                     // q5
            row.add(q567[1] != 0 ? q567[1] : ""); // q6
            row.add(q567[2] != 0 ? q567[2] : ""); // q7
            row.add(qLists.get("q8").get(i));     // q8
            row.add(qLists.get("q9").get(i));     // q9
            row.add(qLists.get("q10").get(i));    // q10
            row.add(qLists.get("q11").get(i));    // q11
            row.add(qLists.get("q12").get(i));    // q12
            row.add(qLists.get("q13").get(i));    // q13
            row.add(qLists.get("q14").get(i));    // q14
            int[] q151617 = q151617List.get(i);
            row.add(q151617[0]);                  // q15
            row.add(q151617[1] != 0 ? q151617[1] : ""); // q16
            row.add(q151617[2] != 0 ? q151617[2] : ""); // q17
            row.add(qLists.get("q18").get(i));    // q18
            row.add("");                          // q19
            row.add("");                          // q20
            data.add(row);
        }

        // 创建 Excel 文件
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("问卷数据");

        // 写入表头
        Row headerRow = sheet.createRow(0);
        for (int j = 0; j < headers.length; j++) {
            Cell cell = headerRow.createCell(j);
            cell.setCellValue(headers[j]);
        }

        // 写入数据
        for (int i = 0; i < numRespondents; i++) {
            Row row = sheet.createRow(i + 1);
            List<Object> rowData = data.get(i);
            for (int j = 0; j < rowData.size(); j++) {
                Object value = rowData.get(j);
                if (value != null && !value.toString().isEmpty()) {
                    Cell cell = row.createCell(j);
                    if (value instanceof Integer) {
                        cell.setCellValue((Integer) value);
                    } else if (value instanceof String) {
                        cell.setCellValue((String) value);
                    }
                }
            }
        }

        // 保存文件
        String filePath = "C:\\Users\\Bruce\\Desktop\\wh_datas.xlsx";
        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            workbook.write(fileOut);
            System.out.println("Excel 文件已成功生成并保存至: " + filePath);
        }
        workbook.close();
    }
}
