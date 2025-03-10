
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author bruce
 * @date 2025年03月10日 15:51:33
 */
public class ProcessStudentFile {
    // 数据库连接信息（需要根据你的实际情况修改）
    private static final String DB_URL = "jdbc:mysql://localhost:3306/flink?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "123456";

    public static void main(String[] args) {
        String filePath = "C:\\Users\\Bruce\\Desktop\\汇报顺序.txt";

        try {
            // 读取文件并处理
            List<String> updatedLines = processFile(filePath);

            // 写回文件
            writeToFile(filePath, updatedLines);

            System.out.println("文件处理完成！");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<String> processFile(String filePath) throws IOException, SQLException {
        List<String> lines = new ArrayList<>();

        // 读取文件
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // 按制表符分割
                String[] parts = line.split("\t");

                // 检查是否包含学号（以 PS 开头）
                if (parts.length >= 2 && (parts[1].startsWith("PS") || parts[1].startsWith("ps") || parts[1].startsWith("pS") || parts[1].startsWith("Ps") ) ) {
                    String stuNo = parts[1];
                    // 查询数据库并获取学生信息
                    String studentInfo = queryStudentInfo(stuNo);
                    // 将查询结果追加到原行
                    lines.add(line + "\t" + studentInfo);
                } else {
                    // 非学号行保持不变
                    lines.add(line);
                }
            }
        }

        return lines;
    }

    private static String queryStudentInfo(String stuNo) throws SQLException {
        String sql = "SELECT name, gender, prof, phone_no FROM mba_student WHERE stu_no = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, stuNo.toUpperCase());
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // 获取学生信息并用制表符拼接
                String name = rs.getString("name");
                String gender = rs.getString("gender");
                String prof = rs.getString("prof");
                String phoneNo = rs.getString("phone_no");

                return String.join("\t", name, gender, prof, phoneNo);
            } else {
                return "未找到\t\t\t"; // 如果没找到学生，返回占位符
            }
        }
    }

    private static void writeToFile(String filePath, List<String> lines) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(filePath), StandardCharsets.UTF_8))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        }
    }
}
