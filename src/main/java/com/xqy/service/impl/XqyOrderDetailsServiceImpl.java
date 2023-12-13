package com.xqy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xqy.bean.XqyOrderDetails;
import com.xqy.dao.XqyOrderDetailsMapper;
import com.xqy.query.XqyOrderDetailsQuery;
import com.xqy.service.XqyOrderDetailsService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xu
 * @description 针对表【t_order_details】的数据库操作Service实现
 * @createDate 2023-09-29 11:31:27
 */
@Service
@CacheConfig(cacheNames = "order_details")
public class XqyOrderDetailsServiceImpl extends ServiceImpl<XqyOrderDetailsMapper, XqyOrderDetails>
        implements XqyOrderDetailsService {
    @Resource
    private XqyOrderDetailsMapper orderDetailsMapper;

    //查询订单记录
    @Override
    @Cacheable("#orderDetailsQuery")
    public Map<String, Object> queryOrderDetailsByParams(XqyOrderDetailsQuery orderDetailsQuery) {
        Map<String, Object> map = new HashMap();
        PageHelper.startPage(orderDetailsQuery.getPage(), orderDetailsQuery.getLimit());
        PageInfo<XqyOrderDetails> pageInfo = new PageInfo(orderDetailsMapper.selectByParams(orderDetailsQuery));
        map.put("code", 0);
        map.put("msg", "");
        map.put("count", pageInfo.getTotal());
        map.put("data", pageInfo.getList());
        return map;
    }
}




