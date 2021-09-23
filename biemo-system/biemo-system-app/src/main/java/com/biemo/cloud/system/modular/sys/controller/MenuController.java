package com.biemo.cloud.system.modular.sys.controller;

import com.biemo.cloud.auth.api.context.LoginContext;
import com.biemo.cloud.auth.api.context.LoginUser;
import com.biemo.cloud.system.modular.sys.entity.SysMenu;
import com.biemo.cloud.system.modular.sys.model.MenuNode;
import com.biemo.cloud.system.modular.sys.model.TreeNode;
import com.biemo.cloud.system.modular.sys.model.params.MenuCondition;
import com.biemo.cloud.system.modular.sys.model.params.SysMenuParam;
import com.biemo.cloud.system.modular.sys.service.SysAppService;
import com.biemo.cloud.system.modular.sys.service.SysMenuService;
import com.biemo.cloud.system.modular.sys.wrapper.MenuWrapper;
import com.biemo.cloud.core.util.ToolUtil;
import com.biemo.cloud.kernel.model.enums.StatusEnum;
import com.biemo.cloud.kernel.model.exception.RequestEmptyException;
import com.biemo.cloud.kernel.model.exception.ServiceException;
import com.biemo.cloud.kernel.model.response.ResponseData;
import com.biemo.cloud.kernel.scanner.modular.annotation.GetResource;
import com.biemo.cloud.kernel.scanner.modular.annotation.PostResource;
import com.biemo.cloud.kernel.scanner.modular.stereotype.ApiResource;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 菜单控制器
 *
 *
 * @Date 2019/9/26 22:49
 */
@RestController
@ApiResource(name = "菜单管理接口", path = "/menu")
@Api(tags = "菜单管理接口")
public class MenuController {

    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private SysAppService appService;

    /**
     * 获取左侧菜单列表
     *
     *
     * @Date 2018/2/28 16:45
     */
    @GetResource(name = "获取左侧菜单列表", path = "/getLeftMenuList")
    @ApiOperation("获取左侧菜单列表")
    public ResponseData getLeftMenuList(@RequestParam(value = "appId") String appId) {
        if (ToolUtil.isEmpty(appId) || appId.equals("undefined")) {
            throw new RequestEmptyException("appId为空");
        }

        LoginUser loginUser = LoginContext.me().getLoginUser();
        Long accountId = loginUser.getAccountId();

        List<MenuNode> leftMenuList = this.sysMenuService.getLeftMenuList(Long.valueOf(appId), accountId);
        return ResponseData.success(leftMenuList);
    }

    /**
     * 新增菜单
     *
     *
     * @Date 2018/2/9 15:12
     */
    @PostResource(name = "新增菜单", path = "/add")
    @ApiOperation("新增菜单")
    public ResponseData addMenu(@RequestBody SysMenuParam sysMenuParam) {
        this.sysMenuService.addMenu(sysMenuParam);
        return ResponseData.success();
    }

    /**
     * 编辑菜单
     *
     *
     * @Date 2018/2/9 16:39
     */
    @PostResource(name = "编辑菜单", path = "/update")
    @ApiOperation("编辑菜单")
    public ResponseData editMenu(@RequestBody SysMenuParam sysMenuParam) {
        this.sysMenuService.updateMenu(sysMenuParam);
        return ResponseData.success();
    }

    /**
     * 删除菜单
     *
     *
     * @Date 2018/2/9 16:40
     */
    @PostResource(name = "删除菜单", path = "/delete")
    @ApiOperation("删除菜单")
    public ResponseData deleteMenu(@RequestBody Map<String, Long[]> param) {
        Long[] menuIds = param.get("menuIds");
        if (ToolUtil.isEmpty(menuIds)) {
            throw new ServiceException(400, "菜单列表为空!");
        }
        this.sysMenuService.deleteMenuBatch(menuIds);
        return ResponseData.success();
    }

    /**
     * 启用菜单
     *
     *
     * @Date 2018/2/9 16:40
     */
    @GetResource(name = "启用禁用菜单", path = "/changeStatus")
    @ApiOperation("启用禁用菜单")
    public ResponseData enableMenu(@RequestParam(value = "menuId") Long menuId, @RequestParam("status") Integer status) {
        if (menuId == null || status == null) {
            throw new RequestEmptyException("请求菜单id或状态为空！");
        }

        this.sysMenuService.changeStatus(menuId, StatusEnum.toEnum(status));
        return ResponseData.success();
    }

    /**
     * 获取菜单列表
     *
     *
     * @Date 2018/2/9 16:40
     */
    @PostResource(name = "获取菜单列表", path = "/list")
    @ApiOperation("获取菜单列表")
    public ResponseData listMenu(@RequestBody MenuCondition menuCondition) {

        IPage<Map<String, Object>> menuList = this.sysMenuService.getMenuList(menuCondition);
        //包装菜单列表结果
        List<Map<String, Object>> records = menuList.getRecords();
        Object warp = new MenuWrapper(records).wrap();
        menuList.setRecords((List<Map<String, Object>>) warp);
        return ResponseData.success(menuList);
    }

    /**
     * 获取父级菜单列表
     *
     *
     * @Date 2018/2/26 15:01
     */
    @GetResource(name = "获取父级菜单列表", path = "/getSelectMenuTreeList")
    @ApiOperation("获取父级菜单列表")
    public ResponseData getSelectMenuTreeList(@RequestParam(value = "appId") Long appId) {
        if (appId == null) {
            throw new RequestEmptyException("应用id为空！");
        }
        List<TreeNode> appList = this.sysMenuService.getSelectMenuTreeList(appId);
        return ResponseData.success(appList);
    }

    /**
     * 获取菜单详情
     *
     *
     * @Date 2018/2/27 15:39
     */
    @GetResource(name = "获取菜单详情", path = "/getMenuDetail")
    @ApiOperation("获取菜单详情")
    public ResponseData getMenuDetail(@RequestParam(value = "menuId") Long menuId) {
        if (menuId == null) {
            throw new RequestEmptyException("菜单id为空！");
        }
        SysMenu menu = this.sysMenuService.getById(menuId);
        return ResponseData.success(menu);
    }

}

