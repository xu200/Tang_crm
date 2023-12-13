package com.xqy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xqy.bean.XqyCusDevPlan;
import com.xqy.dao.XqyCusDevPlanMapper;
import com.xqy.dao.XqySaleChanceMapper;
import com.xqy.query.XqyCusDevPlanQuery;
import com.xqy.service.XqyCusDevPlanService;
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
 * @description 针对表【t_cus_dev_plan】的数据库操作Service实现
 * @createDate 2023-09-29 11:31:27
 */

@Service
@CacheConfig(cacheNames = "cus_dev_plan")
public class XqyCusDevPlanServiceImpl extends ServiceImpl<XqyCusDevPlanMapper, XqyCusDevPlan> implements XqyCusDevPlanService {
    @Resource
    private XqyCusDevPlanMapper cusDevPlanMapper;

    @Resource
    private XqySaleChanceMapper saleChanceMapper;

    //通过条件查询当前用户被分配的记录
    @Cacheable(key = "#cusDevPlanQuery")
    public Map<String, Object> queryCusDevPlansByParams(XqyCusDevPlanQuery cusDevPlanQuery) {
        Map<String, Object> map = new HashMap();
        PageHelper.startPage(cusDevPlanQuery.getPage(), cusDevPlanQuery.getLimit());
        PageInfo<XqyCusDevPlan> pageInfo = new PageInfo(cusDevPlanMapper.selectByParams(cusDevPlanQuery));
        map.put("code", 0);
        map.put("msg", "");
        map.put("count", pageInfo.getTotal());
        map.put("data", pageInfo.getList());
        return map;
    }

    //新增开发计划
    public void saveCusDevPlan(XqyCusDevPlan cusDevPlan) {
        //参数校验（非空）
        checkParams(cusDevPlan.getSaleChanceId(), cusDevPlan.getPlanItem(), cusDevPlan.getPlanDate());
        //设置逻辑存在、创建时间、更新时间
        cusDevPlan.setIsValid(1);
        cusDevPlan.setCreateDate(new Date());
        cusDevPlan.setUpdateDate(new Date());
        //添加记录
        XqyAssertUtil.isTrue(cusDevPlanMapper.insert(cusDevPlan) < 1, "计划项记录添加失败!");
    }

    //编辑更新开发计划
    public void updateCusDevPlan(XqyCusDevPlan cusDevPlan) {
        //参数校验
        checkParams(cusDevPlan.getSaleChanceId(), cusDevPlan.getPlanItem(), cusDevPlan.getPlanDate());
        XqyAssertUtil.isTrue(null == cusDevPlan.getId() || null == cusDevPlanMapper.selectById(cusDevPlan.getId()), "待更新的记录不存在!");
        //设置更新时间
        cusDevPlan.setUpdateDate(new Date());
        //更新结果
        XqyAssertUtil.isTrue(cusDevPlanMapper.updateById(cusDevPlan) < 1, "记录更新失败!");
    }

    //删除一项开发计划
    public void deleteCusDevPlan(Integer id) {
        XqyCusDevPlan cusDevPlan = cusDevPlanMapper.selectById(id);
        XqyAssertUtil.isTrue(null == cusDevPlan, "待删除的记录不存在!");
        //逻辑删除
        cusDevPlan.setIsValid(0);
        XqyAssertUtil.isTrue(cusDevPlanMapper.updateById(cusDevPlan) < 1, "记录删除失败!");
    }

    //参数校验（非空）
    private void checkParams(Integer saleChanceId, String planItem, Date planDate) {
        XqyAssertUtil.isTrue(null == saleChanceId || null == saleChanceMapper.selectById(saleChanceId), "请设置营销机会id");
        XqyAssertUtil.isTrue(StringUtils.isBlank(planItem), "请输入计划项时间（例：2023-9-30）");
        XqyAssertUtil.isTrue(null == planDate, "请指定计划项日期!");
    }
}




