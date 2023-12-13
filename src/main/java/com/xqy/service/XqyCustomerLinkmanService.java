package com.xqy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xqy.bean.XqyCustomerLinkman;

/**
 * @author xu
 * @description 针对表【t_customer_linkman】的数据库操作Service
 * @createDate 2023-09-29 11:31:27
 */
public interface XqyCustomerLinkmanService extends IService<XqyCustomerLinkman> {
    //更新联系人
     void updateCustomerLinkman(XqyCustomerLinkman customerLinkman);
}
