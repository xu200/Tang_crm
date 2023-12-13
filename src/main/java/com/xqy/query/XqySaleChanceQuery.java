package com.xqy.query;

import com.xqy.config.XqyPageQuery;
import lombok.Data;

@Data
public class XqySaleChanceQuery extends XqyPageQuery {
    // 客户名
    private String customerName;

    // 创建人
    private String createMan;

    // 分配状态
    private String state;

    //分配人
    private Integer assignMan;

    // 开发状态
    private Integer devResult;
}
