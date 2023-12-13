package com.xqy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xqy.bean.XqyCustomerReprieve;
import com.xqy.query.XqyCustomerReprQuery;

import java.util.Map;

/**
 * @author xu
 * @description 针对表【t_customer_reprieve】的数据库操作Service
 * @createDate 2023-09-29 11:31:27
 */
public interface XqyCustomerReprieveService extends IService<XqyCustomerReprieve> {
    //查询暂缓数据
    public Map<String, Object> queryCustomerReprsByParams(XqyCustomerReprQuery customerReprQuery);

    //添加暂缓数据
    public void saveCustomerRepr(XqyCustomerReprieve customerReprieve);

    //更新暂缓数据
    public void updateCustomerRepr(XqyCustomerReprieve customerReprieve);

    //删除暂缓数据
    public void deleteCustomerRepr(Integer id);
}
