package com.xqy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xqy.bean.XqyCustomer;
import com.xqy.bean.XqyCustomerServe;
import com.xqy.utils.enums.XqyCustomerServeStatus;
import com.xqy.dao.XqyCustomerMapper;
import com.xqy.dao.XqyCustomerServeMapper;
import com.xqy.dao.XqyUserMapper;
import com.xqy.query.XqyCustomerServeQuery;
import com.xqy.service.XqyCustomerServeService;
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
 * @description 针对表【t_customer_serve】的数据库操作Service实现
 * @createDate 2023-09-29 11:31:26
 */
@Service
@CacheConfig(cacheNames = "customer_serve")
public class XqyCustomerServeServiceImpl extends ServiceImpl<XqyCustomerServeMapper, XqyCustomerServe>
        implements XqyCustomerServeService {
    @Resource
    private XqyCustomerMapper customerMapper;

    @Resource
    private XqyCustomerServeMapper customerServeMapper;

    @Resource
    private XqyUserMapper userMapper;

    //查询客户服务
    @Cacheable("customerServeQuery")
    public Map<String, Object> queryCustomerServesByParams(XqyCustomerServeQuery customerServeQuery) {
        Map<String, Object> map = new HashMap();
        PageHelper.startPage(customerServeQuery.getPage(), customerServeQuery.getLimit());
        PageInfo<XqyCustomerServe> pageInfo = new PageInfo(customerServeMapper.selectByParams(customerServeQuery));
        map.put("code", 0);
        map.put("msg", "");
        map.put("count", pageInfo.getTotal());
        map.put("data", pageInfo.getList());
        return map;
    }


    //添加服务
    public void saveCustomerServe(XqyCustomerServe customerServe) {
        /**
         * 1.参数校验
         *    客户名  非空  客户记录在客户表中必须存在
         *    服务类型  非空
         *    服务请求内容非空  serviceRequest
         *2. 默认值添加
         *      state  已创建 状态
         *      isValid  createDate  updateDate
         * 3.执行添加  判断结果
         */
        XqyAssertUtil.isTrue(StringUtils.isBlank(customerServe.getCustomer()), "请指定客户!");
        XqyAssertUtil.isTrue(StringUtils.isBlank(customerServe.getServeType()), "请指定服务类型!");
        XqyAssertUtil.isTrue(StringUtils.isBlank(customerServe.getServiceRequest()), "请指定服务请求内容!");
        XqyAssertUtil.isTrue(null == customerMapper.selectOne(new QueryWrapper<XqyCustomer>().eq("name", customerServe.getCustomer()).eq("state", 1)), "客户记录不存在!");
        customerServe.setState(XqyCustomerServeStatus.CREATED.getState());
        customerServe.setIsValid(1);
        customerServe.setCreateDate(new Date());
        customerServe.setUpdateDate(new Date());
        XqyAssertUtil.isTrue(customerServeMapper.insert(customerServe) < 1, "客户服务记录创建失败!");
    }


    //更新服务进程
    public void updateCustomerServe(XqyCustomerServe customerServe) {
        /**
         * 1.参数校验
         *     id 记录必须存在
         * 2.如果状态为分配状态 fw_002
         *    分配人必须存在
         *    设置服务更新时间
         *    设置分配时间
         * 3.如果状态为 服务处理  fw_003
         *     服务处理内容非空
         *     设置服务处理时间
         *     服务更新时间
         * 4.如果服务状态为反馈状态 fw_004
         *     反馈内容非空
         *     满意度非空
         *     更新时间
         *     设置服务状态为归档状态
         * 5.执行更新操作 判断结果
         */
        XqyAssertUtil.isTrue(null == customerServeMapper.selectById(customerServe.getId()), "待更新的服务记录不存在!");
        if (customerServe.getState().equals(XqyCustomerServeStatus.ASSIGNED.getState())) {
            // 执行分配
            XqyAssertUtil.isTrue(null == userMapper.selectById(Integer.parseInt(customerServe.getAssigner())), "待分配的用户不存在!");
            customerServe.setAssignTime(new Date());
        } else if (customerServe.getState().equals(XqyCustomerServeStatus.PROCED.getState())) {
            XqyAssertUtil.isTrue(StringUtils.isBlank(customerServe.getServiceProce()), "请指定处理内容!");
            customerServe.setServiceProceTime(new Date());
        } else if (customerServe.getState().equals(XqyCustomerServeStatus.FEED_BACK.getState())) {
            XqyAssertUtil.isTrue(StringUtils.isBlank(customerServe.getServiceProceResult()), "请指定反馈内容!");
            XqyAssertUtil.isTrue(StringUtils.isBlank(customerServe.getSatisfaction()), "请指定满意度!");
            customerServe.setState(XqyCustomerServeStatus.ARCHIVED.getState());
        }
        customerServe.setUpdateDate(new Date());
        XqyAssertUtil.isTrue(customerServeMapper.updateById(customerServe) < 1, "服务记录更新失败!");
    }
}




