package com.xqy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xqy.bean.XqyCustomerOrder;
import com.xqy.dao.XqyCustomerOrderMapper;
import com.xqy.query.XqyCustomerOrderQuery;
import com.xqy.service.XqyCustomerOrderService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xu
 * @description 针对表【t_customer_order】的数据库操作Service实现
 * @createDate 2023-09-29 11:31:27
 */
@Service
@CacheConfig(cacheNames = "customer_order")
public class XqyCustomerOrderServiceImpl extends ServiceImpl<XqyCustomerOrderMapper, XqyCustomerOrder>
        implements XqyCustomerOrderService {

    @Resource
    private XqyCustomerOrderMapper orderMapper;

    //查询所有订单
    @Cacheable("#customerOrderQueryQuery")
    public Map<String, Object> queryCustomerOrdersByParams(XqyCustomerOrderQuery customerOrderQueryQuery) {
        Map<String, Object> map = new HashMap();
        PageHelper.startPage(customerOrderQueryQuery.getPage(), customerOrderQueryQuery.getLimit());
        PageInfo<XqyCustomerOrder> pageInfo = new PageInfo(orderMapper.selectByParams(customerOrderQueryQuery));
        map.put("code", 0);
        map.put("msg", "");
        map.put("count", pageInfo.getTotal());
        map.put("data", pageInfo.getList());
        return map;
    }

    //查询单个订单
    @Cacheable("#orderId")
    public Map<String, Object> queryCustomerOrderByOrderId(Integer orderId) {
        return orderMapper.queryCustomerOrderByOrderId(orderId);
    }
}




