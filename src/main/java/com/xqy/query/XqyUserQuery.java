package com.xqy.query;

import com.xqy.config.XqyPageQuery;
import lombok.Data;

@Data
public class XqyUserQuery extends XqyPageQuery {
    //姓名
    private String userName;
    //邮箱
    private String email;
    //电话
    private String phone;
}
