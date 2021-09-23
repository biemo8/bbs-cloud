package com.biemo.cloud.kernel.model.api.model;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.biemo.cloud.kernel.model.request.AbstractBaseRequest;
import com.biemo.cloud.kernel.model.resource.ResourceDefinition;
import lombok.Data;

import java.util.Map;

/**
 * 汇报资源请求
 *
 *
 * @Date 2019/5/13 20:51
 */
@Data
public class ReportResourceReq extends AbstractBaseRequest {

    /**
     * 项目编码（如果您不设置的话，默认使用spring.application.name填充）
     * <p>
     * 修复一个项目启动的时候会误删别的项目资源的问题
     *
     * @since 2.2.12
     */
    private String projectCode;

    public ReportResourceReq(){}

    /**
     * 资源集合
     */
    private Map<String, Map<String, ResourceDefinition>> resourceDefinitions;

    public ReportResourceReq(String projectCode, Map<String, Map<String, ResourceDefinition>> resourceDefinitions) {
        this.projectCode = projectCode;
        this.resourceDefinitions = resourceDefinitions;
    }

    @Override
    public String checkParam() {
        if (StrUtil.isEmpty(projectCode)) {
            return "请求应用编码为空！";
        }
        if (CollectionUtil.isEmpty(resourceDefinitions)) {
            return "请求资源为空！";
        }
        return null;
    }
}
