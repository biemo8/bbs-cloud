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
package com.biemo.cloud.kernel.scanner.modular.model;

import lombok.Data;

import java.util.List;

/**
 * 资源树
 *
 *
 * @date 2018-01-11 14:56
 */
@Data
public class ResourceTreeNode {

    /**
     * 资源中文名称
     */
    private String name;

    /**
     * 资源的编码
     */
    private String code;

    /**
     * 资源子节点
     */
    private List<ResourceTreeNode> children;

    public ResourceTreeNode() {
    }

    public ResourceTreeNode(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public ResourceTreeNode(String name, String code, List<ResourceTreeNode> children) {
        this.name = name;
        this.code = code;
        this.children = children;
    }

}
