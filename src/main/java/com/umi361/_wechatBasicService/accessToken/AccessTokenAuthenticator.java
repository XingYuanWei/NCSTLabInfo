package com.umi361._wechatBasicService.accessToken;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.umi361._globalConstants.GlobalConstants;
import com.umi361._utils.HttpsUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;


/**
 * access_token是公众号的全局唯一接口调用凭据，公众号调用各接口时都需使用access_token
 * 开发者需要进行妥善保存
 *      access_token 的存储至少要保留512个字符空间
 *      access_token 的有效期目前为2个小时，需定时刷新
 *      重复获取将导致上次获取的 access_token 失效
 * 关于接口的返回 JSON 格式，详见 AccessTokenData 类
 *
 * 该类使用 spring 任务调度机制自行运行
 *
 * 注意设置 ip 白名单
 */
@Service
//@EnableAsync
//@EnableScheduling
public class AccessTokenAuthenticator {
    /**
     * 微信 accessToken 请求接口的相关信息
     */
    private static final String ACQUIRE_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";
    /**
     * params 用于存放调用请求接口的 API 参数：
     * 参数 	    是否必须 	说明
     * grant_type	是	        获取 access_token 填写 client_credential
     * appid	    是	        第三方用户唯一凭证
     * secret	    是	        第三方用户唯一凭证密钥，即 appsecret
     *
     */
    private static final Map<String, String> params = new LinkedHashMap<String, String>();
    private static Logger logger = LogManager.getLogger(AccessTokenAuthenticator.class);
    @Autowired
    private GlobalConstants globalConstants;
    /**
     * 初始化参数操作，使用 postConstruct
     */
    @PostConstruct
    private void init() {
        //获取access_token填写client_credential
        params.put("grant_type", "client_credential");
        params.put("appid", globalConstants.getAppid());
        params.put("secret", globalConstants.getAppsecret());
        refreshToken();
    }

    private static AccessTokenData currAccessTokenData = null;
    private static List<AccessTokenData> historyAccessTokens = new LinkedList<>();
    /**
     * 记录最近一次刷新刷新的时间戳；初始化值为负数表示从未刷新过
     * expiresIn 代表 expires_in，用于结合 lastRequestTime 判断是否需要再次刷新
     */
    private static long lastRequestTime = - Long.MAX_VALUE;
    private static long expiresIn = 7200000L;

    /**
     * 设定程式的默认刷新时间间隔为 3600s（微信默认保证 7200s 内 AccessToken 有效）
     */
    private static final long interval = 3600000L;
    /**
     * 设定程式的默认自动检查时间间隔为 30 分钟
     */
    private static final long autoCheckDelay = 1800000L;
    /**
     * 当多次请求均发生失败时，标记 stuck，autoCheck 程式负责尝试唤醒
     */
    private static boolean stuck = false;


    /**
     * 判断给定的 AccessTokenData 参数是否可用
     * 注意，如果是从历史 accessToken 当中检索可用的，必须从后往前检查
     */
    private static boolean assertAccessTokenDataAvailable(AccessTokenData accessTokenData) {
        if (currAccessTokenData == null) {
            if (accessTokenData.getAcquireTime() + accessTokenData.getExpireIn() > new Date().getTime()) {
                return true;
            } else {
                return false;
            }
        } else if (accessTokenData == currAccessTokenData) {
            return accessTokenData.getAcquireTime() + accessTokenData.getExpireIn() > new Date().getTime();
        } else {
            return false;
        }
    }

    /**
     * 自动检查线程，初次启动延时 10 s
     * 尝试处理 stuck 状态
     */
    @Scheduled(initialDelay = 10000L, fixedDelay = autoCheckDelay)
    synchronized private static void autoCheck() {
        if (stuck) {
            AccessTokenData newData = requestForAccessToken();
            if (assertAccessTokenDataAvailable(newData)) {
                stuck = false;
                stuckTimes = 0;
                currAccessTokenData = newData;
                if (historyAccessTokens.size() < 10) historyAccessTokens.add(newData);
                else {
                    historyAccessTokens = new LinkedList<>(historyAccessTokens.subList(0, 5));
                    historyAccessTokens.add(newData);
                }
                lastRequestTime = new Date().getTime();
                logger.info(new Date() + "\tautoCheck has resolved stuck status, Refreshed accessToken: " + currAccessTokenData);
            } else {
                logger.error(new Date() + "\tWarning, progress still stuck on AccessTokenAuthenticator");
            }
        }
        if (currAccessTokenData == null) {
            logger.error(new Date() + "\tAuto check: access_token invalid, scheduled task may have crashed, trying to refresh");
            refreshToken();
        } else if (!assertAccessTokenDataAvailable(currAccessTokenData)) {
            logger.error(new Date() + "\tAuto check: access_token last requested expired, scheduled task may have crashed, refreshing");
            refreshToken();
        }
    }

    /**
     * 仅当进程未 stuck 时才工作
     * 更新 currAccessTokenData、historyAccessTokens、lastRequestTime 以及 stuckTimes
     */
    @Scheduled(fixedDelay = interval)
    synchronized private static void refreshToken() {
        AccessTokenData newData = requestForAccessToken();
        if (!stuck) {
            if (newData != null && !(newData.getAccessToken().length() == 0)) {
                stuckTimes = 0;
                currAccessTokenData = newData;
                if (historyAccessTokens.size() < 10) historyAccessTokens.add(newData);
                else {
                    historyAccessTokens = new LinkedList<>(historyAccessTokens.subList(0, 5));
                    historyAccessTokens.add(newData);
                }
                lastRequestTime = new Date().getTime();
                logger.info(new Date() + "\tRefreshed accessToken: " + currAccessTokenData);
            } else {
                logger.error(new Date() + "\tRefresh went wrong. To refresh access_token, authenticator is trying once more");
                // 递归调用
                refreshToken();
            }
        }
    }


