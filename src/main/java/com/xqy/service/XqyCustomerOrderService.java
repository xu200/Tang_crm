package com.xqy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xqy.bean.XqyCustomerOrder;
import com.xqy.query.XqyCustomerOrderQuery;

import java.util.Map;

/**
 * @author xu
 * @description 针对表【t_customer_order】的数据库操作Service
 * @createDate 2023-09-29 11:31:27
 */
public interface XqyCustomerOrderService extends IService<XqyCustomerOrder> {
    //查询所有订单
    public Map<String, Object> queryCustomerOrdersByParams(XqyCustomerOrderQuery customerOrderQueryQuery);

    //查询单个订单
    public Map<String, Object> queryCustomerOrderByOrderId(Integer orderId);
}
