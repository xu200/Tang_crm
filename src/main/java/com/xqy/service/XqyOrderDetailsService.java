package com.xqy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xqy.bean.XqyOrderDetails;
import com.xqy.query.XqyOrderDetailsQuery;

import java.util.Map;

/**
 * @author xu
 * @description 针对表【t_order_details】的数据库操作Service
 * @createDate 2023-09-29 11:31:27
 */
public interface XqyOrderDetailsService extends IService<XqyOrderDetails> {
    //查询订单记录
    public Map<String, Object> queryOrderDetailsByParams(XqyOrderDetailsQuery orderDetailsQuery);
}
