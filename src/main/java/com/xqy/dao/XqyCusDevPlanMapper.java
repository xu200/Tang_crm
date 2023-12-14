package com.xqy.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xqy.config.XqyPageQuery;
import com.xqy.bean.XqyCusDevPlan;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
* @author xu
* @description 针对表【cus_dev_plan】的数据库操作Mapper
* @createDate 2023-09-29 11:31:27
* @Entity com.xqy.entity.XqyCusDevPlan
*/
public interface XqyCusDevPlanMapper extends BaseMapper<XqyCusDevPlan> {
    //多条件查询
    public List<XqyCusDevPlan> selectByParams(XqyPageQuery baseQuery) throws DataAccessException;
}




