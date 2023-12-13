package com.xqy.controller;

import com.xqy.config.XqyResultController;
import com.xqy.config.XqyResultInfo;
import com.xqy.bean.XqyCusDevPlan;
import com.xqy.query.XqyCusDevPlanQuery;
import com.xqy.service.XqyCusDevPlanService;
import com.xqy.service.XqySaleChanceService;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author xu
 * @since 2023-9-29
 * 客户开发计划
 */
@RequestMapping("/cus_dev_plan")
@Controller
public class XqyCusDevPlanController extends XqyResultController {
    //依赖注入
    @Resource
    private XqySaleChanceService saleChanceService;

    @Resource
    private XqyCusDevPlanService cusDevPlanService;

    //营销机会管理接口
    @ApiOperation("营销机会管理开发页")
    @GetMapping("index")
    public String index() {
        return "cusDevPlan/cus_dev_plan";
    }


    //获取当前用户的分配项的数据
    @ApiOperation("点击(详情/完成)弹出显示框的数据")
    @GetMapping("toCusDevPlanDataPage")
    public String toCusDevPlanDataPage(Integer sid, Model model) {
        model.addAttribute("saleChance", saleChanceService.getById(sid));
        return "cusDevPlan/cus_dev_plan_data";
    }

    //根据条件分页查询
    @ApiOperation("条件查询")
    @GetMapping("list")
    @ResponseBody
    public Map<String, Object> queryCusDevPlansByParams(XqyCusDevPlanQuery cusDevPlanQuery) {
        return cusDevPlanService.queryCusDevPlansByParams(cusDevPlanQuery);
    }


    @ApiOperation("添加计划工作")
    @PostMapping("save")
    @ResponseBody
    public XqyResultInfo saveCusDevPlan(XqyCusDevPlan cusDevPlan) {
        cusDevPlanService.saveCusDevPlan(cusDevPlan);
        //调用封装的sucess方法
        return success("计划项数据添加成功");
    }

    @ApiOperation("更新计划项")
    @PutMapping("update")
    @ResponseBody
    public XqyResultInfo updateCusDevPlan(XqyCusDevPlan cusDevPlan) {
        cusDevPlanService.updateCusDevPlan(cusDevPlan);
        return success("计划项数据更新成功");
    }

    @ApiOperation("删除计划项")
    @DeleteMapping("delete")
    @ResponseBody
    public XqyResultInfo deleteCusDevPlan(Integer id) {
        cusDevPlanService.deleteCusDevPlan(id);
        return success("计划项数据删除成功");
    }


    @ApiOperation("获得当前用户的计划数据（详情/开发选项）")
    @GetMapping("addOrUpdateCusDevPlanPage")
    public String addOrUpdateCusDevPlanPage(Integer sid, Integer id, Model model) {
        model.addAttribute("sid", sid);
        model.addAttribute("cusDevPlan", cusDevPlanService.getById(id));
        return "cusDevPlan/add_update";
    }

}
