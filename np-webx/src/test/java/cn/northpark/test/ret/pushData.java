package cn.northpark.test.ret;

import cn.northpark.utils.HttpGetUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * @author bruce
 * @date 2022年08月19日 17:53:06
 */
@Slf4j
public class pushData {
    public static void main(String[] args) {

        for (int i = 31; i <=32 ; i++) {
            String path = "C:\\Users\\Bruce\\Documents\\page"+i+".txt";
            try {
                String jsonData = FileUtils.readFileToString(new File(path));

                log.info("爬取的数据----》,{}", jsonData);
                String url = "https://northpark.cn/ret/soft/data";

                String result = HttpGetUtils.sendPostJsonData(url, jsonData);
                while (!result.contains("200")){
                    result = HttpGetUtils.sendPostJsonData(url, jsonData);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }





    }
}
