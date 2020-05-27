package com.umi361._wechatAPI.userInfoAPI;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.umi361._globalConstants.GlobalConstants;
import com.umi361._utils.HttpsUtils;
import com.umi361._wechatBasicService.accessToken.CodeResolvedData;
import com.umi361.domain.userInfo.UserInfoWechat;
import com.umi361._wechatBasicService.accessToken.OAuthAccessTokenCodeResolver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 与 APIUserInfoWechat 不同，该类通过 OAuth 访问授权，在未订阅用户访问 OAuth 授权回调页面时，可以经过用户同意收集用户信息
 *      其中，授权回调页面可以有两种作用域：snsapi_base 和 snsapi_userinfo；要想收集未订阅用户的的详细信息，需要指定 scope 为后者
 *      业务流程：要想让用户访问某一业务页面时收集信息，需要首先通过 GenerateOAuthURL 生成的 scope 为 snsapi_userinfo 的授权回调页面 URL，此时会产生一个 OAuth access_token code
 *      1 第一步：用户同意授权，获取code
 *          如果用户同意授权，页面将跳转至 redirect_uri/?code=CODE&state=STATE。
 *          code说明 ： code作为换取access_token的票据，每次用户授权带上的code将不一样，code只能使用一次，5分钟未被使用自动过期
 *      2 第二步：通过code换取 openid、scope、以及可能的网页授权 access_token 的结果 map
 *          _wechatBasicService 下的 OAuthAccessTokenCodeResolver 负责
 *      3 第三步：拉取用户信息(需scope为 snsapi_userinfo 时的 AccessToken)
 *          参数说明
 *          参数	        描述
 *          access_token	网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同
 *          openid	        用户的唯一标识
 *          lang	        返回国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语
 *      4 附：通过 refresh_token 刷新access_token（如果需要）
 *          未实现
 *      5 附：检验授权凭证（access_token）是否有效
 *          未实现
 *
 */
@Service
class APIOAuthUserInfoWechat {
    @Autowired
    private GlobalConstants globalConstants;
    @Autowired
    private OAuthAccessTokenCodeResolver codeResolver;
    private Logger logger = LogManager.getLogger(getClass());
    private static final String GrabUserInfoWechatByCodeResolvedDataURL = "https://api.weixin.qq.com/sns/userinfo";

    /**
     * @param code 警告，code 不能是已经解析过的，否则出错
     */
    @Nullable
    UserInfoWechat grabUserInfoWechatByCode(String code, String lang) {
        CodeResolvedData codeResolvedData = codeResolver.resolveCode(code);
        return grabUserInfoWechatByCodeResolvedData(codeResolvedData, lang);
    }

    @Nullable
    UserInfoWechat grabUserInfoWechatByCodeResolvedData(CodeResolvedData codeResolvedData, String lang) {
        UserInfoWechat ret = null;
        String accessToken = codeResolvedData.access_token;
        String openid = codeResolvedData.openid;
        String scope = codeResolvedData.scope;
        if (!scope.startsWith("snsapi_userinfo")) {
            logger.error(new Date() + "\tscope for oAuth user info from wechat grab was : " + scope + ", must be snsapi_userinfo scope");
            logger.error("\t\t" + "returning null");
            return null;
        }
        Map<String, String> params = new LinkedHashMap<>();
        initParams(params, accessToken, openid, lang);

        try {
            String retJsonStr = HttpsUtils.sendGET(GrabUserInfoWechatByCodeResolvedDataURL, params);
            ObjectMapper mapper = new ObjectMapper();
            ret = mapper.readValue(retJsonStr, new TypeReference<UserInfoWechat>(){});
            if (ret == null || ret.getOpenid() == null) {
                logger.error(new Date() + "\tJson returned inside " + getClass().getCanonicalName() + " did not resolve, userInfoWechat was null, malformed");
                logger.error("\t\t" + retJsonStr);
            }
        } catch (IOException e) {
            logger.error(new Date() + "\tFailed to grab userInfoWechat by code, IOException");
            e.printStackTrace();
        }
        return ret;
    }

    private void initParams(Map<String, String> params, String accessToken, String openid, String lang) {
        params.put("access_token", accessToken);
        params.put("openid", openid);
        params.put("lang", lang);
    }

}






















