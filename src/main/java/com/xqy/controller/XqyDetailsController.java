package com.xqy.controller;

import com.xqy.config.XqyResultController;
import com.xqy.query.XqyOrderDetailsQuery;
import com.xqy.service.XqyOrderDetailsService;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author xu
 * @Date 2023/9/30 11:50
 * @Version 1.0
 * 订单详情
 */
@Controller
@RequestMapping("order_details")
public class XqyDetailsController extends XqyResultController {
    @Resource
    private XqyOrderDetailsService orderDetailsService;

    @ApiOperation("订单详情")
    @GetMapping("list")
    @ResponseBody
    public Map<String, Object> queryDetailsByParams(XqyOrderDetailsQuery orderDetailsQuery) {
        return orderDetailsService.queryOrderDetailsByParams(orderDetailsQuery);
    }
}
