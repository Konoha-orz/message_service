package com.pulingle.message_service.service.impl;

import com.pulingle.message_service.service.MailService;
import com.pulingle.message_service.utils.EmailUtil;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Teemo
 * @Description: 邮件服务实现类
 * @Date: Created in 12:09 2018/3/27
 */

@Service
@Component("mailService")
public class MailServiceImpl implements MailService {

    @Override
    public Map<String, Object> sendEmail(String email) {
        Map<String, Object> returnmap = new HashMap<String, Object>();
        try {
            EmailUtil eu = new EmailUtil();
            String random = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
            String content = "<p>正在尝试验证您的身份，您的验证码为：" + random + "，请勿泄露您的验证码。</p>";
            eu.sendMail(email, content);
            returnmap.put("msg", "1");
        } catch (Exception e) {
            e.printStackTrace();
            returnmap.put("msg", "0");
        }
        return returnmap;
    }
}
