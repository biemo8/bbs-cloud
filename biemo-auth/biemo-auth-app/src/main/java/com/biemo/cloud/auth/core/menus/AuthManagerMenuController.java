package com.biemo.cloud.auth.core.menus;

import com.biemo.cloud.core.request.RequestData;
import com.biemo.cloud.kernel.model.response.ResponseData;
import com.biemo.cloud.kernel.scanner.modular.stereotype.ApiResource;
import org.springframework.web.bind.annotation.RestController;

/**
 * 权限管理标识控制器
 *
 *
 * @Date 2019/12/5 20:57
 */
@RestController
@ApiResource(name = "权限管理所有菜单标识", path = "/authManagerMenuFlag")
public class AuthManagerMenuController {

    @ApiResource(name = "单点应用", path = "/ssoApp", menuFlag = true)
    public ResponseData ssoApp(RequestData requestData) {
        return ResponseData.success();
    }

    @ApiResource(name = "客户端管理", path = "/clientManager", menuFlag = true)
    public ResponseData clientManager(RequestData requestData) {
        return ResponseData.success();
    }

    @ApiResource(name = "安全审计", path = "/safetyAudit", menuFlag = true)
    public ResponseData safetyAudit(RequestData requestData) {
        return ResponseData.success();
    }

    @ApiResource(name = "登录日志", path = "/loginLog", menuFlag = true)
    public ResponseData loginLog(RequestData requestData) {
        return ResponseData.success();
    }

    @ApiResource(name = "会话管理", path = "/sessionManage", menuFlag = true)
    public ResponseData sessionManage(RequestData requestData) {
        return ResponseData.success();
    }

    @ApiResource(name = "资源维护", path = "/resMaintain", menuFlag = true)
    public ResponseData resMaintain(RequestData requestData) {
        return ResponseData.success();
    }

    @ApiResource(name = "万能密码", path = "/godKey", menuFlag = true)
    public ResponseData godKey(RequestData requestData) {
        return ResponseData.success();
    }
}
