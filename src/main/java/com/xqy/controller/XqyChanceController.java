package com.xqy.controller;

import com.xqy.annotations.RequirePermission;
import com.xqy.config.XqyResultController;
import com.xqy.config.XqyResultInfo;
import com.xqy.bean.XqySaleChance;
import com.xqy.query.XqySaleChanceQuery;
import com.xqy.service.XqySaleChanceService;
import com.xqy.service.XqyUserService;
import com.xqy.utils.RedisUtil;
import com.xqy.utils.XqyLoginUserUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author xu
 * @Date 2023/9/30 11:52
 * @Version 1.0
 * 营销机会管理
 */
@Controller
@RequestMapping("sale_chance")
public class XqyChanceController extends XqyResultController {
    @Resource
    private XqySaleChanceService saleChanceService;

    @Resource
    private XqyUserService userService;


    @ApiOperation(value = "营销机会列表")
    @GetMapping("list")
    @RequirePermission(code = "101001")
    @ResponseBody
    @Cacheable(cacheNames = "sale_chance", key = "#saleChanceQuery")
    //通过传入参数查询营销机会,考虑到与开发计划共用该查询接口，增加了flag标记（标记为开发计划查询），通过（request）获得当前用户id
    public Map<String, Object> querySaleChanceByParams(Integer flag, HttpServletRequest request, XqySaleChanceQuery saleChanceQuery) {
        if (flag != null && flag == 1) {
            // 查询分配给指定用户的机会数据
            saleChanceQuery.setAssignMan(XqyLoginUserUtil.releaseUserIdFromCookie(request));
        }
        return saleChanceService.querySaleChanceByParams(saleChanceQuery);
    }

    @ApiOperation("营销机会页")
    @GetMapping("index")
    @RequirePermission(code = "1010")
    public String index() {
        return "saleChance/sale_chance";
    }


    @ApiOperation("新增营销机会")
    @PostMapping("save")
    @RequirePermission(code = "101002")
    @ResponseBody
    @CachePut(value = "sale_chance", key = "#result.id") //返回结果中的ID
    public XqyResultInfo saveSaleChance(HttpServletRequest request, XqySaleChance saleChance) {
        //通过Cookie获取id
        int userId = XqyLoginUserUtil.releaseUserIdFromCookie(request);
        //通过id获取创建者真实姓名
        String trueName = userService.getById(userId).getTrueName();
        //写入创建者真实姓名
        saleChance.setCreateMan(trueName);
        //保存记录
        saleChanceService.saveSaleChance(saleChance);

        return success("机会数据添加成功");
    }


    @ApiOperation("转发到更新或添加营销机会页面")
    @GetMapping("addOrUpdateSaleChancePage")
    @CachePut(value = "saleChance", key = "#result.id") //返回结果中的ID
    public String addOrUpdateSaleChancePage(Integer id, Model model) {
        if (null != id) {
            model.addAttribute("saleChance", saleChanceService.getById(id));
        }
        return "saleChance/add_update";
    }


    @ApiOperation("更新内容")
    @PostMapping("update")
    @RequirePermission(code = "101004")
    @ResponseBody
    public XqyResultInfo updateSaleChance(XqySaleChance saleChance) {
        saleChanceService.updateSaleChance(saleChance);
        return success("机会数据更新成功");
    }

    @ApiOperation("删除记录")
    @PostMapping("delete")
    @RequirePermission(code = "101003")
    @ResponseBody
    @CacheEvict(value = "sale_chance", key = "#ids", beforeInvocation = false)
    public XqyResultInfo deleteSaleChance(Integer[] ids) {
        saleChanceService.deleteSaleChance(ids);
        return success("机会数据删除成功!");
    }


    @ApiOperation("更新开发状态")
    @PostMapping("updateSaleChanceDevResult")
    @ResponseBody
    public XqyResultInfo updateSaleChanceDevResult(Integer id, Integer devResult) {
        saleChanceService.updateSaleChanceDevResult(id, devResult);
        return success("开发状态更新成功");
    }
}
