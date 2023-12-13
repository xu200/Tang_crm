package com.xqy.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xqy.config.XqyPageQuery;
import com.xqy.bean.XqyCustomerOrder;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Map;

/**
 * @author xu
 * @description 针对表【t_customer_order】的数据库操作Mapper
 * @createDate 2023-09-29 11:31:27
 * @Entity com.xqy.entity.XqyCustomerOrder
 */
public interface XqyCustomerOrderMapper extends BaseMapper<XqyCustomerOrder> {
    //多条件查询
    public List<XqyCustomerOrder> selectByParams(XqyPageQuery baseQuery) throws DataAccessException;

    //查询所有订单
    Map<String, Object> queryCustomerOrderByOrderId(Integer orderId);

    //查询单个订单
    public XqyCustomerOrder queryLastCustomerOrderByCusId(Integer cusId);
}




