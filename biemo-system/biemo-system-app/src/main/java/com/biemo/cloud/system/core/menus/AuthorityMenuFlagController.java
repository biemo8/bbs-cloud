package com.biemo.cloud.system.core.menus;

import com.biemo.cloud.core.request.RequestData;
import com.biemo.cloud.kernel.model.response.ResponseData;
import com.biemo.cloud.kernel.scanner.modular.stereotype.ApiResource;
import org.springframework.web.bind.annotation.RestController;

/**
 * 权限管理菜单控制器
 *
 *
 * @Date 2019/9/17 17:47
 */
@RestController
@ApiResource(name = "资源权限菜单", path = "/authorityMenu")
public class AuthorityMenuFlagController {

    @ApiResource(name = "资源权限菜单", path = "/authorityMenu", menuFlag = true)
    public ResponseData authorityMenu(RequestData requestData) {
        return ResponseData.success();
    }

    @ApiResource(name = "角色管理", path = "/roleManager", menuFlag = true)
    public ResponseData roleManager(RequestData requestData) {
        return ResponseData.success();
    }

    @ApiResource(name = "权限管理", path = "/permissionManager", menuFlag = true)
    public ResponseData permissionManager(RequestData requestData) {
        return ResponseData.success();
    }

    @ApiResource(name = "菜单管理", path = "/menuManager", menuFlag = true)
    public ResponseData menuManager(RequestData requestData) {
        return ResponseData.success();
    }

    @ApiResource(name = "资源管理", path = "/resourceManager", menuFlag = true)
    public ResponseData resourceManager(RequestData requestData) {
        return ResponseData.success();
    }

}
