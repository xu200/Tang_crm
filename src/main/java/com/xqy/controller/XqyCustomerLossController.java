package com.xqy.controller;

import com.xqy.config.XqyResultController;
import com.xqy.config.XqyResultInfo;
import com.xqy.query.XqyCustomerLossQuery;
import com.xqy.service.XqyCustomerLossService;
import com.xqy.service.XqyCustomerReprieveService;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author xu
 * @Date 2023/9/30 11:38
 * @Version 1.0
 * 流失客户管理
 */
@Controller
@RequestMapping("customer_loss")
public class XqyCustomerLossController extends XqyResultController {
    @Resource
    public XqyCustomerLossService customerLossService;

    @Resource
    private XqyCustomerReprieveService customerReprieveService;


    @GetMapping("index")
    public String index() {
        return "customerLoss/customer_loss";
    }

    @ApiOperation("流失客户列表")
    @GetMapping("list")
    @ResponseBody
    public Map<String, Object> queryCustomerLossByParams(XqyCustomerLossQuery customerLossQuery) {
        return customerLossService.queryCustomerLossByParams(customerLossQuery);
    }


    @ApiOperation("暂缓流失界面")
    @GetMapping("toCustomerReprPage")
    public String toCustomerReprPage(Integer id, Model model) {
        model.addAttribute("customerLoss", customerLossService.getById(id));
        return "customerLoss/customer_rep";
    }


    @ApiOperation("暂缓流失操作")
    @GetMapping("addOrUpdateCustomerReprPage")
    public String addOrUpdateCustomerReprPage(Integer id, Integer lossId, Model model) {
        model.addAttribute("customerRep", customerReprieveService.getById(id));
        model.addAttribute("lossId", lossId);
        return "customerLoss/customer_rep_add_update";
    }


    @ApiOperation("确认流失客户")
    @PostMapping("updateCustomerLossStateById")
    @ResponseBody
    public XqyResultInfo updateCustomerLossStateById(Integer id, String lossReason) {
        customerLossService.updateCustomerLossStateById(id, lossReason);
        return success("客户确认流失成功!");
    }
}
