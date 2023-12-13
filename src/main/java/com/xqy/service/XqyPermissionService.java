package com.xqy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xqy.bean.XqyPermission;

import java.util.List;

/**
* @author xu
* @description 针对表【t_permission】的数据库操作Service
* @createDate 2023-09-29 11:31:27
*/
public interface XqyPermissionService extends IService<XqyPermission> {
    //通过用户id查询权限
    List<String> queryUserHasRoleIdsHasModuleIds(Integer userId);
}
