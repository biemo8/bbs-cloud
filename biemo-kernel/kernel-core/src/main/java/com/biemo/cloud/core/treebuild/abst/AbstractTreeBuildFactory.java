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
package com.biemo.cloud.core.treebuild.abst;

import java.util.List;

/**
 * 树构建的抽象类，定义构建tree的基本步骤
 *
 *
 * @Date 2018/7/25 下午5:59
 */
public abstract class AbstractTreeBuildFactory<T> {

    /**
     * 树节点构建整体过程
     *
     *
     * @Date 2018/7/26 上午9:45
     */
    public List<T> doTreeBuild(List<T> nodes) {

        //构建之前的节点处理工作
        List<T> readyToBuild = beforeBuild(nodes);

        //具体构建的过程
        List<T> builded = executeBuilding(readyToBuild);

        //构建之后的处理工作
        return afterBuild(builded);
    }

    /**
     * 构建之前的处理工作
     *
     *
     * @Date 2018/7/26 上午10:10
     */
    protected abstract List<T> beforeBuild(List<T> nodes);

    /**
     * 具体的构建过程
     *
     *
     * @Date 2018/7/26 上午10:11
     */
    protected abstract List<T> executeBuilding(List<T> nodes);

    /**
     * 构建之后的处理工作
     *
     *
     * @Date 2018/7/26 上午10:11
     */
    protected abstract List<T> afterBuild(List<T> nodes);
}
