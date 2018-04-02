package com.pulingle.message_service.web;

import com.pulingle.message_service.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * Created by 杨健 on 2018/4/1
 */

public class EmailController {

    @Autowired
    MailService mailService;

    /**
     * @param email 用户邮箱地址
     * @return json格式的msg 发送邮件响应
     */
    @RequestMapping("/sendEmail")
    public Map<String,Object> sendEmail(String email){
        return mailService.sendEmail(email);
    }

}
