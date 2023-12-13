package com.xqy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xqy.bean.XqyCustomer;
import com.xqy.query.XqyCustomerQuery;

import java.util.Map;

/**
 * @author xu
 * @description 针对表【t_customer】的数据库操作Service
 * @createDate 2023-09-29 11:31:27
 */
public interface XqyCustomerService extends IService<XqyCustomer> {
    //通过参数查询客户
    public Map<String, Object> queryCustomersByParams(XqyCustomerQuery customerQuery);

    //添加用户
    public void saveCustomer(XqyCustomer customer);

    //更新用户
    public void updateCustomer(XqyCustomer customer);

    //删除用户
    public void deleteCustomer(Integer id);

    //通过定时任务添加流失客户
    public void updateCustomerState();

    //查询客户贡献
    public Map<String, Object> queryCustomerContributionByParams(XqyCustomerQuery customerQuery);

    //获取数据显示在echarts折线图上
    public Map<String, Object> countCustomerMake();

    //获取数据显示在echarts玫瑰图上
    public Map<String, Object> countCustomerMake02();

    //获取数据显示在echarts折线图上
    public Map<String, Object> countCustomerServe();

    //获取数据显示在echarts玫瑰图上
    public Map<String, Object> countCustomerServe02();
}
