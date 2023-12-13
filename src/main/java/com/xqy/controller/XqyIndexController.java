package com.xqy.controller;

import com.xqy.config.XqyResultController;
import com.xqy.bean.XqyUser;
import com.xqy.service.XqyPermissionService;
import com.xqy.service.XqyUserService;
import com.xqy.utils.XqyLoginUserUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 首页
 */
@Controller
public class XqyIndexController extends XqyResultController {

    @Resource
    private XqyUserService userService;

    @Resource
    private XqyPermissionService permissionService;

    //系统登录页
    @ApiOperation("登录页")
    @GetMapping("index")
    public String index() {
        return "index";
    }

    //系统欢迎页
    @ApiOperation("系统欢迎页")
    @GetMapping("welcome")
    public String welcome() {
        return "welcome";
    }

    //后台管理页
    @ApiOperation("后台管理页")
    @GetMapping(value = {"/", "main"})
    public String admin(HttpServletRequest request) {
        //通过id查询用户的信息
        Integer userId = XqyLoginUserUtil.releaseUserIdFromCookie(request);
        XqyUser user = userService.getById(userId);
        //得到用户信息后返回
        request.setAttribute("user", user);
        //通过用户id查询该用户所拥有得权限，将查到得权限放到session中，以作为权限控制
        List<String> permissions = permissionService.queryUserHasRoleIdsHasModuleIds(userId);
        request.getSession().setAttribute("permissions", permissions);
        return "main";
    }
}
