package com.xqy.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xqy.config.XqyPageQuery;
import com.xqy.bean.XqyUser;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Map;

/**
 * @author xu
 * @description 针对表【user】的数据库操作Mapper
 * @createDate 2023-09-29 11:31:27
 * @Entity com.xqy.entity.TUser
 */
public interface XqyUserMapper extends BaseMapper<XqyUser> {
    //查询所有销售人员
    public List<Map<String, Object>> queryAllSales();

    //多条件查询
    public List<XqyUser> selectByParams(XqyPageQuery baseQuery) throws DataAccessException;

    //通过名字查询用户
    XqyUser queryUserByUserName(String userName);

    //查询所有客户经理
    List<Map<String, Object>> queryAllCustomerManager();

}




