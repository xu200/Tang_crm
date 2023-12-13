package com.xqy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xqy.bean.XqyPermission;
import com.xqy.dao.XqyPermissionMapper;
import com.xqy.service.XqyPermissionService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xu
 * @description 针对表【t_permission】的数据库操作Service实现
 * @createDate 2023-09-29 11:31:27
 */
@Service
@CacheConfig(cacheNames = "permission")
public class XqyPermissionServiceImpl extends ServiceImpl<XqyPermissionMapper, XqyPermission>
        implements XqyPermissionService {
    @Resource
    private XqyPermissionMapper permissionMapper;
    //通过用户id查询权限
    @Override
    @Cacheable("#userId")
    public List<String> queryUserHasRoleIdsHasModuleIds(Integer userId) {
        return permissionMapper.queryUserHasRoleIdsHasModuleIds(userId);
    }
}




