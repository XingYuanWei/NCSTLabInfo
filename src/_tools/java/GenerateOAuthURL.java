import com.umi361._globalConstants.GlobalConstants;
import com.umi361._utils.HttpsUtils;
import org.javatuples.Triplet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.*;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;


/**
 * 本工具类用于生成授权回调域名对应的访问 URL
 * 注意：授权回调域名配置规范为全域名，因此仅支持 www 站点名的域名
 *
 * 在确保微信公众账号拥有授权作用域（scope参数）的权限的前提下（服务号获得高级接口后，默认拥有scope参数中的snsapi_base和snsapi_userinfo），引导关注者打开如下页面：
 * https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect 若提示“该链接无法访问”，请检查参数是否填写错误，是否拥有scope参数对应的授权作用域权限。
 * 尤其注意：由于授权操作安全等级较高，所以在发起授权请求时，微信会对授权链接做正则强匹配校验，如果链接的参数顺序不对，授权页面将无法正常访问
 * 尤其注意：跳转回调redirect_uri，应当使用https链接来确保授权code的安全性
 *
 * 参数说明
 * 参数名     是否必须  描述
 * appid	        是	公众号的唯一标识
 * redirect_uri	    是	授权后重定向的回调链接地址，请使用urlEncode对链接进行处理
 * response_type	是	返回类型，请填写code
 * scope	        是	应用授权作用域，snsapi_base （不弹出授权页面，直接跳转，只能获取用户openid），snsapi_userinfo （弹出授权页面，可通过openid拿到昵称、性别、所在地。并且，即使在未关注的情况下，只要用户授权，也能获取其信息）
 * state	        否	重定向后会带上state参数，开发者可以填写a-zA-Z0-9的参数值，最多128字节
 * #wechat_redirect	是	无论直接打开还是做页面302重定向时候，必须带此参数
 *
 * （如果用户同意授权）页面将跳转至 redirect_uri/?code=CODE&state=STATE
 */
@ContextConfiguration("classpath:spring.xml")
public class GenerateOAuthURL extends AbstractTestNGSpringContextTests {
    @Autowired
    private GlobalConstants globalConstants;
    private String serverIP;
    private String domainHost;
    private String protocol;
    private String contextPath = "ncstlabinfo";
    private static final String OAuthURL = "https://open.weixin.qq.com/connect/oauth2/authorize";

    /**
     * 适配 HttpsUtils.buildURL 方法所用的 map
     */
    private static final Map<String, String> initialParams = new LinkedHashMap<String, String>();

    /**
     * 存放多组 URI 数据的 set
     * 第二个参数 state 可能为空
     */
    private Set<Triplet<String, String, String>> URL_STATE_SCOPE = new LinkedHashSet<Triplet<String, String, String>>();


    @Test
    public void deGenerate() throws Exception {
        PrintWriter writer = new PrintWriter(new File("_文档/wechat_properties/OAuthURLOutput.txt"));
        for (Triplet<String, String, String> each : URL_STATE_SCOPE) {
            String orig_url = each.getValue0();
            String url = URLEncoder.encode(orig_url, "UTF-8");
            String state = each.getValue1();
            String scope = each.getValue2();

            initialParams.put("redirect_uri", url);
            initialParams.put("scope", scope.equals("userinfo") ? "snsapi_userinfo" : "snsapi_base");
            initialParams.put("state", state);

            writer.write("scope: " + scope + "\n");
            writer.write("state arg: " + state + "\n");
            writer.write(orig_url + "\n");
            writer.write(HttpsUtils.buildURL(OAuthURL, initialParams).concat("#wechat_redirect") + "\n");
            writer.write("\n");
            writer.flush();
        }
        writer.close();
    }

    @BeforeClass
    private void inti() {
        initialParams.put("appid", globalConstants.getAppid());
        /*
         * 此处对应文本文件中应当填写的回调页面 URL
         */
        initialParams.put("redirect_uri", null);
        initialParams.put("response_type", "code");
        initialParams.put("scope", null);
        /*
         * 此处对应文本文件中应当填写的回调请求携带的参数，参数名为 state，参数支持 a-zA-Z0-9，最多 128 字节
         */
        initialParams.put("state", null);
        serverIP = globalConstants.getServerIP();
        domainHost = globalConstants.getDomainHost();
        protocol = globalConstants.getProtocol();
        loadURIOrig();
    }

    /**
     * 原始 OAuthURLOrig 文件的格式为：第一行 URI（ContextPath 之后的 '/' 开始，包括 '/'）、第二行 state 参数、第三行 base 或 userinfo 表示 scope
     * 允许 '//' 单行注释
     * 格式示范：
     *      /wechat/show.jsp
            state: wechat
            base

            // 省略 state 参数
            http://pub.ngrok.icesea.io/ncstlabinfo/wechat/show.jsp
            userinfo
     */
    private void loadURIOrig() {
        try {
            LineNumberReader reader = new LineNumberReader(new BufferedReader(new InputStreamReader(
                    new FileInputStream(new File("_文档/wechat_properties/OAuthURLOrig.txt"))
            )));

            /*
             * 以一组 URL 为单元进行循环
             */
            String temp;
            while((temp = reader.readLine())!=null) {
                String url = null, state = null, scope = null;
                temp = temp.trim();
                // 处理注释和空行
                if (temp.isEmpty() || temp.startsWith("//")) continue;

                boolean correct = true; int errLine = -1;
                if (!(temp.startsWith("/"))) { correct = false; errLine = reader.getLineNumber(); }
                url = preInitURI(temp);

                temp = reader.readLine().trim();
                if (temp.startsWith("state:")) {
                    state = temp.substring(temp.indexOf("state:") + "state:".length(), temp.length()).trim();
                    scope = reader.readLine().trim();
                    if (! (scope.equals("base") || scope.equals("userinfo"))) { correct = false; errLine = reader.getLineNumber(); }
                } else {
                    scope = temp;
                    if (! (scope.equals("base") || scope.equals("userinfo"))) { correct = false; errLine = reader.getLineNumber(); }
                }

                if(correct) {
                    URL_STATE_SCOPE.add(new Triplet<>(url, state, scope));
                } else {
                    throw new RuntimeException("OAuthURLOrig.txt 存在格式错误：第 " + errLine + " 行");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理 URI 为原始 URL
     */
    private String preInitURI(String uri) {
        return new StringBuilder().append(protocol).append("://").append(domainHost).append("/").append(contextPath).append(uri).toString();
    }
}
























