package com.xqy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xqy.bean.XqyCustomerServe;
import com.xqy.query.XqyCustomerServeQuery;

import java.util.Map;

/**
 * @author xu
 * @description 针对表【t_customer_serve】的数据库操作Service
 * @createDate 2023-09-29 11:31:26
 */
public interface XqyCustomerServeService extends IService<XqyCustomerServe> {
    //查询客户服务
    public Map<String, Object> queryCustomerServesByParams(XqyCustomerServeQuery customerServeQuery);

    //添加服务
    public void saveCustomerServe(XqyCustomerServe customerServe);

    //更新服务进程
    public void updateCustomerServe(XqyCustomerServe customerServe);
}
