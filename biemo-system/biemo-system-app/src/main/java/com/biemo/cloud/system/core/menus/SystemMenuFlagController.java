package com.biemo.cloud.system.core.menus;

import com.biemo.cloud.core.request.RequestData;
import com.biemo.cloud.kernel.model.response.ResponseData;
import com.biemo.cloud.kernel.scanner.modular.stereotype.ApiResource;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统管理菜单标识控制器
 *
 *
 * @Date 2019/9/17 17:47
 */
@RestController
@ApiResource(name = "系统菜单", path = "/systemMenu")
public class SystemMenuFlagController {

    @ApiResource(name = "系统管理菜单", path = "/system", menuFlag = true)
    public ResponseData system(RequestData requestData) {
        return ResponseData.success();
    }

    @ApiResource(name = "应用管理", path = "/appManager", menuFlag = true)
    public ResponseData appManager(RequestData requestData) {
        return ResponseData.success();
    }

    @ApiResource(name = "字典管理", path = "/dicManager", menuFlag = true)
    public ResponseData dicManager(RequestData requestData) {
        return ResponseData.success();
    }

}
