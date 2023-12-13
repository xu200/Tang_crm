package com.xqy.controller;

import com.xqy.config.XqyResultController;
import com.xqy.query.XqyCustomerOrderQuery;
import com.xqy.service.XqyCustomerOrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author xu
 * @Date 2023/9/30 11:39
 * @Version 1.0
 * 客户信息管理中的订单查看
 */
@Controller
@RequestMapping("order")
public class XqyCustomerOrderController extends XqyResultController {
    @Resource
    private XqyCustomerOrderService customerOrderService;

    @ApiOperation("顾客所有订单")
    @GetMapping("list")
    @ResponseBody
    public Map<String, Object> queryCustomerOrdersByParams(XqyCustomerOrderQuery customerOrderQuery) {
        return customerOrderService.queryCustomerOrdersByParams(customerOrderQuery);
    }
}
