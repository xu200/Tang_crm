package com.xqy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xqy.bean.XqyCusDevPlan;
import com.xqy.query.XqyCusDevPlanQuery;

import java.util.Map;

/**
 * @author xu
 * @description 针对表【t_cus_dev_plan】的数据库操作Service
 * @createDate 2023-09-29 11:31:27
 */
public interface XqyCusDevPlanService extends IService<XqyCusDevPlan> {
    //通过条件查询当前用户被分配的记录
    public Map<String, Object> queryCusDevPlansByParams(XqyCusDevPlanQuery cusDevPlanQuery);

    //新增开发计划
    public void saveCusDevPlan(XqyCusDevPlan cusDevPlan);

    //编辑更新开发计划
    public void updateCusDevPlan(XqyCusDevPlan cusDevPlan);

    //删除一项开发计划
    public void deleteCusDevPlan(Integer id);
}
