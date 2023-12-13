package com.xqy.exceptions;

/**
 * @Author xu
 * @Date 2023/9/30 13:13
 * @Version 1.0
 * 自定义参数异常
 */
public class XqyUnLoginException extends RuntimeException{
    private Integer code=300;
    private String msg="用户未登录!";


    public XqyUnLoginException() {
        super("用户未登录!");
    }

    public XqyUnLoginException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public XqyUnLoginException(Integer code) {
        super("用户未登录!");
        this.code = code;
    }

    public XqyUnLoginException(Integer code, String msg) {
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
