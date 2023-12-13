package com.xqy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xqy.bean.XqyCustomerLoss;
import com.xqy.dao.XqyCustomerLossMapper;
import com.xqy.dao.XqyCustomerMapper;
import com.xqy.query.XqyCustomerLossQuery;
import com.xqy.service.XqyCustomerLossService;
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
 * @description 针对表【t_customer_loss】的数据库操作Service实现
 * @createDate 2023-09-29 11:31:27
 */
@Service
@CacheConfig(cacheNames = "customer_loss")

public class XqyCustomerLossServiceImpl extends ServiceImpl<XqyCustomerLossMapper, XqyCustomerLoss>
        implements XqyCustomerLossService {
    @Resource
    private XqyCustomerLossMapper customerLossMapper;

    @Resource
    private XqyCustomerMapper customerMapper;

    //查询流失数据
    @Cacheable("#customerLossQuery")
    public Map<String, Object> queryCustomerLossByParams(XqyCustomerLossQuery customerLossQuery) {
        Map<String, Object> map = new HashMap<String, Object>();
        PageHelper.startPage(customerLossQuery.getPage(), customerLossQuery.getLimit());
        PageInfo<XqyCustomerLoss> pageInfo = new PageInfo(customerLossMapper.selectByParams(customerLossQuery));
        map.put("code", 0);
        map.put("msg", "");
        map.put("count", pageInfo.getTotal());
        map.put("data", pageInfo.getList());
        return map;
    }

    //添加流失顾客
    public void updateCustomerLossStateById(Integer id, String lossReason) {
        XqyCustomerLoss customerLoss = customerLossMapper.selectById(id);
        XqyAssertUtil.isTrue(null == customerLoss, "待流失的客户记录不存在!");
        customerLoss.setState(0);// 0.确认流失
        customerLoss.setLossReason(lossReason);
        customerLoss.setConfirmLossTime(new Date());
        customerLoss.setUpdateDate(new Date());

        XqyAssertUtil.isTrue(customerLossMapper.updateById(customerLoss) < 1, "确认流失失败!");
    }
}




