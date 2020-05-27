package com.umi361._utils;

import com.umi361._globalConstants.GlobalConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class URLBuilder {
    @Autowired
    GlobalConstants globalConstants;
    private static final String O_AUTH_URL = "https://open.weixin.qq.com/connect/oauth2/authorize";
    // O_AUTH_URL 访问时所需 GET 参数的模板
    private static final Map<String, String> O_AUTH_PARAM_TML = new LinkedHashMap<>();
    @PostConstruct
    public void init() {
        O_AUTH_PARAM_TML.put("appid", globalConstants.getAppid());
        O_AUTH_PARAM_TML.put("redirect_uri", null);
        O_AUTH_PARAM_TML.put("response_type", "code");
        O_AUTH_PARAM_TML.put("scope", null);
        O_AUTH_PARAM_TML.put("state", null);
    }

    /**
     * 将普通的 uri 解析成 url；如果 uri 已经包含 contextPath，需要制定 containsContextPath 参数为 true
     */
    public String convert_uri_to_url(String uri, boolean containsContextPath) {
        return convert_uri_to_url(uri, containsContextPath, globalConstants.getProtocol());
    }
    public String convert_uri_to_url(String uri, boolean containsContextPath, String protocol) {
        StringBuilder ret = new StringBuilder();
        ret.append(protocol).append("://").append(globalConstants.getDomainHost())
                .append(containsContextPath ? "" : "/ncstlabinfo").append(uri);
        return ret.toString();
    }


    /**
     * 生成 OAuth 回调 URL
     * @param redirect_url 需要被回调的 URL
     * @param state state 参数
     * @param isBaseScope 该回调是否为 base scope，不是则为 userinfo scope
     */
    public String buildOAuthURL(String redirect_url, String state, boolean isBaseScope) {
        Map<String, String> params = new LinkedHashMap<>(O_AUTH_PARAM_TML);
        try {
            params.put("redirect_uri", URLEncoder.encode(redirect_url, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        params.put("scope", isBaseScope ? "snsapi_base" : "snsapi_userinfo");
        params.put("state", state);
        return HttpsUtils.buildURL(O_AUTH_URL, params).concat("#wechat_redirect");
    }
    /**
     * @param redirect_uri        微信回调之后的请求的 uri，例如：/ncstlabinfo/wechat/show.jsp
     * @param state      回调页 state 参数
     * @param isBaseScope 真表示 base，假表示 userinfo
     * @return 请求 URL
     */
    public String buildOAuthURLByURI(String redirect_uri, String state, boolean containsContextPath, boolean isBaseScope) {
        return buildOAuthURL(convert_uri_to_url(redirect_uri, containsContextPath), state, isBaseScope);
    }
    public String buildOAuthURLByURI(String redirect_uri, String state, boolean isBaseScope) {
        return buildOAuthURL(convert_uri_to_url(redirect_uri, true), state, isBaseScope);
    }
}
