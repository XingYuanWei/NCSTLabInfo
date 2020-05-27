package com.umi361._wechatAPI.userInfoAPI;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.umi361._globalConstants.GlobalConstants;
import com.umi361._utils.HttpsUtils;
import com.umi361._wechatBasicService.accessToken.CodeResolvedData;
import com.umi361.domain.userInfo.UserInfoWechat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 开发者可通过OpenID来获取用户基本信息（仅当用户订阅了公众号时）
 * 特别需要注意的是，如果开发者拥有多个移动应用、网站应用和公众帐号，可通过获取用户基本信息中的unionid来区分用户的唯一性
 *      只要是同一个微信开放平台帐号下的移动应用、网站应用和公众帐号，用户的unionid是唯一的
 *      换句话说，同一用户，对同一个微信开放平台下的不同应用，unionid是相同的
 * JSON 结构示例见 DO UserInfoWechat
 */
@Service
public class APIUserInfoWechat {
    @Autowired
    private GlobalConstants globalConstants;
    private static final String APIUserInfoURL = "https://api.weixin.qq.com/cgi-bin/user/info";
    private static Map<String, String> params = new LinkedHashMap<String, String>();

    /**
     * access_token	    是	    调用接口凭证
     * openid	        是	    普通用户的标识，对当前公众号唯一
     * lang	            否	    返回国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语
     *                          由构造器 grab 方法进行设置；此处初始化为 null
     */
    @PostConstruct
    private void init() {
        params.put("access_token", globalConstants.getAccessToken());
        params.put("openid", null);
        // 默认返回 zh_CN 中文格式
        params.put("lang", "zh_CN");
    }

    /**
     * 为 APIOAuth 提供视图
     */
    @Autowired
    private APIOAuthUserInfoWechat apioAuthUserInfoWechat;
    public UserInfoWechat grabUserInfoWechatByCode(String code) {
        return grabUserInfoWechatByCode(code, "zh_CN");
    }
    public UserInfoWechat grabUserInfoWechatByCode(String code, String lang) {
        return apioAuthUserInfoWechat.grabUserInfoWechatByCode(code, lang);
    }
    public UserInfoWechat grabUserInfoWechatByCodeResolvedData(CodeResolvedData codeResolvedData) {
        return grabUserInfoWechatByCodeResolvedData(codeResolvedData, "zh_CN");
    }
    public UserInfoWechat grabUserInfoWechatByCodeResolvedData(CodeResolvedData codeResolvedData, String lang) {
        return apioAuthUserInfoWechat.grabUserInfoWechatByCodeResolvedData(codeResolvedData, lang);
    }

    /**
     * 通过 openid 获取 wechat 用户信息实体类，默认语言格式 zh_CN
     * @param openid 用户 openid
     * @return 注意，如果用户不是公众号订阅者，返回的实体类中只含有 openid 字段
     */
    public UserInfoWechat grabUserInfoWechatByOpenid(String openid) {
        return grabUserInfoWechatByOpenid(openid, "zh_CN");
    }

    public UserInfoWechat grabUserInfoWechatByOpenid(String openid, String lang) {
        Map<String, Object> map;
        UserInfoWechat userInfo = null;
        String retJsonStr = null;
        params.put("openid", openid);
        params.put("lang", lang);
        try {
            retJsonStr = HttpsUtils.sendGET(APIUserInfoURL, params);
        } catch (IOException e) {
            logger.error(new Date() + "\tRequest for user's wechat info by APIUserInfoWechat failed due to IOException", e);
        }
        if (retJsonStr == null) {
            logger.error(new Date() + "\tJSON returned from request for user's wechat info by APIUserInfoWechat was null");
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            map = objectMapper.readValue(retJsonStr, new TypeReference<Map<String, Object>>(){});
            userInfo = objectMapper.readValue(retJsonStr, UserInfoWechat.class);
            Integer subscribe = (Integer)map.get("subscribe");
            if (subscribe.equals(0)) {
                logger.info(new Date() + "\tJSON returned from request for user's wechat info by APIUserInfoWechat did not obtain useful info");
                logger.info("\tErrorCode: " + map.get("errcode") + "; ErrorMsg: " + map.get("errmsg"));
                return null;
            }
        } catch (IOException e) {
            logger.error(new Date() + "\tJSON returned from request for user's wechat info by APIUserInfoWechat can not be resolved, malformed", e);
        }
        return userInfo;
    }

    // TODO 批量拉取用户信息

    private static Logger logger = LogManager.getLogger(APIUserInfoWechat.class);
}
























