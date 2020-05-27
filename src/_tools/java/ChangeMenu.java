import com.umi361._globalConstants.GlobalConstants;
import com.umi361._utils.HttpsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

/**
 * 读取工程目录 wechat_properties 的 menu.json 并向微信服务器请求修改 menu
 */


@ContextConfiguration("classpath:spring.xml")
public class ChangeMenu extends AbstractTestNGSpringContextTests {

    @Autowired
    private GlobalConstants globalConstants;

    private static final String url = "https://api.weixin.qq.com/cgi-bin/menu/create";

    @Test
    public void doChange() {
        try {
            Map<String, String> params = new HashMap<String, String>();
            params.put("access_token", globalConstants.getAccessToken());
            BufferedReader reader = new BufferedReader(new FileReader("_文档/wechat_properties/menu.json"));
            StringBuilder menu = new StringBuilder();
            String str;
            while((str = reader.readLine()) != null) {
                menu.append(str);
            }
            System.out.println(HttpsUtils.sendPOST(url, menu.toString(), params));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
