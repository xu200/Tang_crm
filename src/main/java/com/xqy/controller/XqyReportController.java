package com.xqy.controller;

import com.xqy.config.XqyResultController;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author xu
 * @Date 2023/9/30 11:51
 * @Version 1.0
 */
@Controller
@RequestMapping("report")
public class XqyReportController extends XqyResultController {
    @ApiOperation("页面分发")
    @GetMapping("/{type}")
    public String index(@PathVariable Integer type) {
        if (type == 0) {
            // 客户贡献分析页面
            return "report/customer_contri";
        } else if (type == 1) {
            // 客户构成页面
            return "report/customer_make";
        } else if (type == 2) {
            // 客户构成页面
            return "report/customer_serve";
        } else if (type == 3) {
            // 客户流失分析页面
            return "report/customer_loss";
        } else {
            return "";
        }
    }
}
