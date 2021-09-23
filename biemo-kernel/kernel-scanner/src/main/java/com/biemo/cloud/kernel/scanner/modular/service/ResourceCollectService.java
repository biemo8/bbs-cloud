/**
 * Copyright  2018-2020 &   (admin@makesoft.cn)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.biemo.cloud.kernel.scanner.modular.service;

import cn.hutool.core.util.StrUtil;
import com.biemo.cloud.kernel.model.resource.ResourceDefinition;
import com.biemo.cloud.kernel.scanner.config.properties.ScannerProperties;
import com.biemo.cloud.kernel.scanner.modular.factory.ApiResourceFactory;
import com.biemo.cloud.kernel.scanner.modular.model.ResourceTreeNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 资源整合服务
 *
 *
 * @date 2018-01-11 14:25
 */
public class ResourceCollectService {

    /**
     * 应用前缀标识编码的前缀标识
     */
    public static final String APP_CODE_PREFIX = "appType-";

    /**
     * 控制器(模块)编码的前缀标识
     */
    public static final String CONTROLLER_CODE_PREFIX = "conType-";

    /**
     * 资源编码的前缀标识
     */
    public static final String RESOURCE_CODE_PREFIX = "resType-";

    private ApiResourceFactory apiResourceFactory;

    private ScannerProperties scannerProperties;

    public ResourceCollectService(ApiResourceFactory apiResourceFactory, ScannerProperties scannerProperties) {
        this.apiResourceFactory = apiResourceFactory;
        this.scannerProperties = scannerProperties;
    }

    /**
     * 获取所有应用的资源列表
     *
     * @param code         可以为应用的标识(前缀为appType-),可以为控制器标识(前缀为conType-),也可以是资源标识(前缀为resType-)
     * @param resourceName 资源名称
     *
     * @Date 2018/1/11 14:34
     */
    public List<ResourceDefinition> getAllAppResourceList(String code, String resourceName) {
        if (StrUtil.isBlank(code)) {
            List<ResourceDefinition> result = apiResourceFactory.getAllResources();
            return findResource(resourceName, result);
        } else {
            List<ResourceDefinition> result = null;
            if (code.startsWith(APP_CODE_PREFIX)) {
                result = apiResourceFactory.getAllResources();
            } else if (code.startsWith(CONTROLLER_CODE_PREFIX)) {
                result = apiResourceFactory.getResourcesByModularCode(code.replace(CONTROLLER_CODE_PREFIX, ""));
            } else if (code.startsWith(RESOURCE_CODE_PREFIX)) {
                ResourceDefinition resourceDefinitions = apiResourceFactory.getResource(code.replace(CONTROLLER_CODE_PREFIX, ""));
                result = Arrays.asList(resourceDefinitions);
            }
            return findResource(resourceName, result);
        }
    }

    /**
     * 获取资源树
     *
     *
     * @Date 2018/1/11 14:59
     */
    public List<ResourceTreeNode> getResourceTree() {
        ArrayList<ResourceTreeNode> resourceTreeNodes = new ArrayList<>();

        //收集本应用的所有资源(按模块分类)
        ResourceTreeNode appResources = new ResourceTreeNode();
        appResources.setName(scannerProperties.getAppName());
        appResources.setCode(APP_CODE_PREFIX + scannerProperties.getAppCode());
        appResources.setChildren(new ArrayList<>());

        //构建本应用每个模块的资源
        Map<String, Map<String, ResourceDefinition>> modularResources = apiResourceFactory.getModularResources();
        for (String modularCode : modularResources.keySet()) {
            ArrayList<ResourceTreeNode> modularResourceTreeNode = new ArrayList<>();
            Map<String, ResourceDefinition> stringResourceDefinitionMap = modularResources.get(modularCode);
            for (Map.Entry<String, ResourceDefinition> entry : stringResourceDefinitionMap.entrySet()) {
                ResourceDefinition value = entry.getValue();
                modularResourceTreeNode.add(new ResourceTreeNode(value.getName(), RESOURCE_CODE_PREFIX + value.getCode()));
            }
            appResources.getChildren().add(
                    new ResourceTreeNode(
                            apiResourceFactory.getResourceName(modularCode), CONTROLLER_CODE_PREFIX + modularCode, modularResourceTreeNode));
        }

        resourceTreeNodes.add(appResources);
        return resourceTreeNodes;
    }

    private List<ResourceDefinition> findResource(String resourceName, List<ResourceDefinition> resourceDefinitions) {
        if (StrUtil.isBlank(resourceName)) {
            return resourceDefinitions;
        } else {
            for (ResourceDefinition resourceDefinition : resourceDefinitions) {
                if (resourceDefinition.getName().equals(resourceName)) {
                    return Arrays.asList(resourceDefinition);
                }
            }
            return null;
        }
    }
}
