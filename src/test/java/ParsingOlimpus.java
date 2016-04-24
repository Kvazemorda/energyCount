import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Map;

public class ParsingOlimpus {

    private static String baseUrl = "http://srv-app:9001/Prepare/Examing/Result?attemptId=53545";
    String ptm = "Пожарно-технический минимум для работников учреждений (офисов)";
    String ot = "ОТ 151.10. Обучение и проверка знаний требований охраны труда для работников учреждений (офисов)",
    bilet = "<th>Билет</th>\n" +
            "\t\t\t\t<td>",
    sdan = "<span class=\"passed\">\tЭкзамен сдан </span>",
    question = "\"Question\":\"<div>",
    answer = "\"Answer\":\"<div>";

    public static void main(String[] args) throws IOException {
/*
        for(int i = 53545; i < 56546; i++){
            URL url = new URL("http://srv-app:9001/Prepare/Examing/Result?attemptId=53545");
            URLConnection urlConnection = url.openConnection();
           // urlConnection.setRequestProperty("User-Agent", "Crome");

            System.out.println();
            BufferedReader page = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            line = page.readLine();
            System.out.println(line);
            page.close();


        }
*/
        Connection.Response res = Jsoup.connect(baseUrl)
                .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
                //.data("WorkplaceToken", "570c7824-6662-4aa3-afd5-3da135950bd1", "prepareCookie", "employeeId=15448&profileId=1")
                .method(Connection.Method.POST)
                .execute();

        Document doc = res.parse();
        String sessionId = res.cookie("WorkplaceToken");
    }
        public static Map<String, String> getCookiesForSession(String url) throws IOException {
        Connection.Response res = Jsoup.connect(url)
                .method(Connection.Method.GET).timeout(50000).execute();

        return res.cookies();

    }
}