package com.xqy.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xqy.config.XqyResultController;
import com.xqy.config.XqyResultInfo;
import com.xqy.bean.XqyCustomerContact;
import com.xqy.query.XqyCustomerContactQuery;
import com.xqy.service.XqyCustomerContactService;
import io.swagger.annotations.ApiOperation;
import org.springframework.cache.annotation.Cacheable;
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
 * @Date 2023/9/30 11:35
 * @Version 1.0
 * 客户信息管理中的交往记录
 */
@Controller
@RequestMapping("/customer_contact")
public class XqyCustomerContactController extends XqyResultController {
    @Resource
    private XqyCustomerContactService customerContactService;

    @ApiOperation("交往记录管理")
    @GetMapping("customerContactPage")
    public String customerContactPage(Integer cid, Model model) {
        model.addAttribute("customerContact", customerContactService.getOne(new QueryWrapper<XqyCustomerContact>().eq("cus_id", cid)));
        model.addAttribute("cusId", cid);
        return "customerContact/customer_contact";
    }

    @ApiOperation("添加/更新页")
    @GetMapping("addOrUpdateCustomerContactPage")
    public String addOrUpdateCustomerContactPage(Integer cusId, Integer id, Model model) {
        model.addAttribute("customerContact", customerContactService.getById(id));
        //传入客户id，如果是add操作需要该参数
        model.addAttribute("cusId", cusId);
        //System.out.println(customerService.getById(id));
        return "customerContact/add_update";
    }

    @ApiOperation("查询所有交往记录")
    @GetMapping("list")
    @ResponseBody
    @Cacheable(cacheNames = "customer_contact", key = "#customerContactQuery")
    public Map<String, Object> queryCustomersByParams(XqyCustomerContactQuery customerContactQuery) {
        //System.out.println("=========="+customerContactService.selectAll(customerContactQuery));
        return customerContactService.selectAll(customerContactQuery);
    }

    @ApiOperation("添加记录")
    @PostMapping("save")
    @ResponseBody
    public XqyResultInfo saveCustomer(XqyCustomerContact customerContact) {
        customerContactService.saveCustomerContact(customerContact);
        return success("客户交往记录添加成功!");
    }

    @ApiOperation("编辑交往记录")
    @PostMapping("update")
    @ResponseBody
    public XqyResultInfo updateCustomer(XqyCustomerContact customerContact) {
        System.out.println(customerContact);
        customerContactService.updateCustomerContact(customerContact);
        return success("客户记录更新成功!");
    }

    @ApiOperation("删除记录")
    @PostMapping("delete")
    @ResponseBody
    public XqyResultInfo deleteCustomer(Integer id) {
        customerContactService.deleteCustomerContact(id);
        return success("客户记录删除成功!");
    }
}
