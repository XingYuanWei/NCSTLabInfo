package com.umi361.controller.wechat;

import com.umi361.dao.college.CollegeProfessionDAO;
import com.umi361.dao.preference.DomainDAO;
import com.umi361.dao.preference.SpecialityDAO;
import com.umi361.dao.userInfo.UserInfoBasicDAO;
import com.umi361.dao.userInfo.UserInfoWechatDAO;
import com.umi361.service.userInfo.UserInfoRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

@Controller
public class RegisterController {
    @Autowired
    UserInfoWechatDAO userInfoWechatDAO;
    @Autowired
    UserInfoBasicDAO userInfoBasicDAO;
    @Autowired
    CollegeProfessionDAO collegeProfessionDAO;
    @Autowired
    SpecialityDAO specialityDAO;
    @Autowired
    DomainDAO domainDAO;
    @Autowired
    UserInfoRegisterService userInfoRegisterService;

    @RequestMapping(value = "/register.jsp", method = RequestMethod.GET)
    public String register(@SessionAttribute String openid, HttpServletRequest request) {
        try {
            request.setAttribute("userInfoWechat", userInfoWechatDAO.getUserInfoWechatByOpenid(openid));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        request.setAttribute("colleges", collegeProfessionDAO.getProfessionCollegePairListMap());
        request.setAttribute("specialities", specialityDAO.getSpecialityPairsList());
        request.setAttribute("domains", domainDAO.getDomainInterestPairListMap());
        return "register";
    }

    // Post 方法用于接受 register 表单数据

    /**
     * studentId
     * majorId 如果提交时没有匹配的数据项（一般不可能），默认值为 -1
     * realName
     * sex
     * speciality（nullable）
     * interest（nullable）
     * 以上参数为 $.post() 发送的 json 数据
     */
    @RequestMapping(value = "/register.jsp", method = RequestMethod.POST)
    public ResponseEntity processForm(@SessionAttribute String openid, HttpServletRequest request, @RequestParam String studentId,
                                      @RequestParam String majorId, @RequestParam String realName, @RequestParam String sex,
                                      @RequestParam(required = false, defaultValue = "") String speciality, @RequestParam(required = false, defaultValue = "") String interest) {
        long majorIdInt;
        int sexInt;
        long[] specialityArr;
        long[] interestArr;
        try {
            majorIdInt = Long.parseLong(majorId);
            sexInt = Integer.parseInt(sex);
            if (majorIdInt == -1 || sexInt > 2 || sexInt < 0) {
                throw new Exception();
            }
            specialityArr = speciality.isEmpty() ? new long[0] : handleStringArr(speciality.trim().split(" "));
            interestArr = interest.isEmpty() ? new long[0] : handleStringArr(interest.trim().split(" "));
            // 用户注册服务
            userInfoRegisterService.RegisterWithUserInfo(openid, studentId, majorIdInt, realName, sexInt, specialityArr, interestArr);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.ok().body(null);
    }

    private long[] handleStringArr(String[] arr) {
        long[] retArr = new long[arr.length];
        int i = 0;
        for (String each : arr) {
            retArr[i] = Long.parseLong(each);
            i++;
        }
        return retArr;
    }

}
