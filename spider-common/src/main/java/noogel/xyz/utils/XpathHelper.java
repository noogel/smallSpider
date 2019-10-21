package noogel.xyz.utils;

import org.htmlcleaner.*;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.util.*;

/**
 * https://www.cnblogs.com/Kavlez/p/4049210.html
 */
public class XpathHelper {

    private static final Logger logger = LoggerFactory.getLogger(XpathHelper.class);

    public static Map<String, List<String>> parseContent(String html, String... exps) {
        HtmlCleaner htmlCleaner = new HtmlCleaner();
        TagNode tagNode = htmlCleaner.clean(html);
        try {
            Document document = new DomSerializer(new CleanerProperties()).createDOM(tagNode);
            XPath xPath = XPathFactory.newInstance().newXPath();
            Map<String, List<String>> resp = new HashMap<>();
            for (String exp : exps) {
                try {
                    NodeList evaluate = (NodeList) xPath.evaluate(exp, document, XPathConstants.NODESET);
                    List<String> nodeList = new ArrayList<>();
                    for (int i = 0; i < evaluate.getLength(); i++) {
                        Node node = evaluate.item(i);
                        nodeList.add(node.getNodeValue() == null ? node.getTextContent() : node.getNodeValue());
                    }
                    resp.put(exp, nodeList);
                } catch (XPathExpressionException e) {
                    logger.error(e.getMessage(), e);
                }
            }
            return resp;
        } catch (ParserConfigurationException ex) {
            logger.error(ex.getMessage(), ex);
        }
        return Collections.emptyMap();
    }

    public static void main(String[] args) throws IOException, XPatherException, ParserConfigurationException, XPathExpressionException {
        String url = "http://zhidao.baidu.com/daily";
        String exp = "//li/div[@class='daily-cont']/div[@class='daily-cont-top']/h2/a";

        String html = null;
        try {
            Connection connect = Jsoup.connect(url);
            html = connect.get().body().html();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(parseContent(html, exp));
    }
}
