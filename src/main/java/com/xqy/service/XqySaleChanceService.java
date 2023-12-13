package com.xqy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xqy.bean.XqySaleChance;
import com.xqy.query.XqySaleChanceQuery;

import java.util.Map;

/**
 * @author xu
 * @description 针对表【t_sale_chance】的数据库操作Service
 * @createDate 2023-09-29 11:31:27
 */
public interface XqySaleChanceService extends IService<XqySaleChance> {
    //通过传入参数查询营销机会
    public Map<String, Object> querySaleChanceByParams(XqySaleChanceQuery saleChanceQuery);

    //添加营销机会
    public void saveSaleChance(XqySaleChance saleChance);

    //更新营销机会记录
    public void updateSaleChance(XqySaleChance saleChance);

    //批量删除
    public void deleteSaleChance(Integer[] ids);

    //更新开发结果
    public void updateSaleChanceDevResult(Integer id, Integer devResult);
}
