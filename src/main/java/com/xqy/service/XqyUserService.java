package com.xqy.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.xqy.bean.XqyUser;
import com.xqy.query.XqyUserQuery;
import com.xqy.utils.XqyLoginUser;

import java.util.List;
import java.util.Map;

/**
 * @author xu
 * @description 针对表【t_user】的数据库操作Service
 * @createDate 2023-09-29 11:31:27
 */
public interface XqyUserService extends IService<XqyUser> {
    //登录逻辑
    public XqyLoginUser login(String userName, String userPwd);

    //修改用户信息逻辑
    public void updateUserInfo(Integer userId, String userOldPwd, String newPwd, String confirmPwd);

    //查询所有销售人员
    public List<Map<String, Object>> queryAllSales();

    //查询用户
    public Map<String, Object> queryUsersByParams(XqyUserQuery userQuery);

    //添加用户
    public void saveUser(XqyUser user);

    //角色更新操作
    public void updateUser(XqyUser user);

    //批量删除
    public void deleteUserByIds(Integer[] ids);

    //查询所有客户经理
    List<Map<String, Object>> queryAllCustomerManager();
}
