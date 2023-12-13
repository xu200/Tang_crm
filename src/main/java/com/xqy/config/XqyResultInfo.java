package com.xqy.config;

import lombok.Getter;
import lombok.Setter;

/**
 * @author xu
 * @since 2023-9-29
 * 配置状态码
 */

public class XqyResultInfo {
    private Integer code = 200;
    private String msg = "success";
    private Object result;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
