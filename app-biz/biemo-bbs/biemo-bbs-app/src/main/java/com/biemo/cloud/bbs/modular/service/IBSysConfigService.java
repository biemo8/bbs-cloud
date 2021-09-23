package com.biemo.cloud.bbs.modular.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.biemo.cloud.bbs.modular.domain.BSysConfig;
import com.biemo.cloud.bbs.modular.domain.response.BSysConfigResponse;

/**
 * Service接口
 *
 * @author makesoft
 * @date 2021-08-11
 */
public interface IBSysConfigService extends IService<BSysConfig>
{

    BSysConfigResponse customSysconfig();

    Map<String,Object> coverSysconfigToMap();
}
