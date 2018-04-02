package com.pulingle.message_service.service;

import java.util.Map;

/**
 * @Author: Teemo
 * @Description: 邮件服务
 * @Date: Created in 9:06 2018/3/27
 */
public interface MailService {

    Map<String,Object> sendEmail(String email);

}
