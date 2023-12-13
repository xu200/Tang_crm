package com.xqy.dao;

import com.xqy.bean.XqyPermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @author xu
 * @description 针对表【t_permission】的数据库操作Mapper
 * @createDate 2023-09-29 11:31:27
 * @Entity com.xqy.entity.XqyPermission
 */
public interface XqyPermissionMapper extends BaseMapper<XqyPermission> {
    //通过传入角色id查询该角色被授权的资源
    public List<Integer> queryRoleAllModIds(Integer roleId);

    //通过用户id查询该用户被授权得资源
    public List<String> queryUserHasRoleIdsHasModuleIds(Integer userId);
}




