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
package com.biemo.cloud.kernel.model.resource;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * api资源的声明类
 *
 *
 * @date 2018-01-03-下午3:27
 */
@Data
public class ResourceDefinition implements Serializable {

    /**
     * 应用的标识
     */
    private String appCode;

    /**
     * 项目编码（如果您不设置的话，默认使用spring.application.name填充）
     * <p>
     * 修复一个项目启动的时候会误删别的项目资源的问题
     *
     * @since 2.2.12
     */
    private String projectCode;

    /**
     * 控制器类名称
     */
    private String className;

    /**
     * 控制器中的方法名称
     */
    private String methodName;

    /**
     * 资源所属模块
     */
    private String modularCode;

    /**
     * 模块中文名称
     */
    private String modularName;

    /**
     * 资源的标识
     */
    private String code;

    /**
     * 资源名称
     */
    private String name;

    /**
     * 资源的请求路径
     */
    private String url;

    /**
     * http请求方法
     */
    private String httpMethod;

    /**
     * 是否是菜单（true-是，false-否）
     */
    private Boolean menuFlag;

    /**
     * 是否需要登录
     */
    private Boolean requiredLogin;

    /**
     * 是否需要鉴权
     */
    private Boolean requiredPermission;

    /**
     * 资源添加日期
     */
    private Date createTime;

    /**
     * 初始化资源的机器的ip地址
     */
    private String ipAddress;

}