    /**
     * 提供接口
     * 获取一个基本保证有效的 access_token
     * @return access_token
     */
    synchronized public static String getAccessTokenSafely() {
        AccessTokenData ret = currAccessTokenData;
        if (ret != null && lastRequestTime + expiresIn > new Date().getTime()) {
            return ret.getAccessToken();
        } else if (ret != null) {
            if (!stuck) {
                refreshToken();
                return getAccessTokenSafely();
            } else {
                logger.error(new Date() + "\tFailed to return AccessToken safely, returned null");
                return null;
            }
        } else {
            logger.error(new Date() + "\taccess_token invalid, scheduled task may have crashed, trying to return from history");
            for (int i = historyAccessTokens.size() - 1; i >= 0; i++) {
                ret = historyAccessTokens.get(i);
                if (assertAccessTokenDataAvailable(ret)) {
                    return ret.getAccessToken();
                }
            }
        }
        logger.error(new Date() + "\tFailed to return AccessToken safely, returned null");
        return null;
    }


    /**
     * errCodeMsg 保存所有错误代码对应的错误信息
     */
    private static Map<String, String> errCodeMsg = new HashMap<>();
    static {
        errCodeMsg.put("-1", "系统繁忙，此时请开发者稍候再试");
        errCodeMsg.put("40001", "AppSecret错误或者AppSecret不属于这个公众号，请开发者确认AppSecret的正确性");
        errCodeMsg.put("40002", "请确保grant_type字段值为client_credential");
        errCodeMsg.put("40164", "调用接口的IP地址不在白名单中，请在接口IP白名单中进行设置");
    }

    /**
     * 每当 refreshToken 失败，便进行一次计数，requestForAccessToken、refreshToken 将停止工作，
     */
    private static int stuckTimes = 0;
    /**
     * 自动刷新 access_token 时所调用的方法
     * 如果超过一定次数，同时标记 stuck 状态阻断
     * @return 处理失败时，返回 null
     */
    synchronized private static AccessTokenData requestForAccessToken() {
        AccessTokenData accessTokenData = null;
        String retJsonStr = null;
        if (stuck) return null;
        try {
             retJsonStr = HttpsUtils.sendGET(ACQUIRE_ACCESS_TOKEN_URL, params);
        } catch (IOException e) {
            logger.error(new Date() + "\tRequest for access_token failed due to HTTPS IOException", e);
        }
        if (retJsonStr == null) {
            logger.error(new Date() + "\tJSON returned from request for access_token was null");
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, String> map = objectMapper.readValue(retJsonStr, new TypeReference<Map<String, String>>(){});
            String accessToken = map.get("access_token");
            String expireRemainTime = map.get("expires_in");
            if (accessToken != null && expireRemainTime != null) {
                accessTokenData = new AccessTokenData(accessToken, Long.valueOf(expireRemainTime));
                expiresIn = Long.parseLong(expireRemainTime);
            } else {
                String errCode = map.get("errcode"), errMsg = map.get("errmsg");
                logger.error(new Date() + "\tJSON returned from request for access_token did not obtain access_token");
                logger.error("\tErrorCode: " + map.get("errcode") + "; ErrorMsg: " + errCodeMsg.get(errCode) + "\n\t" + map.get("errmsg"));
            }
        } catch (IOException e) {
            logger.error(new Date() + "\tJSON returned from request for access_token can not be resolved, malformed" + e);
        }
        if (accessTokenData == null) stuckTimes++;
        if (stuckTimes > 3) {
            stuck = true; stuckTimes = 0;
            logger.error(new Date() + "\twarning, progress stuck on AccessTokenAuthenticator");
        }
        return accessTokenData;
    }
}


/**
 * 正常情况下，微信会返回下述JSON数据包给公众号：
 * {"access_token":"ACCESS_TOKEN","expires_in":7200}
 *      参数说明
 *      access_token	获取到的凭证
 *      expires_in	    凭证有效时间，单位：秒
 *
 * 错误时微信会返回错误码等信息，JSON数据包示例如下（该示例为AppID无效错误）:
 * {"errcode":40013,"errmsg":"invalid appid"}
 *      返回码说明
 *      -1	            系统繁忙，此时请开发者稍候再试
 *      0	            请求成功
 *      40001	        AppSecret错误或者AppSecret不属于这个公众号，请开发者确认AppSecret的正确性
 *      40002	        请确保grant_type字段值为client_credential
 *      40164           调用接口的IP地址不在白名单中，请在接口IP白名单中进行设置
 */
class AccessTokenData {
    private String accessToken;
    private long expireIn;
    /**
     * 记录 AccessToken 获取的时间
     */
    private long acquireTime = new Date().getTime();

    AccessTokenData(String accessToken, long expireIn) {
        this.accessToken = accessToken;
        this.expireIn = expireIn;
    }

    String getAccessToken() {
        return accessToken;
    }

    long getExpireIn() {
        return expireIn;
    }

    public long getAcquireTime() { return acquireTime; }

    @Override
    public String toString() {
        return "AccessTokenData{" +
                "accessToken='" + accessToken + '\'' +
                ", expireIn=" + expireIn +
                ", acquireTime=" + acquireTime +
                '}';
    }
}