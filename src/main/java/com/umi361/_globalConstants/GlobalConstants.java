package com.umi361._globalConstants;

import com.umi361._wechatBasicService.accessToken.AccessTokenAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GlobalConstants {
    @Autowired
    private ConstantsBean constantsBean;

    /**
     * token 为微信首次验证服务器时填写的 token
     */
    public String getToken() { return constantsBean.token; }

    public String getServerIP() { return constantsBean.serverIP; }

    public String getDomainHost() { return constantsBean.domainHost; }

    public String getProtocol() { return constantsBean.protocol; }

    public String getAppid() { return constantsBean.appid; }

    public String getAppsecret() { return constantsBean.appsecret; }

    public String getPanelLoginSuccessUrl() {return constantsBean.successUrl;}

    /**
     * 保证 AccessTokenAuthenticator 已经被正确注入 spring 容器
     */
    @Autowired
    private AccessTokenAuthenticator accessTokenAuthenticator;
    public String getAccessToken() { return AccessTokenAuthenticator.getAccessTokenSafely(); }
}

@Component
class ConstantsBean {
    @Value("${wechat.serverIP}")
    String serverIP;

    @Value("${wechat.domainHost}")
    String domainHost;

    @Value("${wechat.protocol}")
    String protocol;



    @Value("${wechat.token}")
    String token;

    @Value("${wechat.appid}")
    String appid;

    @Value("${wechat.appsecret}")
    String appsecret;




    @Value("${shiro.successUrl}")
    String successUrl;

}