package com.xqy.controller;

import com.xqy.config.XqyResultController;
import com.xqy.config.XqyResultInfo;
import com.xqy.bean.XqyModule;
import com.xqy.service.XqyModuleService;
import com.xqy.utils.XqyZTree;
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
 * @Date 2023/9/30 11:49
 * @Version 1.0
 */
@Controller
@RequestMapping("/module")
public class XqyModuleController extends XqyResultController {
    @Resource
    private XqyModuleService moduleService;


    @ApiOperation("菜单管理模块")
    @GetMapping("index")
    public String index() {
        return "module/module";
    }

    @ApiOperation("查询传入id查询角色")
    @PostMapping("queryAllModules")
    @ResponseBody
    public List<XqyZTree> queryAllModules(Integer roleId) {
        return moduleService.queryAllModules(roleId);
    }


    @ApiOperation("获取所有菜单")
    @GetMapping("list")
    @ResponseBody
    public Map<String, Object> queryModules() {
        return moduleService.queryModules();
    }


    @ApiOperation("添加菜单")
    @PostMapping("save")
    @ResponseBody
    public XqyResultInfo saveModule(XqyModule module) {
        moduleService.saveModule(module);
        return success("菜单添加成功");
    }

    @ApiOperation("更新菜单")
    @PostMapping("update")
    @ResponseBody
    public XqyResultInfo updateModule(XqyModule module) {
        moduleService.updateModule(module);
        return success("菜单更新成功");
    }

    @ApiOperation("删除菜单")
    @PostMapping("delete")
    @ResponseBody
    public XqyResultInfo deleteModule(Integer mid) {
        moduleService.deleteModule(mid);
        return success("菜单删除成功");
    }


    @ApiOperation("添加菜单展示页面")
    @GetMapping("addModulePage")
    public String addModulePage(Integer grade, Integer parentId, Model model) {
        model.addAttribute("grade", grade);
        model.addAttribute("parentId", parentId);
        return "module/add";
    }


    @ApiOperation("更新菜单展示页面")
    @GetMapping("updateModulePage")
    public String updateModulePage(Integer id, Model model) {
        model.addAttribute("module", moduleService.getById(id));
        return "module/update";
    }

}
