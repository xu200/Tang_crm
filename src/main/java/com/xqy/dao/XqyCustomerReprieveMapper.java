package com.xqy.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xqy.config.XqyPageQuery;
import com.xqy.bean.XqyCustomerReprieve;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * @author xu
 * @description 针对表【t_customer_reprieve】的数据库操作Mapper
 * @createDate 2023-09-29 11:31:27
 * @Entity com.xqy.entity.XqyCustomerReprieve
 */
public interface XqyCustomerReprieveMapper extends BaseMapper<XqyCustomerReprieve> {
    //多条件查询
    public List<XqyCustomerReprieve> selectByParams(XqyPageQuery baseQuery) throws DataAccessException;

}




