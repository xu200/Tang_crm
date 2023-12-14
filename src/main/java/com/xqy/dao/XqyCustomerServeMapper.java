package com.xqy.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xqy.config.XqyPageQuery;
import com.xqy.bean.XqyCustomerServe;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * @author xu
 * @description 针对表【customer_serve】的数据库操作Mapper
 * @createDate 2023-09-29 11:31:26
 * @Entity com.xqy.entity.XqyCustomerServe
 */
public interface XqyCustomerServeMapper extends BaseMapper<XqyCustomerServe> {
    //多条件查询
    public List<XqyCustomerServe> selectByParams(XqyPageQuery baseQuery) throws DataAccessException;
}




