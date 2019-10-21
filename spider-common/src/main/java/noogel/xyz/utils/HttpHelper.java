package noogel.xyz.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;

public class HttpHelper {
    public static final Logger logger = LoggerFactory.getLogger(HttpHelper.class);

    public static String sendRequest(String urlParam, String requestType) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(urlParam);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(requestType);
            connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setConnectTimeout(1000);
            connection.setReadTimeout(3000);
            connection.setUseCaches(false);
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                StringBuilder builder = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
                return builder.toString();
            }

        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }

    public static void main(String[] args) {
        Instant one = Instant.now();
        for (int i = 0; i < 1000; i++) {
            sendRequest("https://www.baidu.com", "GET");
        }
        Instant two = Instant.now();
        System.out.println(two.toEpochMilli() - one.toEpochMilli());
    }
}
