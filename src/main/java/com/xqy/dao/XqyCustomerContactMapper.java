package com.xqy.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xqy.config.XqyPageQuery;
import com.xqy.bean.XqyCustomerContact;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
* @author xu
* @description 针对表【customer_contact】的数据库操作Mapper
* @createDate 2023-09-29 11:31:27
* @Entity com.xqy.entity.XqyCustomerContact
*/
public interface XqyCustomerContactMapper extends BaseMapper<XqyCustomerContact> {

    //多条件查询
    public List<XqyCustomerContact> selectAll(XqyPageQuery baseQuery) throws DataAccessException;
}




