package caesar_bot;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.common.collect.ImmutableMap;
import com.google.common.base.Splitter;
import org.apache.commons.lang3.StringUtils;


public class ApiClient {
    private static String API = "https://5tg3bs55i7.execute-api.us-west-2.amazonaws.com/dev/cipher/caesar/encode";
    public static Map<String, String> post(ImmutableMap<String, Object> values) throws IOException, InterruptedException {
        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper
            .writeValueAsString(values);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(API))
            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
            .build();

        HttpResponse<String> response = client.send(request,
            HttpResponse.BodyHandlers.ofString());
        String bodyText = StringUtils.strip(StringUtils.strip(response.body(), "{"), "}");
        Map<String, String> resultMap = new HashMap<String, String>();
        for(final String entry : bodyText.split(",")) {
            final String[] parts = entry.trim().split(":");
            String key = StringUtils.strip(parts[0].trim(), "\"");
            String value = StringUtils.strip(parts[1].trim(), "\"");
            resultMap.put(key, value);
        }
        return resultMap;
    }
}