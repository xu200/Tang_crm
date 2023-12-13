package com.xqy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xqy.bean.XqyPermission;
import com.xqy.bean.XqyRole;
import com.xqy.bean.XqyUserRole;
import com.xqy.dao.XqyModuleMapper;
import com.xqy.dao.XqyPermissionMapper;
import com.xqy.dao.XqyRoleMapper;
import com.xqy.dao.XqyUserRoleMapper;
import com.xqy.query.XqyRoleQuery;
import com.xqy.service.XqyPermissionService;
import com.xqy.service.XqyRoleService;
import com.xqy.utils.XqyAssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author xu
 * @description 针对表【t_role】的数据库操作Service实现
 * @createDate 2023-09-29 11:31:27
 */
@Service
@CacheConfig(cacheNames = "#Role")
public class XqyRoleServiceImpl extends ServiceImpl<XqyRoleMapper, XqyRole> implements XqyRoleService {
    @Resource
    private XqyRoleMapper roleMapper;
    @Resource
    private XqyUserRoleMapper userRoleMapper;
    @Autowired
    private XqyPermissionService permissionService;
    @Resource
    private XqyPermissionMapper permissionMapper;
    @Resource
    private XqyModuleMapper moduleMapper;

    //查询所有角色
    @Override
    @Cacheable("#userId")
    public List<Map<String, Object>> queryAllRoles(Integer userId) {
        return roleMapper.queryAllRoles(userId);
    }

    //通过传入条件查询角色
    @Override
    @Cacheable("#roleQuery")
    public Map<String, Object> queryRolesByParams(XqyRoleQuery roleQuery) {
        Map<String, Object> map = new HashMap<String, Object>();
        //分页
        PageHelper.startPage(roleQuery.getPage(), roleQuery.getLimit());
        PageInfo<XqyRole> pageInfo = new PageInfo(roleMapper.selectByParams(roleQuery));
        map.put("code", 0);
        map.put("msg", "");
        map.put("count", pageInfo.getTotal());
        map.put("data", pageInfo.getList());
        return map;
    }


    //添加角色
    @Override
    public void saveRole(XqyRole role) {
//参数校验
        XqyAssertUtil.isTrue(StringUtils.isBlank(role.getRoleName()), "请输入角色名称!");
        //mybatis-plus的条件构造器（所有与role_name字段相等的项）
        QueryWrapper<XqyRole> wrapper = new QueryWrapper<XqyRole>().eq("role_name", role.getRoleName());
        XqyAssertUtil.isTrue(null != roleMapper.selectOne(wrapper), "该角色已存在!");
        //初始化角色数据
        role.setIsValid(1);
        role.setCreateDate(new Date());
        role.setUpdateDate(new Date());
        //添加角色
        XqyAssertUtil.isTrue(roleMapper.insert(role) < 1, "角色记录添加失败!");
    }

    //修改角色
    @Override
    public void updateRole(XqyRole role) {
//参数校验
        XqyAssertUtil.isTrue(StringUtils.isBlank(role.getRoleName()), "请输入角色名称!");
        //mybatis-plus的条件构造器（所有与role_name字段相等的项）
        QueryWrapper<XqyRole> wrapper = new QueryWrapper<XqyRole>().eq("role_name", role.getRoleName());
        //查询角色
        XqyRole temp = roleMapper.selectOne(wrapper);
        //角色存在，但是传入的id与数据库中该角色的id值一致，则是已存在
        XqyAssertUtil.isTrue(null != temp && !(temp.getId().equals(role.getId())), "该角色已存在!");
        //传入更新时间
        role.setUpdateDate(new Date());
        XqyAssertUtil.isTrue(roleMapper.updateById(role) < 1, "角色记录更新失败!");
    }

    //删除角色
    @Override
    public void deleteRole(Integer roleId) {
//查询角色是否存在
        XqyRole role = roleMapper.selectById(roleId);
        XqyAssertUtil.isTrue(null == role, "待删除的记录不存在!");

        //mybatis-plus的条件构造器（所有与role_id字段相等的项）
        QueryWrapper<XqyUserRole> wrapper = new QueryWrapper<XqyUserRole>().eq("role_id", roleId);
        int total = userRoleMapper.selectCount(wrapper);
        //如果关联表中中有记录，先删除中间表中的数据
        if (total > 0) {
            //直接删除中间表t_user_role中的关联数据
            XqyAssertUtil.isTrue(userRoleMapper.delete(wrapper) != total, "用户角色记录删除失败!");
        }
        //System.out.println(role);
        //使用mybatis-plus中的逻辑删除 is_valid = 0
        XqyAssertUtil.isTrue(roleMapper.deleteById(roleId) < 1, "角色记录删除失败!");
    }

    //角色授权
    @Override
    public void addGrant(Integer[] mids, Integer roleId) {
//mybatis-plus的条件构造器（所有与role_id字段相等的项）
        QueryWrapper<XqyPermission> wrapper = new QueryWrapper<XqyPermission>().eq("role_id", roleId);
        int total = permissionMapper.selectCount(wrapper);
        //如果关联表中中有记录，先删除中间表中的数据
        if (total > 0) {
            XqyAssertUtil.isTrue(permissionMapper.delete(wrapper) != total, "角色授权失败!");
        }

        //如果有传入权限值 则进行辅助操作
        if (null != mids && mids.length > 0) {
            //接收所有权限对象
            List<XqyPermission> permissions = new ArrayList();
            for (Integer mid : mids) {
                XqyPermission permission = new XqyPermission();
                permission.setCreateDate(new Date());
                permission.setModuleId(mid);
                permission.setRoleId(roleId);
                permission.setUpdateDate(new Date());
                //权限值
                permission.setAclValue(moduleMapper.selectById(mid).getOptValue());
                //添加到集合中
                permissions.add(permission);
            }
            //批量添加到权限表中
            XqyAssertUtil.isTrue(!(permissionService.saveBatch(permissions)), "角色授权失败!");
        }
    }
}




