package com.xqy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xqy.bean.XqySaleChance;
import com.xqy.utils.enums.XqyDevResult;
import com.xqy.utils.enums.XqyStateStatus;
import com.xqy.dao.XqySaleChanceMapper;
import com.xqy.query.XqySaleChanceQuery;
import com.xqy.service.XqySaleChanceService;
import com.xqy.utils.XqyAssertUtil;
import com.xqy.utils.XqyPhoneUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xu
 * @description 针对表【t_sale_chance】的数据库操作Service实现
 * @createDate 2023-09-29 11:31:27
 */
@Service
@CacheConfig(cacheNames = "sale_chance")
public class XqySaleChanceServiceImpl extends ServiceImpl<XqySaleChanceMapper, XqySaleChance>
        implements XqySaleChanceService {
    @Resource
    private XqySaleChanceMapper saleChanceMapper;

    //通过传入参数查询营销机会
    @Override
    @Cacheable("#saleChanceQuery")
    public Map<String, Object> querySaleChanceByParams(XqySaleChanceQuery saleChanceQuery) {
        //把查询值放在map中
        Map<String, Object> map = new HashMap<String, Object>();

        //获取分页状态
        PageHelper.startPage(saleChanceQuery.getPage(), saleChanceQuery.getLimit());
        //使用了pageHelper查询结果并分页
        PageInfo<XqySaleChance> pageInfo = new PageInfo<>(saleChanceMapper.selectByParams(saleChanceQuery));
        //将获取到的分页信息放入map中，然后返回
        map.put("code", 0);
        map.put("msg", "");
        map.put("count", pageInfo.getTotal());
        map.put("data", pageInfo.getList());
        //返回结果集
        return map;
    }

    //添加营销机会
    @Override
    public void saveSaleChance(XqySaleChance saleChance) {
        /**
         *      参数校验
         *      customerName  客户名非空
         *      linkMan  非空
         *      linkPhone  非空 11位手机号
         */
        checkParams(saleChance.getCustomerName(), saleChance.getLinkMan(), saleChance.getLinkPhone());
        /**
         *      设置相关参数默认值
         *       state 默认未分配   如果选择分配人  state 为已分配状态
         *       assignTime 默认空   如果选择分配人  分配时间为系统当前时间
         *       devResult  默认未开发  如果选择分配人 devResult 为开发中 0-未开发  1-开发中 2-开发成功 3-开发失败
         *       isValid  默认有效(1-有效  0-无效)
         *       createDate  updateDate:默认系统当前时间
         */
        //写入状态（默认）
        saleChance.setState(XqyStateStatus.UNSTATE.getType());
        saleChance.setDevResult(XqyDevResult.UNDEV.getStatus());
        //如果分配人不为空
        if (StringUtils.isNotBlank(saleChance.getAssignMan())) {
            //写入开发状态
            saleChance.setState(XqyStateStatus.STATED.getType());
            saleChance.setDevResult(XqyDevResult.DEVING.getStatus());
            saleChance.setAssignTime(new Date());
        }
        //逻辑存在
        saleChance.setIsValid(1);
        //写入创建和更新时间
        saleChance.setCreateDate(new Date());
        saleChance.setUpdateDate(new Date());
        //插入数据
        XqyAssertUtil.isTrue(saleChanceMapper.insert(saleChance) < 1, "机会数据添加失败!");
    }

    private void checkParams(String customerName, String linkMan, String linkPhone) {
        XqyAssertUtil.isTrue(StringUtils.isBlank(customerName), "请输入客户名!");
        XqyAssertUtil.isTrue(StringUtils.isBlank(linkMan), "请输入联系人!");
        XqyAssertUtil.isTrue(StringUtils.isBlank(linkPhone), "请输入手机号!");
        XqyAssertUtil.isTrue(!(XqyPhoneUtil.isMobile(linkPhone)), "手机号格式不合法!");
    }

    //更新营销机会记录
    @Override
    public void updateSaleChance(XqySaleChance saleChance) {
        /**
         *      参数校验
         *     id 记录必须存在
         *     customerName  客户名非空
         *     linkMan  非空
         *     linkPhone  非空 11位手机号
         */
        checkParams(saleChance.getCustomerName(), saleChance.getLinkMan(), saleChance.getLinkPhone());

        //先查询将要更新的记录在不在，获取对象
        XqySaleChance temp = saleChanceMapper.selectById(saleChance.getId());
        XqyAssertUtil.isTrue(null == temp, "待更新记录不存在!");

        //写入当前记录的更新时间
        saleChance.setUpdateDate(new Date());
        /**
         *       未分配 修改后
         *       已分配(分配人是否存在)
         *          state   0--->1
         *          assignTime   设置分配时间 系统时间
         *          devResult  0--->1
         *       原始记录  已分配
         *       修改后  未分配
         *         state 1-->0
         *         assignTime  null
         *         devResult 1-->0
         */
        //如果数据库中记录的分配人为空，并且传入的对象的分配人不为空（为记录进行分配操作）
        if (StringUtils.isBlank(temp.getAssignMan()) && StringUtils.isNotBlank(saleChance.getAssignMan())) {
            //修改为已分配
            saleChance.setState(XqyStateStatus.STATED.getType());
            //写入分配时间
            saleChance.setAssignTime(new Date());
            //写入开发状态
            saleChance.setDevResult(XqyDevResult.DEVING.getStatus());
        } else if (StringUtils.isNotBlank(temp.getAssignMan()) && StringUtils.isBlank(saleChance.getAssignMan())) {
            //修改为未分配状态
            saleChance.setState(XqyStateStatus.UNSTATE.getType());
            saleChance.setAssignTime(null);
            saleChance.setDevResult(XqyDevResult.UNDEV.getStatus());
            saleChance.setAssignMan("");
        }
        //查看更新结果
        XqyAssertUtil.isTrue(saleChanceMapper.updateById(saleChance) < 1, "机会数据更新失败!");
    }

    //批量删除
    @Override
    public void deleteSaleChance(Integer[] ids) {
        XqyAssertUtil.isTrue(null == ids || ids.length == 0, "请选择待删除记录!");
        XqyAssertUtil.isTrue(saleChanceMapper.deleteBatchIds(Arrays.asList(ids)) != ids.length, "记录删除失败!");
    }

    //更新开发结果
    @Override
    public void updateSaleChanceDevResult(Integer id, Integer devResult) {
        XqySaleChance temp = saleChanceMapper.selectById(id);
        XqyAssertUtil.isTrue(null == temp, "待更新记录不存在!");
        temp.setDevResult(devResult);
        XqyAssertUtil.isTrue(saleChanceMapper.updateById(temp) < 1, "机会数据状态更新失败!");
    }
}




