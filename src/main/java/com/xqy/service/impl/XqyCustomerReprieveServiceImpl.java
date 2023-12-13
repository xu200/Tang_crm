package com.xqy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xqy.bean.XqyCustomerReprieve;
import com.xqy.dao.XqyCustomerLossMapper;
import com.xqy.dao.XqyCustomerReprieveMapper;
import com.xqy.query.XqyCustomerReprQuery;
import com.xqy.service.XqyCustomerReprieveService;
import com.xqy.utils.XqyAssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xu
 * @description 针对表【t_customer_reprieve】的数据库操作Service实现
 * @createDate 2023-09-29 11:31:27
 */
@Service
@CacheConfig(cacheNames = "customer_reprieve")
public class XqyCustomerReprieveServiceImpl extends ServiceImpl<XqyCustomerReprieveMapper, XqyCustomerReprieve>
        implements XqyCustomerReprieveService {
    @Resource
    private XqyCustomerReprieveMapper reprieveMapper;
    @Resource
    private XqyCustomerLossMapper lossMapper;

    //查询暂缓数据
    @Cacheable("#customerReprQuery")
    @Override
    public Map<String, Object> queryCustomerReprsByParams(XqyCustomerReprQuery customerReprQuery) {
        Map<String, Object> map = new HashMap();
        PageHelper.startPage(customerReprQuery.getPage(), customerReprQuery.getLimit());
        PageInfo<XqyCustomerReprieve> pageInfo = new PageInfo(reprieveMapper.selectByParams(customerReprQuery));
        map.put("code", 0);
        map.put("msg", "");
        map.put("count", pageInfo.getTotal());
        map.put("data", pageInfo.getList());
        return map;
    }

    //添加暂缓数据
    @Override
    public void saveCustomerRepr(XqyCustomerReprieve customerReprieve) {
        /**
         * 1.参数校验
         *    流失客户id 非空 记录必须存在
         *    暂缓错误  measure 非空
         * 2.参数默认值设置
         *    isValid  createDate updateDate
         *  3.执行添加 判断结果
         */
        XqyAssertUtil.isTrue(null == customerReprieve.getLossId()
                || null == lossMapper.selectById(customerReprieve.getLossId()), "请指定流失客户id");
        XqyAssertUtil.isTrue(StringUtils.isBlank(customerReprieve.getMeasure()), "请指定措施项!");
        customerReprieve.setIsValid(1);
        customerReprieve.setCreateDate(new Date());
        customerReprieve.setUpdateDate(new Date());
        XqyAssertUtil.isTrue(reprieveMapper.insert(customerReprieve) < 1, "暂缓措施添加失败!");
    }

    //更新暂缓数据
    @Override
    public void updateCustomerRepr(XqyCustomerReprieve customerReprieve) {
        /**
         * 1.参数校验
         *    id 记录必须存在
         *    流失客户id 非空 记录必须存在
         *    暂缓错误  measure 非空
         * 2.参数默认值设置
         *     updateDate
         *  3.执行更新 判断结果
         */
        XqyAssertUtil.isTrue(null == reprieveMapper.selectById(customerReprieve.getId()), "待更新的暂缓措施不存在!");
        XqyAssertUtil.isTrue(null == customerReprieve.getLossId()
                || null == reprieveMapper.selectOne(new QueryWrapper<XqyCustomerReprieve>().eq("loss_id", customerReprieve.getLossId())), "请指定流失客户id");
        XqyAssertUtil.isTrue(StringUtils.isBlank(customerReprieve.getMeasure()), "请指定措施项!");
        customerReprieve.setUpdateDate(new Date());
        XqyAssertUtil.isTrue(reprieveMapper.updateById(customerReprieve) < 1, "暂缓措施更新失败!");
    }

    //删除暂缓数据
    @Override
    public void deleteCustomerRepr(Integer id) {
        XqyCustomerReprieve temp = reprieveMapper.selectById(id);
        XqyAssertUtil.isTrue(null == temp, "待删除的暂缓措施不存在!");
        XqyAssertUtil.isTrue(reprieveMapper.deleteById(temp) < 1, "记录删除失败!");
    }
}




