package com.xqy.controller;

import com.xqy.config.XqyResultController;
import com.xqy.config.XqyResultInfo;
import com.xqy.bean.XqyRole;
import com.xqy.query.XqyRoleQuery;
import com.xqy.service.XqyRoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Author xu
 * @Date 2023/9/30 11:51
 * @Version 1.0
 * 角色管理
 */
@Controller
@RequestMapping("/role")
public class XqyRoleController extends XqyResultController {
    @Resource
    private XqyRoleService roleService;

    @ApiOperation("角色管理页")
    @GetMapping("index")
    public String index() {
        return "role/role";
    }


    @ApiOperation("查询所有角色")
    @PostMapping("queryAllRoles")
    @ResponseBody
    public List<Map<String, Object>> queryAllRoles(Integer userId) {
        return roleService.queryAllRoles(userId);
    }


    @ApiOperation("查询角色")
    @GetMapping("list")
    @ResponseBody
    public Map<String, Object> queryRolesByParams(XqyRoleQuery roleQuery) {
        return roleService.queryRolesByParams(roleQuery);
    }

    @ApiOperation("添加角色")
    @PostMapping("save")
    @ResponseBody
    public XqyResultInfo saveRole(XqyRole role) {
        roleService.saveRole(role);
        return success("角色记录添加成功");
    }

    @ApiOperation("更新角色")
    @PostMapping("update")
    @ResponseBody
    public XqyResultInfo updateRole(XqyRole role) {
        roleService.updateRole(role);
        return success("角色记录更新成功");
    }


    @ApiOperation("添加或者更新角色弹出框")
    @GetMapping("addOrUpdateRolePage")
    public String addOrUpdateRolePage(Integer id, Model model) {
        model.addAttribute("role", roleService.getById(id));
        return "role/add_update";
    }

    @ApiOperation("删除角色")
    @PostMapping("delete")
    @ResponseBody
    public XqyResultInfo deleteRole(Integer id) {
        roleService.deleteRole(id);
        return success("角色记录删除成功");
    }

    @ApiOperation("为角色授权页")
    @GetMapping("toAddGrantPage")
    public String toAddGrantPage(Integer roleId, Model model) {
        model.addAttribute("roleId", roleId);
        return "role/grant";
    }

    @ApiOperation("授权接口")
    @PostMapping("addGrant")
    @ResponseBody
    public XqyResultInfo addGrant(Integer[] mids, Integer roleId) {
        roleService.addGrant(mids, roleId);
        return success("角色授权成功!");
    }

}
