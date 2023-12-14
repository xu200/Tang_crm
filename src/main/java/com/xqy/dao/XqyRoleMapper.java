package com.xqy.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xqy.config.XqyPageQuery;
import com.xqy.bean.XqyRole;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Map;

/**
 * @author xu
 * @description 针对表【role】的数据库操作Mapper
 * @createDate 2023-09-29 11:31:27
 * @Entity com.xqy.entity.XqyRole
 */
public interface XqyRoleMapper extends BaseMapper<XqyRole> {
    //查找用户角色
    public List<Map<String, Object>> queryAllRoles(Integer userId);

    //多条件查询
    public List<XqyRole> selectByParams(XqyPageQuery baseQuery) throws DataAccessException;
}




