package com.xqy.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xqy.config.XqyPageQuery;
import com.xqy.bean.XqySaleChance;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
* @author xu
* @description 针对表【sale_chance】的数据库操作Mapper
* @createDate 2023-09-29 11:31:27
* @Entity com.xqy.entity.XqySaleChance
*/
public interface XqySaleChanceMapper extends BaseMapper<XqySaleChance> {
    //多条件查询
    public List<XqySaleChance> selectByParams(XqyPageQuery baseQuery) throws DataAccessException;
}




