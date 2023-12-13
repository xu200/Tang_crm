package com.xqy.config;

import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xu
 * @since 2023-9-29
 */
public class XqyResultController {

    //配置请求头的前缀
    @ModelAttribute
    public void preHandler(HttpServletRequest request) {
        request.setAttribute("ctx", request.getContextPath());
    }


    public XqyResultInfo success() {
        return new XqyResultInfo();
    }

    public XqyResultInfo success(String msg) {
        XqyResultInfo resultInfo = new XqyResultInfo();
        resultInfo.setMsg(msg);
        return resultInfo;
    }

    public XqyResultInfo success(String msg, Object result) {
        XqyResultInfo resultInfo = new XqyResultInfo();
        resultInfo.setMsg(msg);
        resultInfo.setResult(result);
        return resultInfo;
    }
}
