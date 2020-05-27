package com.umi361._wechatBasicService.wechatTokenMessageService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

@Controller
public class TokenMessageController {
    @RequestMapping(value = "/tokeninitialvalidate", method = RequestMethod.POST, params = {"signature", "timestamp", "nonce", "openid"})
    public void wechatInitialValidatePost(HttpServletResponse response, @RequestParam String signature,
                                          @RequestParam String timestamp, @RequestParam String nonce, @RequestParam String openid) {
        System.out.println("未处理的 message POST 信息 from " + openid);
    }
}
