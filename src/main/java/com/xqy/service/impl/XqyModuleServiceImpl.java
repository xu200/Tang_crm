package com.xqy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.xqy.bean.XqyModule;
import com.xqy.bean.XqyPermission;
import com.xqy.dao.XqyModuleMapper;
import com.xqy.dao.XqyPermissionMapper;
import com.xqy.service.XqyModuleService;
import com.xqy.utils.XqyAssertUtil;
import com.xqy.utils.XqyZTree;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xu
 * @description 针对表【t_module】的数据库操作Service实现
 * @createDate 2023-09-29 11:31:27
 */
@Service
@CacheConfig(cacheNames = "module")
public class XqyModuleServiceImpl extends ServiceImpl<XqyModuleMapper, XqyModule>
        implements XqyModuleService {
    @Resource
    private XqyModuleMapper moduleMapper;
    @Resource
    private XqyPermissionMapper permissionMapper;

    //查询所有已经给传入角色分配的资源
    @Override
    @Cacheable("#roleId")
    public List<XqyZTree> queryAllModules(Integer roleId) {
        //先查询所有资源
        List<XqyZTree> ZTrees = moduleMapper.queryAllModules();
        // 查询角色已分配的菜单id
        List<Integer> mids = permissionMapper.queryRoleAllModIds(roleId);
        /**
         * 如果角色已经拥有了某个资源的权限，则将z-tree的checked值置为true，
         * 这样就能让前端页面显示的时候可以显示已经打勾
         */
        if (null != mids && mids.size() > 0) {
            ZTrees.forEach(tree -> {
                //查看当前ZTree对象的id是否包含在 用户已经被分配到的资源id集合中
                if (mids.contains(tree.getId())) {
                    // 设置为已经被选择，用作前台展示
                    tree.setChecked(true);
                }
            });
        }
        return ZTrees;
    }

    //查询整张菜单表数据
    @Override
    public Map<String, Object> queryModules() {
        Map<String, Object> result = new HashMap();
        //查询所有菜单的所有数据
        List<XqyModule> modules = moduleMapper.selectList(null);
        result.put("count", modules.size());
        result.put("data", modules);
        result.put("code", 0);
        result.put("msg", "");
        return result;
    }

    //添加菜单项
    @Override
    public void saveModule(XqyModule module) {
        /**
         *
         *     菜单名
         *         非空 同一层级 菜单名唯一
         *     url
         *        二级菜单时 非空 不可重复
         *     上级菜单 parentId
         *        一级菜单  parentId (-1)
         *        二级|三级菜单   parentId 非空 上级菜单记录必须存在
         *     菜单层级  grade
         *       非空  0|1|2
         *     权限码  optValue
         *        非空  不可重复
         */
        XqyAssertUtil.isTrue(StringUtils.isBlank(module.getModuleName()), "请输入菜单名!");
        Integer grade = module.getGrade();
        XqyAssertUtil.isTrue(null == grade || !(grade == 0 || grade == 1 || grade == 2), "菜单层级非法!");
        //mybatis-plus的条件构造器（所有与grade以及module.getModuleName()字段相等的项）
        XqyModule module1 = moduleMapper.selectOne(new QueryWrapper<XqyModule>().eq("grade", grade).eq("module_name", module.getModuleName()));
        XqyAssertUtil.isTrue(null != module1, "该层级下菜单名已存在!");
        if (grade == 1) {
            XqyAssertUtil.isTrue(StringUtils.isBlank(module.getUrl()), "请输入二级菜单url地址!");
            //mybatis-plus的条件构造器（所有与grade以及module.getUrl()字段相等的项）
            module1 = moduleMapper.selectOne(new QueryWrapper<XqyModule>().eq("grade", grade).eq("url", module.getUrl()));
            XqyAssertUtil.isTrue(null != module1, "二级菜单下url不可重复!");
        }

        // 二级 三级菜单 必须指定上级菜单id
        if (grade != 0) {
            XqyAssertUtil.isTrue(null == module.getParentId() ||
                    //mybatis-plus的条件构造器（所有与id字段相等的项,即传入的父级菜单parent_id，可以在表中找到）
                    null == moduleMapper.selectOne(new QueryWrapper<XqyModule>().eq("id", module.getParentId())), "请指定上级菜单!");
        }

        XqyAssertUtil.isTrue(StringUtils.isBlank(module.getOptValue()), "请输入菜单权限码!");
        //查询特征码是否存在
        module1 = moduleMapper.selectOne(new QueryWrapper<XqyModule>().eq("opt_value", module.getOptValue()));
        XqyAssertUtil.isTrue(null != module1, "权限码重复!");

        module.setIsValid(1);
        module.setCreateDate(new Date());
        module.setUpdateDate(new Date());
        //添加数据
        XqyAssertUtil.isTrue(moduleMapper.insert(module) < 1, "菜单添加失败!");
    }

    //编辑更新菜单
    @Override
    public void updateModule(XqyModule module) {
        /**
         *
         *    id 记录必须存在
         *     菜单名
         *         非空 同一层级 菜单名唯一
         *     url
         *        二级菜单时 非空 不可重复
         *     上级菜单 parentId
         *        二级|三级菜单   parentId 非空 上级菜单记录必须存在
         *     菜单层级  grade
         *       非空  0|1|2
         *     权限码  optValue
         *        非空  不可重复
         */
        XqyModule temp = moduleMapper.selectById(module.getId());
        XqyAssertUtil.isTrue(null == temp, "待修改的菜单记录不存在!");
        XqyAssertUtil.isTrue(StringUtils.isBlank(module.getModuleName()), "请输入菜单名!");
        //验证层级
        Integer grade = module.getGrade();
        XqyAssertUtil.isTrue(null == grade || !(grade == 0 || grade == 1 || grade == 2), "菜单层级非法!");
        //mybatis-plus的条件构造器（所有与grade以及module.getModuleName()字段相等的项）
        temp = moduleMapper.selectOne(new QueryWrapper<XqyModule>().eq("grade", grade).eq("module_name", module.getModuleName()));
        XqyAssertUtil.isTrue(null != temp && !(temp.getId().equals(module.getId())), "该层级下菜单名已存在!");
        if (grade == 1) {
            XqyAssertUtil.isTrue(StringUtils.isBlank(module.getUrl()), "请输入二级菜单url地址!");
            //mybatis-plus的条件构造器（所有与grade以及module.getUrl()字段相等的项）
            temp = moduleMapper.selectOne(new QueryWrapper<XqyModule>().eq("grade", grade).eq("url", module.getUrl()));
            XqyAssertUtil.isTrue(null != temp && !(temp.getId().equals(module.getId())), "二级菜单下url不可重复!");
        }

        // 二级 三级菜单 必须指定上级菜单id
        if (grade != 0) {
            XqyAssertUtil.isTrue(null == module.getParentId() ||
                    //mybatis-plus的条件构造器（所有与id字段相等的项,即传入的父级菜单parent_id，可以在表中找到）
                    null == moduleMapper.selectOne(new QueryWrapper<XqyModule>().eq("id", module.getParentId())), "请指定上级菜单!");
        }

        XqyAssertUtil.isTrue(StringUtils.isBlank(module.getOptValue()), "请输入菜单权限码!");
        temp = moduleMapper.selectOne(new QueryWrapper<XqyModule>().eq("opt_value", module.getOptValue()));
        XqyAssertUtil.isTrue(null != temp && !(temp.getId().equals(module.getId())), "权限码重复!");

        module.setUpdateDate(new Date());
        //更新数据
        XqyAssertUtil.isTrue(moduleMapper.updateById(module) < 1, "菜单记录更新失败!");
    }

    //删除菜单项
    @Override
    public void deleteModule(Integer mid) {
/**
 * 1.记录必须存在
 *     id 非空  记录存在
 * 2.如果待删除的菜单存在子菜单  不允许直接删除当前菜单
 * 3.如果删除的菜单 在权限表中存在对应记录  此时要级联删除权限表对应记录
 */
        XqyModule temp = moduleMapper.selectById(mid);
        XqyAssertUtil.isTrue(null == temp, "待删除的记录不存在!");
        //通过mybatis-plus查询，需要验证选择菜单没有子菜单，则说明该菜单的id不存在于任何菜单的父级菜单中
        Integer total = moduleMapper.selectCount(new QueryWrapper<XqyModule>().eq("parent_id", mid));
        XqyAssertUtil.isTrue(total > 0, "存在子菜单，暂不支持删除操作!");

        // 删除权限表(permission表)对应记录
        XqyPermission permission = permissionMapper.selectById(mid);
        //如果存在记录
        if (null != permission) {
            XqyAssertUtil.isTrue(permissionMapper.delete(new QueryWrapper<XqyPermission>().eq("parent_id", mid)) < 1, "菜单记录删除失败!");
        }
        XqyAssertUtil.isTrue(moduleMapper.deleteById(mid) < 1, "菜单记录删除失败!");
    }
}




