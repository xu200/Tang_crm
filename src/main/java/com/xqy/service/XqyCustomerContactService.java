package com.xqy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xqy.bean.XqyCustomerContact;
import com.xqy.query.XqyCustomerContactQuery;

import java.util.Map;

/**
 * @author xu
 * @description 针对表【t_customer_contact】的数据库操作Service
 * @createDate 2023-09-29 11:31:27
 */
public interface XqyCustomerContactService extends IService<XqyCustomerContact> {
    //查询所有交往记录
    public Map<String, Object> selectAll(XqyCustomerContactQuery customerContactQuery);

    //添加用户
    public void saveCustomerContact(XqyCustomerContact customerContact);

    //更新用户
    public void updateCustomerContact(XqyCustomerContact customerContact);

    //删除用户
    public void deleteCustomerContact(Integer id);

}
