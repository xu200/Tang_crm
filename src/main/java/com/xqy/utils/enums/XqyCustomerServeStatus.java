package com.xqy.utils.enums;

/**
 * @author xu
 * 客户服务状态枚举类
 */
public enum XqyCustomerServeStatus {
    // 创建
    CREATED("fw_001"),
    // 分配
    ASSIGNED("fw_002"),
    // 处理
    PROCED("fw_003"),
    // 反馈
    FEED_BACK("fw_004"),
    // 归档
    ARCHIVED("fw_005");

    private String state;

    XqyCustomerServeStatus(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
