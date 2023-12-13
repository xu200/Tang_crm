package com.xqy.utils;

import lombok.Data;

/**
 * @author xu
 * 登录数据
 */
@Data
public class XqyLoginUser {
    //id(加密)
    private String userId;
    //姓名
    private String userName;
    //密码
    private String trueName;
}
