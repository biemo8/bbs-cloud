package com.biemo.cloud.bbs.modular.controller;

import com.biemo.cloud.bbs.api.BLinkApi;
import com.biemo.cloud.bbs.modular.service.IBLinkService;
import com.biemo.cloud.bbs.modular.service.IBSysConfigService;
import com.biemo.cloud.kernel.model.response.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/link")
public class BLinkController extends BaseController implements BLinkApi {
    @Autowired
    private IBLinkService ibLinkService;

    @Override
    public ResponseData toplinks(){
        return ResponseData.success(ibLinkService.list());
    }
}
