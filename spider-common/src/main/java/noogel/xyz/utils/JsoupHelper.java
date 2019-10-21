package noogel.xyz.utils;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Instant;

public class JsoupHelper {
    private static final Logger logger = LoggerFactory.getLogger(JsoupHelper.class);

    public static String sendRequest(String url) {
        String html = null;
        try {
            Connection connect = Jsoup.connect(url);
            html = connect.get().body().html();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return html;
    }

    public static void main(String[] args) {
        Instant one = Instant.now();
        for (int i = 0; i < 1000; i++) {
            sendRequest("https://www.baidu.com");
        }
        Instant two = Instant.now();
        System.out.println(two.toEpochMilli() - one.toEpochMilli());
        // 38386
        // 47494
    }
}
