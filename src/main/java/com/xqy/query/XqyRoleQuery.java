package com.xqy.query;

import com.xqy.config.XqyPageQuery;
import lombok.Data;

@Data
public class XqyRoleQuery extends XqyPageQuery {
    private String roleName;
}
