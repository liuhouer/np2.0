package cn.northpark.test.ret;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author bruce
 * @date 2024年03月04日 09:28:46
 */
@Slf4j
public class DynamicPageScraper {

    @Test
    public void WebCrawler() {
        String url = "https://y.qq.com/n/ryqq/playlist/8820405070";

        try (WebClient webClient = new WebClient(BrowserVersion.CHROME)) {
            // 启用 JavaScript 执行
            webClient.getOptions().setJavaScriptEnabled(true);

            // 禁用 CSS 以加快页面加载速度
            webClient.getOptions().setCssEnabled(false);

            // 获取页面
            HtmlPage page = webClient.getPage(url);

            // 使用 XPath 定位对应的标签
            HtmlElement playCountElement = (HtmlElement) page.getFirstByXPath("//ul[@class='data__info']/li[@class='data_info__item']");

            if (playCountElement != null) {
                // 提取标签内容
                String playCount = playCountElement.getTextContent();

                // 打印播放量
                System.out.println("播放量：" + playCount);
            } else {
                System.out.println("未找到播放量信息");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void detailId() {
//        String detailId ="257693_3_186_1";
//        if(detailId.lastIndexOf(detailId,'_')!=-1){
//            detailId = StringUtils.substring(detailId,StringUtils
//                    .lastIndexOf(detailId,'_')+1, detailId.length());
//        }
//        log.info("detailId====={}",detailId);

        String input = "257693_3";
        String detailId = extractDetailId(input);
        System.out.println(detailId);  // 输出：1_5
    }

    public String extractDetailId(String input) {
        String[] parts = input.split("_");
        if (parts.length >= 3) {
            StringBuilder detailId = new StringBuilder(parts[parts.length - 1]);
            for (int i = parts.length - 2; i >= 2; i--) {
                detailId.insert(0, parts[i] + "_");
            }
            return detailId.toString();
        } else {
            // 如果字符串格式不符合要求，可以返回null或者抛出异常等处理
            return null;
        }
    }

}
