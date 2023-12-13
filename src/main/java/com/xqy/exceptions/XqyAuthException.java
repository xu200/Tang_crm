package com.xqy.exceptions;

/**
 * @Author xu
 * @Date 2023/9/30 13:52
 * @Version 1.0
 * 认证异常
 */
public class XqyAuthException extends RuntimeException {
    private Integer code=400;
    private String msg="暂无权限";


    public XqyAuthException() {
        super("暂无权限!");
    }

    public XqyAuthException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public XqyAuthException(Integer code) {
        super("暂无权限!");
        this.code = code;
    }

    public XqyAuthException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

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
}
