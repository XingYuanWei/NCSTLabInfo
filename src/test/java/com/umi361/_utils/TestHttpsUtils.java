package com.umi361._utils;

import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class TestHttpsUtils {
    static String url = "https://api.weixin.qq.com/cgi-bin/token";
    static Map<String, String> params = new HashMap<String, String>();
    static {
        params.put("grant_type", "client_credential");
        params.put("appid", "wx87b4a14452135c7c");
        params.put("secret", "661acc53ce8f8585702a5c245697ce30");
    }

    @Test
    public void buildURL() {
        System.out.println(HttpsUtils.buildURL(url, params));
    }

    @Test
    public void sendGET() {
        try {
            System.out.println(HttpsUtils.sendGET(url, params));
        } catch (IOException e) {
            System.exit(1);
        }
    }
}
