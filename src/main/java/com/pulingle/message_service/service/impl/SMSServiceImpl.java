package com.pulingle.message_service.service.impl;

import com.pulingle.message_service.service.SMSService;
import com.pulingle.message_service.utils.SmsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Teemo
 * @Description: SMSService接口实现
 * @Date: Created in 19:24 2018/3/26
 */

@Service
@Component("smsService")
public class SMSServiceImpl implements SMSService {

    long timeout = 300;//设置超时时间

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private SmsUtil su;

    @Override
    public Map<String, Object> sendSMS(String phone) {
        Map<String, Object> returnmap = new HashMap<String, Object>();
        String random = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
        String jediskey = "p" + phone;
        stringRedisTemplate.opsForValue().set(jediskey, random, timeout, TimeUnit.SECONDS);
        int flag = su.sendSms(phone, random);
        if (flag == 1) {
            returnmap.put("msg", 1);
        } else {
            returnmap.put("msg", 0);
        }
        return returnmap;
    }

    @Override
    public Map<String, Object> checkAuthCode(String phone, String authCode) {
        Map<String, Object> returnmap = new HashMap<String, Object>();
        String key = "p" + phone;
        if (stringRedisTemplate.hasKey(key)) {
            String value = stringRedisTemplate.opsForValue().get(key);
            if (value.equals(authCode)) {
                returnmap.put("msg", "1");
            } else {
                returnmap.put("msg", "0");
            }
        } else {
            returnmap.put("msg", "2");
        }
        return returnmap;
    }

}
