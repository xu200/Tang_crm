package com.xqy.controller;

import com.xqy.config.XqyResultController;
import com.xqy.config.XqyResultInfo;
import com.xqy.bean.XqyUser;
import com.xqy.query.XqyUserQuery;
import com.xqy.service.XqyUserService;
import com.xqy.utils.XqyLoginUserUtil;
import com.xqy.utils.XqyLoginUser;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Author xu
 * @Date 2023/9/30 16:33
 * @Version 1.0
 */
@Controller
public class XqyUserController extends XqyResultController {
    @Resource
    private XqyUserService userService;

    //用户登录
    @ApiOperation("用户登录")
    @PostMapping("/user/login")
    @ResponseBody
    public XqyResultInfo login(String userName, String userPassword) {
        //用于放置结果信息
        XqyResultInfo resultInfo = new XqyResultInfo();

        //获取结果
        XqyLoginUser loginResult = userService.login(userName, userPassword);
        //放置结果
        resultInfo.setResult(loginResult);
        //返回操作结果
        return resultInfo;
    }

    @ApiOperation("用户更新密码")
    @PostMapping("/user/updatePassword")
    @ResponseBody
    //用户更新密码
    public XqyResultInfo updateUserInfo(HttpServletRequest request, String oldPassword, String newPassword, String confirmPassword) {
        //用于放置结果信息
        XqyResultInfo resultInfo = new XqyResultInfo();

        //更新密码操作
        userService.updateUserInfo(XqyLoginUserUtil.releaseUserIdFromCookie(request), oldPassword, newPassword, confirmPassword);

        //返回操作结果
        return resultInfo;
    }

    @ApiOperation("更新密码页")
    @GetMapping("/user/toPasswordPage")
    public String toPasswordPage() {
        return "user/password";
    }

    @ApiOperation("查询所有销售人员列表")
    @PostMapping("user/queryAllSales")
    @ResponseBody
    public List<Map<String, Object>> queryAllSales() {
        return userService.queryAllSales();
    }

    @ApiOperation("查询用户")
    @GetMapping("user/list")
    @ResponseBody
    public Map<String, Object> queryUsersByParams(XqyUserQuery userQuery) {
        return userService.queryUsersByParams(userQuery);
    }

    @ApiOperation("用户页")
    @GetMapping("user/index")
    public String index() {
        return "user/user";
    }

    @ApiOperation("保存用户")
    @PostMapping("user/save")
    @ResponseBody
    public XqyResultInfo saveUser(XqyUser user) {
        userService.saveUser(user);
        return success("用户记录添加成功");
    }

    @ApiOperation("更新用户信息")
    @PostMapping("user/update")
    @ResponseBody
    public XqyResultInfo updateUser(XqyUser user) {
        userService.updateUser(user);
        return success("用户记录更新成功");
    }

    @ApiOperation("删除用户")
    @PostMapping("user/delete")
    @ResponseBody
    public XqyResultInfo deleteUser(Integer[] ids) {
        userService.deleteUserByIds(ids);
        return success("用户记录删除成功");
    }

    @ApiOperation("编辑框")
    @GetMapping("user/addOrUpdateUserPage")
    public String addOrUpdateUserPage(Integer id, Model model) {
        model.addAttribute("user", userService.getById(id));
        return "user/add_update";
    }


    @ApiOperation("查询客户经理")
    @PostMapping("user/queryAllCustomerManager")
    @ResponseBody
    public List<Map<String, Object>> queryAllCustomerManager() {
        return userService.queryAllCustomerManager();
    }

}
