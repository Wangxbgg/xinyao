package com.xinyao.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class RequestUtils {

    /**
     * 获取Ip地址
     */
    public static String getIpAddress(HttpServletRequest request) {
        String xip = request.getHeader("X-Real-IP");
        String xFor = request.getHeader("X-Forwarded-For");
        String unknown = "unknown";
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(xFor) && !unknown.equalsIgnoreCase(xFor)) {
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = xFor.indexOf(",");
            if (index != -1) {
                return xFor.substring(0, index);
            } else {
                return xFor;
            }
        }
        xFor = xip;
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(xFor) && !unknown.equalsIgnoreCase(xFor)) {
            return xFor;
        }
        if (org.apache.commons.lang3.StringUtils.isBlank(xFor) || unknown.equalsIgnoreCase(xFor)) {
            xFor = request.getHeader("Proxy-Client-IP");
        }
        if (org.apache.commons.lang3.StringUtils.isBlank(xFor) || unknown.equalsIgnoreCase(xFor)) {
            xFor = request.getHeader("WL-Proxy-Client-IP");
        }
        if (org.apache.commons.lang3.StringUtils.isBlank(xFor) || unknown.equalsIgnoreCase(xFor)) {
            xFor = request.getHeader("HTTP_CLIENT_IP");
        }
        if (org.apache.commons.lang3.StringUtils.isBlank(xFor) || unknown.equalsIgnoreCase(xFor)) {
            xFor = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isBlank(xFor) || unknown.equalsIgnoreCase(xFor)) {
            xFor = request.getRemoteAddr();
        }
        return xFor;
    }
}
