package com.pulingle.message_service.utils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;

/**
 * Created by @杨健 on 2018/4/28 18:00
 *
 * @Des:
 */

public class IpUtil {
    /**
     * 13      * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址,
     * 14 *
     * 16      * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？
     * 17      * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。
     * 18      *
     * 19      * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130,
     * 20      * 192.168.1.100
     * 21      *
     * 22      * 用户真实IP为： 192.168.1.110
     * 23      *
     * 24      * @param request
     * 25      * @return
     * 26
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if (ip.equals("127.0.0.1")) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ip = inet.getHostAddress();
            }
        }
        // 多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ip != null && ip.length() > 15) {
            if (ip.indexOf(",") > 0) {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }
        return ip;
    }
}
