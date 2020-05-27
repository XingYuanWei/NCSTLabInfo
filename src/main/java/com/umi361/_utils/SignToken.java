package com.umi361._utils;


import com.umi361._globalConstants.GlobalConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

/**
 * 用于微信服务器首次验证
 */

@Component
public class SignToken {
    private static Logger logger = LogManager.getLogger(SignToken.class);
    @Autowired
    private GlobalConstants globalConstants;
    private static String token;
    @PostConstruct
    public void init() {
        token = globalConstants.getToken();
    }

    /**
	 * 验证签名，使用字典序排序 {token, timestamp, nonce} 然后计算 SHA-1，与 signature 进行比较
	 * @param signature 微信服务器传送的SHA-1字符串，长度为20，代表40个byte
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public static boolean checkSignature(String signature, String timestamp, String nonce) {
	    if (signature == null || token == null || timestamp == null || nonce == null) {
            logger.error("String Parameters for chechSignature can not be null.");
            return false;
        }
		String[] rawStrArr = { token, timestamp, nonce };
		Arrays.sort(rawStrArr);
		StringBuilder rawStr = new StringBuilder();
		for (int i = 0; i<rawStrArr.length; i++) {
			rawStr.append(rawStrArr[i]);
		}

		byte[] digested = null;
		MessageDigest messageDigest;
		try {
			messageDigest = MessageDigest.getInstance("SHA-1");
			digested = messageDigest.digest(rawStr.toString().getBytes());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return Arrays.equals(signToBytes(signature), digested);
	}

	/**
	 * 将SHA-1字符串转换为byte数组
	 * @param signature 待转换字符串
	 * @return
	 */
	public static byte[] signToBytes(@NotNull String signature) {
		signature = signature.toLowerCase();
		if(signature.length() != 40) { return null; }
		byte[] ret = new byte[20];
		for (int i = 0; i<signature.length(); i+=2) {
			int temp = hex.indexOf(String.valueOf(signature.charAt(i)));
			temp <<= 4;
			temp += hex.indexOf(String.valueOf(signature.charAt(i+1)));
			ret[i/2] = (byte)temp;
		}
		return ret;
	}
	static List<String> hex = Arrays.asList("0123456789abcdef".split(""));
}



















