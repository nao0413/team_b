package teamB.comicrental.top;
import java.sql.*;
import java.nio.file.*;

public class TopPageGenerator {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/comicrental";
        String user = "postgres";
        String password = "yourpassword";

        String inputTemplate = "top_template.html";
        String outputHtml = "top.html";

        StringBuilder rankingHtml = new StringBuilder();
        rankingHtml.append("<ol>\n");

        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);

            String sql = "SELECT title, author, comic_image, rentaltimes FROM comic ORDER BY rentaltimes DESC LIMIT 5";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            int rank = 1;
            while (rs.next()) {
                String title = rs.getString("title");
                String author = rs.getString("author");
                String image = rs.getString("comic_image");
                int times = rs.getInt("rentaltimes");

                rankingHtml.append(String.format(
                    "<li><img src='images/%s' alt='%s' width='80'><br><strong>%d位:</strong> %s（%s） - %d回</li>\n",
                    image, title, rank++, escapeHtml(title), escapeHtml(author), times
                ));
            }
            rankingHtml.append("</ol>\n");

            rs.close(); ps.close(); conn.close();

            // テンプレHTMLを読み込んでランキングに差し替え
            String template = new String(Files.readAllBytes(Paths.get(inputTemplate)), "UTF-8");
            String result = template.replace("<!-- RANKING_PLACEHOLDER -->", rankingHtml.toString());

            // 出力HTML保存
            Files.write(Paths.get(outputHtml), result.getBytes("UTF-8"));

            System.out.println("✅ 完成: " + outputHtml);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String escapeHtml(String text) {
        return text.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }
}
