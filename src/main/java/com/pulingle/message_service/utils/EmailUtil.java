package com.pulingle.message_service.utils;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Properties;

/**
 * @Author: Teemo
 * @Description: javamail发送邮件工具类
 * @Date: Created in 9:09 2018/3/27
 */
public class EmailUtil {

    public static void sendMail(String email,String content)
            throws AddressException, MessagingException, UnsupportedEncodingException, GeneralSecurityException {

        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "SMTP");
        props.setProperty("mail.host", "smtp.yeah.net");
        props.setProperty("mail.smtp.auth", "true");// 指定验证为true

        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.socketFactory", sf);

        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.port", "465");

        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("liveroom_admin@yeah.net", "liveroom_123123");
            }
        };

        Session session = Session.getInstance(props, auth);
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("liveroom_admin@yeah.net","Teemo")); // 设置发送者
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(email)); // 设置发送方式与接收者
        message.setSubject("验证码");
        message.setContent(content, "text/html;charset=UTF-8");
        // 3.创建 Transport用于将邮件发送
        Transport.send(message);
    }
}
