package com.xqy.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xu
 * 在Cookie中获取用户id工具类
 */
public class XqyLoginUserUtil {
    /**
     * 从cookie中获取userId
     *
     * @param request
     * @return
     */
    public static int releaseUserIdFromCookie(HttpServletRequest request) {
        String userIdString = XqyCookieUtil.getCookieValue(request, "userId");
        if (StringUtils.isBlank(userIdString)) {
            return 0;
        }
        Integer userId = XqyUserIDBase64.decoderUserID(userIdString);
        return userId;
    }
}
