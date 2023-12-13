package com.xqy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xqy.bean.XqyCustomerLoss;
import com.xqy.query.XqyCustomerLossQuery;

import java.util.Map;

/**
 * @author xu
 * @description 针对表【t_customer_loss】的数据库操作Service
 * @createDate 2023-09-29 11:31:27
 */
public interface XqyCustomerLossService extends IService<XqyCustomerLoss> {
    //查询流失数据
    public Map<String, Object> queryCustomerLossByParams(XqyCustomerLossQuery customerLossQuery);

    //更新流失顾客
    public void updateCustomerLossStateById(Integer id, String lossReason);
}
