package com.xqy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xqy.bean.XqyRole;
import com.xqy.query.XqyRoleQuery;

import java.util.List;
import java.util.Map;

/**
 * @author xu
 * @description 针对表【t_role】的数据库操作Service
 * @createDate 2023-09-29 11:31:27
 */
public interface XqyRoleService extends IService<XqyRole> {
    //查找用户角色
    public List<Map<String, Object>> queryAllRoles(Integer userId);

    //通过传入条件查询角色
    public Map<String, Object> queryRolesByParams(XqyRoleQuery roleQuery);

    //添加角色
    public void saveRole(XqyRole role);

    //更新角色
    public void updateRole(XqyRole role);

    //删除角色
    public void deleteRole(Integer id);

    //角色授权（mids：资源id ，roleId：角色id）
    public void addGrant(Integer[] mids, Integer roleId);
}
