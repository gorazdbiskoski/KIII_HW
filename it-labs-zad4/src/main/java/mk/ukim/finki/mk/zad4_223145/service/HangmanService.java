package mk.ukim.finki.mk.zad4_223145.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class HangmanService {
    private final HttpClient httpClient;

    public HangmanService() {
        this.httpClient = HttpClient.newHttpClient();
    }

    public String getHangmanResult(String choice, String inputField) throws IOException, InterruptedException {
        String url;
        if(choice.equals("cgi")) {
            url = "http://zad1:8081/date.cgi";
        }
        else if(choice.equals("java")) {
            url = "http://zad2:8082/date";
        }
        else if(choice.equals("dotnet")) {
            url = "http://zad3:8083/date";
        }
        else {
            return "Error";
        }

        if (!inputField.isEmpty()) {
            url += "?" + inputField;
        }

        HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        return getBody(response.body());
    }

    private String getBody(String html) {
        String bodyRegex = "<body[^>]*>(.*?)</body>";
        Pattern pattern = Pattern.compile(bodyRegex, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(html);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return "";
        }
    }
}
