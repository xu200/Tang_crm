package com.xqy.config;

/**
 * @Author xu
 * @Date 2023/9/30 17:29
 * @Version 1.0
 * 分页查询
 */
public class XqyPageQuery {
    private Integer page = 1;
    private Integer limit = 10;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
