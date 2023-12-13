package com.xqy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xqy.bean.XqyModule;
import com.xqy.utils.XqyZTree;

import java.util.List;
import java.util.Map;

/**
 * @author xu
 * @description 针对表【t_module】的数据库操作Service
 * @createDate 2023-09-29 11:31:27
 */
public interface XqyModuleService extends IService<XqyModule> {
    //查询传入角色拥有的权限，放回类型为z-tree
    public List<XqyZTree> queryAllModules(Integer roleId);

    //查询整张菜单表数据
    public Map<String, Object> queryModules();

    //添加菜单项
    public void saveModule(XqyModule module);

    //编辑更新菜单
    public void updateModule(XqyModule module);

    //删除菜单项
    public void deleteModule(Integer mid);
}
