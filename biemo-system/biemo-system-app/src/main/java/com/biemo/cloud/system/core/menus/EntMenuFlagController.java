package com.biemo.cloud.system.core.menus;

import com.biemo.cloud.core.request.RequestData;
import com.biemo.cloud.kernel.model.response.ResponseData;
import com.biemo.cloud.kernel.scanner.modular.stereotype.ApiResource;
import org.springframework.web.bind.annotation.RestController;

/**
 * 组织架构菜单标识控制器
 *
 *
 * @Date 2019/9/17 17:47
 */
@RestController
@ApiResource(name = "组织架构", path = "/entMenu")
public class EntMenuFlagController {

    @ApiResource(name = "组织架构菜单", path = "/enterprise", menuFlag = true)
    public ResponseData system(RequestData requestData) {
        return ResponseData.success();
    }

    @ApiResource(name = "公司管理", path = "/company", menuFlag = true)
    public ResponseData company(RequestData requestData) {
        return ResponseData.success();
    }

    @ApiResource(name = "部门管理", path = "/dept", menuFlag = true)
    public ResponseData dept(RequestData requestData) {
        return ResponseData.success();
    }

    @ApiResource(name = "人员管理", path = "/personal", menuFlag = true)
    public ResponseData personal(RequestData requestData) {
        return ResponseData.success();
    }

    @ApiResource(name = "职位管理", path = "/duty", menuFlag = true)
    public ResponseData duty(RequestData requestData) {
        return ResponseData.success();
    }

}
