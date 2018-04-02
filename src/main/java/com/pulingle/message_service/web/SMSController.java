package com.pulingle.message_service.web;

import com.pulingle.message_service.service.SMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author: Teemo
 * @Description:Controller
 * @Date: Created in 10:05 2018/3/25
 */

@RestController
public class SMSController {
    @Autowired
    SMSService smsService;


    /**
     * @param phone 用户手机号
     * @return json格式的msg 发短信响应
     */
    @RequestMapping("/sendSMS")
    public Map<String, Object> sendSMS(String phone) {
        return smsService.sendSMS(phone);
    }

    /**
     * @param phone    用户手机
     * @param authCode 用户填写的验证码
     * @return json格式的msg 验证短信验证码响应
     */
    @RequestMapping("/checkAuthCode")
    public Map<String, Object> checkAuthCode(String phone, String authCode) {
        return smsService.checkAuthCode(phone, authCode);
    }

}
