package com.biemo.cloud.bbs.modular.controller;

import com.biemo.cloud.bbs.api.BSysConfigApi;
import com.biemo.cloud.bbs.modular.service.IBSysConfigService;
import com.biemo.cloud.kernel.model.response.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/config")
public class BSysConfigController extends BaseController implements BSysConfigApi {
    @Autowired
    private IBSysConfigService sysConfigService;

    @Override
    public ResponseData configs(){
        return ResponseData.success(sysConfigService.coverSysconfigToMap());
    }
}
