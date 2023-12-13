package com.xqy.controller;

import com.xqy.config.XqyResultController;
import com.xqy.config.XqyResultInfo;
import com.xqy.bean.XqyCustomerReprieve;
import com.xqy.query.XqyCustomerReprQuery;
import com.xqy.service.XqyCustomerReprieveService;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author xu
 * @Date 2023/9/30 11:39
 * @Version 1.0
 * 流失客户管理中的暂缓
 */
@Controller
@RequestMapping("customer_rep")
public class XqyReprieveController extends XqyResultController {
    @Resource
    private XqyCustomerReprieveService customerReprService;

    public XqyReprieveController() {
    }

    @ApiOperation("暂缓流失客户列表")
    @GetMapping({"list"})
    @ResponseBody
    public Map<String, Object> queryCustomerReprsByParams(XqyCustomerReprQuery customerReprQuery) {
        return this.customerReprService.queryCustomerReprsByParams(customerReprQuery);
    }

    @ApiOperation("添加暂缓记录")
    @PostMapping({"save"})
    @ResponseBody
    public XqyResultInfo saveCustomerRepr(XqyCustomerReprieve customerReprieve) {
        this.customerReprService.saveCustomerRepr(customerReprieve);
        return this.success("暂缓记录添加成功!");
    }

    @ApiOperation("更新暂缓记录")
    @PostMapping({"update"})
    @ResponseBody
    public XqyResultInfo updateCustomerRepr(XqyCustomerReprieve customerReprieve) {
        this.customerReprService.updateCustomerRepr(customerReprieve);
        return this.success("暂缓记录更新成功!");
    }

    @ApiOperation("删除暂缓记录")
    @PostMapping({"delete"})
    @ResponseBody
    public XqyResultInfo deleteCustomerRepr(Integer id) {
        this.customerReprService.deleteCustomerRepr(id);
        return this.success("暂缓记录删除成功!");
    }
}
