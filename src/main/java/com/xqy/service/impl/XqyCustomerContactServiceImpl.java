package com.xqy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xqy.bean.XqyCustomerContact;
import com.xqy.dao.XqyCustomerContactMapper;
import com.xqy.query.XqyCustomerContactQuery;
import com.xqy.service.XqyCustomerContactService;
import com.xqy.utils.XqyAssertUtil;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xu
 * @description 针对表【t_customer_contact】的数据库操作Service实现
 * @createDate 2023-09-29 11:31:27
 */
@Service
@CacheConfig(cacheNames = "customer_contact")
public class XqyCustomerContactServiceImpl extends ServiceImpl<XqyCustomerContactMapper, XqyCustomerContact> implements XqyCustomerContactService {
    @Resource
    private XqyCustomerContactMapper customerContactMapper;

    //查询交往记录
    @Cacheable("#customerContactQuery")
    public Map<String, Object> selectAll(XqyCustomerContactQuery customerContactQuery) {
        Map<String, Object> map = new HashMap();
        PageHelper.startPage(customerContactQuery.getPage(), customerContactQuery.getLimit());
        PageInfo<XqyCustomerContact> pageInfo = new PageInfo(customerContactMapper.selectAll(customerContactQuery));
        map.put("code", 0);
        map.put("msg", "");
        map.put("count", pageInfo.getTotal());
        map.put("data", pageInfo.getList());
        return map;
    }

    //添加交往记录
    public void saveCustomerContact(XqyCustomerContact customerContact) {
        customerContact.setIsValid(1);
        customerContact.setCreateDate(new Date());
        customerContact.setUpdateDate(new Date());
        //添加数据
        XqyAssertUtil.isTrue(customerContactMapper.insert(customerContact) < 1, "交往记录添加失败!");
    }

    //更新交往记录
    public void updateCustomerContact(XqyCustomerContact customerContact) {
        customerContact.setUpdateDate(new Date());
        XqyAssertUtil.isTrue(customerContactMapper.updateById(customerContact) < 1, "交往记录更新失败!");
    }

    //删除交往记录
    public void deleteCustomerContact(Integer id) {
        XqyCustomerContact customerContact = customerContactMapper.selectById(id);
        XqyAssertUtil.isTrue(null == customerContact, "待删除的客户记录不存在!");
        XqyAssertUtil.isTrue(customerContactMapper.deleteById(customerContact) < 1, "客户记录删除失败!");
    }
}




