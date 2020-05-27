package com.umi361._wechatBasicService.wechatTokenValidator;

import com.umi361._utils.SignToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;


@Controller
public class TokenValidatorController {
	private static Logger logger = LogManager.getLogger(TokenValidatorController.class);

	@RequestMapping(value = "/tokeninitialvalidate", method = RequestMethod.GET, params = {"signature", "timestamp", "nonce", "echostr"})
	public void wechatInitialValidate(HttpServletResponse response, @RequestParam String signature,
	                                  @RequestParam String timestamp, @RequestParam String nonce, @RequestParam String echostr) {
		doValidate(response, signature, timestamp, nonce, echostr);
	}

    /**
     * 使用 SignToken 进行 signature 的检查
     * @param signature 微信服务器传递的用于验证的加密签名，与 timestamp、nonce 均有关
     * @param timestamp 时间戳参数
     * @param nonce 随机数
     * @param echostr 服务器验证 signature 有效时返回的字符串
     */
    protected void doValidate(HttpServletResponse response, String signature,
                                      String timestamp, String nonce, String echostr) {
        //判断服务器发送的 signature 有效
        if (SignToken.checkSignature(signature, timestamp, nonce)) {
            try {
                PrintWriter writer = response.getWriter();
                //回写 echostr
                writer.print(echostr);
                writer.close();
            } catch (IOException ioe) {
                logger.error(ioe, ioe);
            }
        } else {
            logger.info(TokenValidatorController.class.getCanonicalName() + "\tServerValidate 不符合预期\n" +
                    "\tSignature: " + signature + ", TimeStamp: " + timestamp + ", Nonce: " + nonce + ", EchoStr: " + echostr);
        }
    }
}
























