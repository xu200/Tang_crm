package com.xqy.exceptions;

/**
 * 自定义参数异常
 */
public class XqyParamsException extends RuntimeException {
    private Integer code = 300;
    private String msg = "参数异常!";


    public XqyParamsException() {
        super("参数异常!");
    }

    public XqyParamsException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public XqyParamsException(Integer code) {
        super("参数异常!");
        this.code = code;
    }

    public XqyParamsException(Integer code, String msg) {
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
