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
package com.biemo.cloud.kernel.logger.chain.enums;

/**
 * 远程调用阶段类型枚举
 *
 *
 * @date 2018-05-15-下午3:51
 */
public enum RpcPhaseEnum {


    G1,     //网关发送请求(网关前置拦截器)

    P1,     //控制器接受到请求(controllerAOP)

    P2,     //准备调用远程服务(consumerAOP)

    P3,     //被调用端接收到请求(providerAOP)

    EP1,    //控制器处理过程中出错(controllerAOP)

    EP2,    //feign远程调用，调用方出错(consumerAOP)

    EP3,    //feign远程调用，被调用方出错(providerAOP)

    G2,     //网关接收到成功请求(网关后置拦截器)

    EG2,    //网关接收到错误响应(网关后置拦截器)

    TC      //记录请求耗时的类型

}
