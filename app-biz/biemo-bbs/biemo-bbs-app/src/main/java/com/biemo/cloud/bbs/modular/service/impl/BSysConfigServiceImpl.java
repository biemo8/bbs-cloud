package com.biemo.cloud.bbs.modular.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biemo.cloud.bbs.modular.domain.response.BSysConfigResponse;
import com.biemo.cloud.core.util.StringUtils;
import org.springframework.stereotype.Service;
import com.biemo.cloud.bbs.modular.mapper.BSysConfigMapper;
import com.biemo.cloud.bbs.modular.domain.BSysConfig;
import com.biemo.cloud.bbs.modular.service.IBSysConfigService;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service业务层处理
 *
 * @author makesoft
 * @date 2021-08-11
 */
@Service
public class BSysConfigServiceImpl extends ServiceImpl<BSysConfigMapper,BSysConfig> implements IBSysConfigService
{

    @Override
    public BSysConfigResponse customSysconfig() {
        List<BSysConfig> sysConfigList = this.list();
        BSysConfigResponse bSysConfigResponse = new BSysConfigResponse();
        if(sysConfigList!=null&&sysConfigList.size()>0){
            sysConfigList.forEach(config->{
                if("articlePending".equals(config.getKey())){
                    bSysConfigResponse.setArticlePending(StringUtils.isNotEmpty(config.getValue())&&"true".equals(config.getValue()));
                }else if("createArticleEmailVerified".equals(config.getKey())){
                    bSysConfigResponse.setCreateArticleEmailVerified(StringUtils.isNotEmpty(config.getValue())&&"true".equals(config.getValue()));
                }
            });
        }
        return bSysConfigResponse;
    }

    @Override
    @Transactional
    public Map<String, Object> coverSysconfigToMap() {
        List<BSysConfig> sysConfigList = this.list();
        Map<String,Object> result = new HashMap<>();
        if(sysConfigList!=null&&sysConfigList.size()>0){
            sysConfigList.forEach(config->{
                String value = config.getValue();
                boolean flag = StringUtils.isJson(value);
                boolean isArray = flag?StringUtils.isArray(value):false;
                if(StringUtils.isEmpty(value)||!flag){
                    if("false".equals(value)){
                        result.put(config.getKey(),false);
                    }else if("true".equals(value)){
                        result.put(config.getKey(),true);
                    }else if(value.matches("[0-9]+")){
                        result.put(config.getKey(),Long.valueOf(value));
                    }else{
                        result.put(config.getKey(),value);
                    }
                }else{
                    result.put(config.getKey(),isArray? JSONArray.parseArray(value):JSONObject.parse(value));
                }
            });
        }
        return result;
    }

}
