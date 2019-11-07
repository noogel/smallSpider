package noogel.xyz.utils;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class OkHttpHelper {
    private static final Logger logger = LoggerFactory.getLogger(OkHttpHelper.class);
    private static OkHttpClient CLIENT = new OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.SECONDS)
            .readTimeout(2, TimeUnit.SECONDS)
            .build();

    public static String sendRequest(String urlParam) {
        Request request = new Request.Builder()
                .url(urlParam)
                .build();
        try (Response response = CLIENT.newCall(request).execute()) {
            return response.body().string();
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return null;
        }
    }

    public static void main(String[] args) {
        Instant one = Instant.now();
        for (int i = 0; i < 1000; i++) {
            sendRequest("https://www.baidu.com");
        }
        Instant two = Instant.now();
        System.out.println(two.toEpochMilli() - one.toEpochMilli());
        // 6429
        // 6457
    }
}
