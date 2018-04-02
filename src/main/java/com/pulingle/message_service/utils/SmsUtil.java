package com.pulingle.message_service.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author: Teemo
 * @Description: 阿里云短信服务发短信工具类
 * @Date: Created in 22:38 2018/3/26
 */
@Component("su")
public class SmsUtil {

    //设置超时时间-可自行调整
    @Value("${aliyun.sms.systemTimeout}")
    private String SYSTEM_TIMEOUT;

    //短信API产品名称（短信产品名固定，无需修改）
    @Value("${aliyun.sms.apiName}")
    private String API_NAME;

    //短信API产品域名（接口地址固定，无需修改）
    @Value("${aliyun.sms.apiDomain}")
    private String API_DOMAIN;

    @Value("${aliyun.sms.accessKeyId}")
    private String ACCESS_KEY_ID;

    @Value("${aliyun.sms.accessKeySecret}")
    private String ACCESS_KEY_SECRET;

    //cn-hangzhou
    @Value("${aliyun.sms.regionId}")
    private String REGION_ID;

    @Value("${aliyun.sms.endPointName}")
    private String ENPOINT_NAME;

//    必填:短信签名-可在短信控制台中找到;Githup中文乱码，请设置为英文
//    @Value("${aliyun.sms.signName}")
//    private String SIGN_NAME;

    @Value("${aliyun.sms.templateCode}")
    private String TEMPLATE_CODE;

    public int sendSms(String phone, String random) {
        try {
            //设置超时时间-可自行调整
            System.setProperty("sun.net.client.defaultConnectTimeout", SYSTEM_TIMEOUT);
            System.setProperty("sun.net.client.defaultReadTimeout", SYSTEM_TIMEOUT);

            //初始化ascClient,暂时不支持多region（请勿修改）
            IClientProfile profile = DefaultProfile.getProfile(REGION_ID, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
            DefaultProfile.addEndpoint(ENPOINT_NAME, REGION_ID, API_NAME, API_DOMAIN);
            IAcsClient acsClient = new DefaultAcsClient(profile);
            //组装请求对象
            SendSmsRequest request = new SendSmsRequest();
            //使用post提交
            request.setMethod(MethodType.POST);
            //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
            request.setPhoneNumbers(phone);
            //必填:短信签名-可在短信控制台中找到
            request.setSignName("李成");
            //必填:短信模板-可在短信控制台中找到
            request.setTemplateCode(TEMPLATE_CODE);
            //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
            //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
            request.setTemplateParam("{\"name\":\"Tom\", \"code\":\"" + random + "\"}");
            //可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
            //request.setSmsUpExtendCode("90997");
            //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
            request.setOutId("yourOutId");
            //请求失败这里会抛ClientException异常

            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
            System.out.println(sendSmsResponse.getCode());
            if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
                return 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return 0;
    }
}
