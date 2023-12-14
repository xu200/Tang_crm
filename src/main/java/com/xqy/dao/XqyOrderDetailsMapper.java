package com.xqy.dao;

import com.xqy.config.XqyPageQuery;
import com.xqy.bean.XqyOrderDetails;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
* @author xu
* @description 针对表【order_details】的数据库操作Mapper
* @createDate 2023-09-29 11:31:27
* @Entity com.xqy.entity.XqyOrderDetails
*/
public interface XqyOrderDetailsMapper extends BaseMapper<XqyOrderDetails> {
    //多条件查询
    public List<XqyOrderDetails> selectByParams(XqyPageQuery baseQuery) throws DataAccessException;
}




