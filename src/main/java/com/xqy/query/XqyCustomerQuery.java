package com.xqy.query;

import com.xqy.config.XqyPageQuery;
import lombok.Data;

@Data
public class XqyCustomerQuery extends XqyPageQuery {
    //顾客姓名
    private String Name;
    //顾客id
    private String customerId;
    //顾客等级
    private String level;
    //流失时间
    private String time;
    // 金额区间  1:0-1000   2:1000-3000  3:3000-5000 4:5000+
    private Integer type;
}
