package com.umi361._wechatBasicService.accessToken;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.umi361._globalConstants.GlobalConstants;
import com.umi361._utils.HttpsUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 该基本服务类提供以下服务
 * 用户点击微信回调链接时的 code 解析：
 *      参数说明：
 *      参数	    是否必须    说明
 *      appid	    是	        公众号的唯一标识
 *      secret	    是	        公众号的appsecret
 *      code	    是	        填写第一步获取的code参数
 *      grant_type	是	        填写为authorization_code
 *      返回 JSON：
 *      access_token	网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同
 *      expires_in	    access_token接口调用凭证超时时间，单位（秒）
 *      refresh_token	用户刷新access_token
 *      openid	        用户唯一标识，请注意，在未关注公众号时，用户访问公众号的网页，也会产生一个用户和公众号唯一的OpenID
 *      scope	        用户授权的作用域，使用逗号（,）分隔
 *
 *
 */
@Service
public class OAuthAccessTokenCodeResolver {
    @Autowired
    private GlobalConstants globalConstants;
    private static final String ResolveCodeURL = "https://api.weixin.qq.com/sns/oauth2/access_token";
    private Logger logger = LogManager.getLogger(getClass());
    private final Map<String, String> params_OAuthByCode = new LinkedHashMap<>();
    @PostConstruct
    public void init() {
        params_OAuthByCode.put("appid", globalConstants.getAppid());
        params_OAuthByCode.put("secret", globalConstants.getAppsecret());
        // code 需要填入
        params_OAuthByCode.put("code", null);
        params_OAuthByCode.put("grant_type", "authorization_code");
    }

    /**
     * @return 出错时返回 null
     */
    @Nullable
    synchronized public CodeResolvedData resolveCode(String code) {
        params_OAuthByCode.put("code", code);
        CodeResolvedData result = null;
        try {
            String retJsonStr = HttpsUtils.sendGET(ResolveCodeURL, params_OAuthByCode);
            ObjectMapper mapper = new ObjectMapper();
            try {
                result = mapper.readValue(retJsonStr, new TypeReference<CodeResolvedData>(){});
            } catch(Exception e) {
                logger.error(new Date() + "\tJson returned from OAuth code resolve did not contain openid, malformed");
                logger.error("\t\t" + retJsonStr);
                logger.error("\t\t" + e);
                result = null;
            }
        } catch (IOException e) {
            logger.error(new Date() + "\tFailed to resolve code for OAuth, IOException");
            e.printStackTrace();
        }
        return result;
    }
}















