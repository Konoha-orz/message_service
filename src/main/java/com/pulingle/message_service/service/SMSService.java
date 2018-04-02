package com.pulingle.message_service.service;

import java.util.Map;

/**
 * @Author: Teemo
 * @Description: 短信服务
 * @Date: Created in 19:23 2018/3/26
 */
public interface SMSService {

    /**
     * @param phone 用户手机
     * @return json格式的msg 发短信响应
     */
    Map<String,Object> sendSMS(String phone);

    /**
     * @param phone 用户手机
     * @param authCode 用户填写的验证码
     * @return json格式的msg 验证短响应
     */
    Map<String,Object> checkAuthCode(String phone,String authCode);

}
