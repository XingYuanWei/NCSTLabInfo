package com.umi361._utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import javax.net.ssl.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;

public class HttpsUtils {
    private static Logger logger = LogManager.getLogger(HttpsUtils.class);

    public static String sendGET(String url, Map<String, String> params) throws IOException {
        return sendGET(url, params, "utf-8");
    }
    public static String sendGET(String url, Map<String, String> params, String charSet) throws IOException {
        BufferedReader in = null;
        StringBuilder result = new StringBuilder("");
        try {
            SSLContext ctx = null;
            try {
                ctx = SSLContext.getInstance("TLS");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            try {
                ctx.init(new KeyManager[0], new TrustManager[]{ new DefaultTrustManager()},
                        new SecureRandom());
            } catch (KeyManagementException e) {
                e.printStackTrace();
            }
            SSLContext.setDefault(ctx);

            String urlWithParams = buildURL(url, params);
            URL realURL = null;
            try {
                realURL = new URL(urlWithParams);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            //打开和 URL 之间的连接
            HttpsURLConnection connection = (HttpsURLConnection) realURL.openConnection();
            //设置通用属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setHostnameVerifier(new HostnameVerifier() {
                public boolean verify(String s, SSLSession sslSession) {
                    return true;
                }
            });

            //建立实际连接
            connection.connect();

            // TODO 获取响应的所有头部字段
            Map<String, List<String>> map = connection.getHeaderFields();

            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), charSet));
            String line;
            while((line = in.readLine())!=null) {
                result.append(line);
            }
        } catch (IOException e) {
            throw e;
        } finally {
            if (in != null)
                try {
                    in.close();
                } catch (IOException e) {
                    logger.error("GET 请求发送出现IO异常");
                    e.printStackTrace();
                }
        }
        return result.toString();
    }

    public static String sendPOST(String url, String content, Map<String, String> params) throws IOException {
        return sendPOST(url, content, params, "utf-8");
    }
    public static String sendPOST(String url, String content, Map<String, String> params, String charSet) throws IOException {
        BufferedReader in = null;
        PrintWriter out = null;
        StringBuilder result = new StringBuilder("");
        try {
            SSLContext ctx = null;
            try {
                ctx = SSLContext.getInstance("TLS");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            try {
                ctx.init(new KeyManager[0], new TrustManager[]{ new DefaultTrustManager()},
                        new SecureRandom());
            } catch (KeyManagementException e) {
                e.printStackTrace();
            }
            SSLContext.setDefault(ctx);

            String urlWithParmas = buildURL(url, params);
            URL realURL = null;
            try {
                realURL = new URL(urlWithParmas);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            //打开和 URL 之间的连接
            HttpsURLConnection connection = (HttpsURLConnection) realURL.openConnection();
            //设置通用属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setHostnameVerifier(new HostnameVerifier() {
                public boolean verify(String s, SSLSession sslSession) {
                    return true;
                }
            });

            //POST 设置
            connection.setDoOutput(true);
            connection.setDoInput(true);

            //发送 POST 请求体
            out = new PrintWriter(connection.getOutputStream());
            out.print(content);
            //刷新输出流，实际操作
            out.flush();

            // TODO 获取响应的所有头部字段
            Map<String, List<String>> map = connection.getHeaderFields();

            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), charSet));
            String line;
            while((line = in.readLine())!=null) {
                result.append(line);
            }
        } catch (IOException e) {
            throw e;
        } finally {
            if (out != null)
                out.close();
            if (in != null)
                try {
                    in.close();
                } catch (IOException e) {
                logger.error("POST 请求发送出现异常");
                    e.printStackTrace();
                }
        }
        return result.toString();
    }

    @NotNull
    public static String buildURL(String url, Map<String, String> params) {
        StringBuilder urlBuilder = new StringBuilder(url);
        if (params == null) return url;
        int paramNum = 0;
        for (String paramName : params.keySet()) {
            if (paramNum == 0) urlBuilder.append('?'); else urlBuilder.append('&'); paramNum++;
            if (paramName == null) continue;

            String paramValue = params.get(paramName);
            if (paramValue == null) paramValue = "";
            urlBuilder.append(paramName).append('=').append(paramValue);
        }
        return urlBuilder.toString();
    }


    /**
     * SSL TrustManager 简易实现类
     */
    private static class DefaultTrustManager implements X509TrustManager {
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }
}
