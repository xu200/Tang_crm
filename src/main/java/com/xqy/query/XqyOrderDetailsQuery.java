package com.xqy.query;

import com.xqy.config.XqyPageQuery;
import lombok.Data;

@Data
public class XqyOrderDetailsQuery extends XqyPageQuery {
    private Integer orderId;
}
