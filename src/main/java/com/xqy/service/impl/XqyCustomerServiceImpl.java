package com.xqy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xqy.bean.XqyCustomer;
import com.xqy.bean.XqyCustomerLinkman;
import com.xqy.bean.XqyCustomerLoss;
import com.xqy.bean.XqyCustomerOrder;
import com.xqy.dao.*;
import com.xqy.query.XqyCustomerQuery;
import com.xqy.service.XqyCustomerLossService;
import com.xqy.service.XqyCustomerService;
import com.xqy.utils.XqyAssertUtil;
import com.xqy.utils.XqyPhoneUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author xu
 * @description 针对表【t_customer】的数据库操作Service实现
 * @createDate 2023-09-29 11:31:27
 */
@Service
@CacheConfig(cacheNames = "customer_serve")
public class XqyCustomerServiceImpl extends ServiceImpl<XqyCustomerMapper, XqyCustomer>
        implements XqyCustomerService {
    @Resource
    private XqyCustomerMapper customerMapper;

    @Resource
    private XqyCustomerLinkmanMapper customerLinkmanMapper;

    @Resource
    private XqyCustomerLossService customerLossService;

    @Resource
    private XqyCustomerOrderMapper customerOrderMapper;

    //通过参数查询用户
    @Cacheable("#customerQuery")
    public Map<String, Object> queryCustomersByParams(XqyCustomerQuery customerQuery) {
        Map<String, Object> map = new HashMap<String, Object>();
        PageHelper.startPage(customerQuery.getPage(), customerQuery.getLimit());
        PageInfo<XqyCustomer> pageInfo = new PageInfo(customerMapper.selectByParams(customerQuery));
        map.put("code", 0);
        map.put("msg", "");
        map.put("count", pageInfo.getTotal());
        map.put("data", pageInfo.getList());
        return map;
    }

    //添加用户
    public void saveCustomer(XqyCustomer customer) {
        /**
         *   参数校验
         *      客户级别不为空
         *     客户名称  name 非空 不可重复
         *     phone 联系电话  非空 格式合法
         *     法人  legal_person 非空
         */
        XqyAssertUtil.isTrue(StringUtils.isBlank(customer.getLevel()), "请指定客户级别!");
        checkParams(customer.getName(), customer.getPhone(), customer.getLegalPerson());
        XqyAssertUtil.isTrue(null != customerMapper.selectOne(new QueryWrapper<XqyCustomer>().eq("name", customer.getName())), "该客户已存在!");

        /**
         * 参数默认值
         *     isValid
         *     createDate
         *     updateDate
         *     state  流失状态  1-未流失 0-已流失
         */
        customer.setIsValid(1);
        customer.setCreateDate(new Date());
        customer.setUpdateDate(new Date());
        customer.setState(1);

        // 设置客户编号(当前时间（微妙）+ 0-10000内的一个随机数)
        String customerId = "KH" + System.currentTimeMillis() + new Random().nextInt(10000);
        customer.setCustomerId(customerId);
        //添加数据
        XqyAssertUtil.isTrue(customerMapper.insert(customer) < 1, "客户记录添加失败!");

        //初始化用户联系人t_customer_linkman
        XqyCustomerLinkman customerLinkman = new XqyCustomerLinkman();
        customerLinkman.setCusId(customer.getId());
        customerLinkman.setCreateDate(new Date());
        XqyAssertUtil.isTrue(customerLinkmanMapper.insert(customerLinkman) < 1, "客户联系人添加失败!");

    }

    //参数校验
    private void checkParams(String name, String phone, String legalPerson) {
        XqyAssertUtil.isTrue(!(XqyPhoneUtil.isMobile(phone)), "手机号格式非法!");
        XqyAssertUtil.isTrue(StringUtils.isBlank(name), "请指定客户名称!");
        XqyAssertUtil.isTrue(StringUtils.isBlank(legalPerson), "请指定公司法人!");
    }


    //更新用户
    public void updateCustomer(XqyCustomer customer) {

        //参数校验
        checkParams(customer.getName(), customer.getPhone(), customer.getLegalPerson());
        //查找是否存在
        XqyCustomer temp = customerMapper.selectById(customer.getId());
        XqyAssertUtil.isTrue(null == temp, "待更新的客户记录不存在!");
        //名字不允许重复
        temp = customerMapper.selectOne(new QueryWrapper<XqyCustomer>().eq("name", customer.getName()));
        XqyAssertUtil.isTrue(null != temp && !(temp.getId().equals(customer.getId())), "该客户已存在!");
        customer.setUpdateDate(new Date());

        XqyAssertUtil.isTrue(customerMapper.updateById(customer) < 1, "客户记录更新失败!");
    }

    //删除用户
    public void deleteCustomer(Integer id) {
        XqyCustomer customer = customerMapper.selectById(id);
        XqyAssertUtil.isTrue(null == customer, "待删除的客户记录不存在!");
        XqyAssertUtil.isTrue(customerMapper.deleteById(customer) < 1, "客户记录删除失败!");
        //删除从表t_customer_link中的信息
        //查找是否存在
        XqyCustomerLinkman temp = customerLinkmanMapper.selectOne(new QueryWrapper<XqyCustomerLinkman>().eq("cus_id", id));
        //删除
        customerLinkmanMapper.deleteById(temp.getId());
    }

    //通过定时任务添加流失客户
    public void updateCustomerState() {
        /**
         * 查询待流失的客户数据
         * 将流失的客户数据批量化添加到客户流失表中
         * 批量更新客户流失状态
         * 通过定时任务 定时流转流失客户数据到客户流失表中
         */
        List<XqyCustomer> customers = customerMapper.queryLossCustomers();
        if (null != customers && customers.size() > 0) {
            List<XqyCustomerLoss> customerLosses = new ArrayList();
            List<Integer> lossCusIds = new ArrayList();
            //遍历整个查找到的流失用户
            customers.forEach(c -> {
                XqyCustomerLoss customerLoss = new XqyCustomerLoss();
                // 1-暂缓流失状态   0-确认流失状态
                customerLoss.setState(1);
                customerLoss.setCreateDate(new Date());
                customerLoss.setCusManager(c.getCusManager());
                customerLoss.setCusName(c.getName());
                customerLoss.setCusNo(c.getCustomerId());
                customerLoss.setIsValid(1);
                customerLoss.setUpdateDate(new Date());
                XqyCustomerOrder customerOrder = customerOrderMapper.queryLastCustomerOrderByCusId(c.getId());
                if (null != customerOrder) {
                    customerLoss.setLastOrderTime(customerOrder.getOrderDate());
                }
                customerLosses.add(customerLoss);
                //更新用户的流失状态
                lossCusIds.add(c.getId());
            });
            //System.out.println("==========================="+lossCusIds);
            XqyAssertUtil.isTrue(!(customerLossService.saveBatch(customerLosses)), "客户数据流转失败!");
            XqyAssertUtil.isTrue(customerMapper.updateCustomerStateByIds(lossCusIds) != lossCusIds.size(), "客户数据流转失败!");
        }
    }


    //查询客户贡献
    public Map<String, Object> queryCustomerContributionByParams(XqyCustomerQuery customerQuery) {
        Map<String, Object> map = new HashMap<String, Object>();
        PageHelper.startPage(customerQuery.getPage(), customerQuery.getLimit());
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(customerMapper.queryCustomerContributionByParams(customerQuery));
        map.put("code", 0);
        map.put("msg", "");
        map.put("count", pageInfo.getTotal());
        map.put("data", pageInfo.getList());
        return map;
    }

    //获取数据显示在echarts折线图上
    public Map<String, Object> countCustomerMake() {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Map<String, Object>> list = customerMapper.countCustomerMake();
        List<String> data1 = new ArrayList<String>();
        List<Integer> data2 = new ArrayList<Integer>();
        /**
         * result
         *    data1:["大客户","合作伙伴"]
         *    data2:[10,20]
         */
        list.forEach(m -> {
            data1.add(m.get("level").toString());
            data2.add(Integer.parseInt(m.get("total").toString()));
        });
        result.put("data1", data1);
        result.put("data2", data2);
        return result;
    }


    //获取数据显示在echarts玫瑰图上
    public Map<String, Object> countCustomerMake02() {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Map<String, Object>> list = customerMapper.countCustomerMake();
        List<String> data1 = new ArrayList<String>();
        List<Map<String, Object>> data2 = new ArrayList<Map<String, Object>>();
        list.forEach(m -> {
            data1.add(m.get("level").toString());
            Map<String, Object> temp = new HashMap<String, Object>();
            temp.put("name", m.get("level"));
            temp.put("value", m.get("total"));
            data2.add(temp);
        });
        result.put("data1", data1);
        result.put("data2", data2);
        return result;
    }

    //获取数据显示在echarts折线图上
    public Map<String, Object> countCustomerServe() {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Map<String, Object>> list = customerMapper.countCustomerServe();
        List<String> data1 = new ArrayList<String>();
        List<Integer> data2 = new ArrayList<Integer>();
        /**
         * result
         *    data1:["建议","投诉"]
         *    data2:[1,2]
         */
        list.forEach(m -> {
            data1.add(m.get("data_dic_value").toString());
            data2.add(Integer.parseInt(m.get("total").toString()));
        });
        result.put("data1", data1);
        result.put("data2", data2);
        return result;
    }


    //获取数据显示在echarts玫瑰图上
    public Map<String, Object> countCustomerServe02() {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Map<String, Object>> list = customerMapper.countCustomerServe();
        List<String> data1 = new ArrayList<String>();
        List<Map<String, Object>> data2 = new ArrayList<Map<String, Object>>();
        list.forEach(m -> {
            data1.add(m.get("data_dic_value").toString());
            Map<String, Object> temp = new HashMap<String, Object>();
            temp.put("name", m.get("data_dic_value"));
            temp.put("value", m.get("total"));
            data2.add(temp);
        });
        result.put("data1", data1);
        result.put("data2", data2);
        return result;
    }
}




