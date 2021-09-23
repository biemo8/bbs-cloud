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
package com.biemo.cloud.kernel.logger.entity;

import lombok.Data;

/**
 * 日志链实体类
 *
 *
 * @date 2018-05-16 09:40
 */
@Data
public class SendingTraceLog {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 应用编码
     */
    private String appCode;

    /**
     * ip地址
     */
    private String ip;

    /**
     * 请求路径
     */
    private String servletPath;

    /**
     * rpc调用类型，
     * G1,     //网关发送请求(网关前置拦截器)
     * <p>
     * P1,     //控制器接受到请求(controllerAOP)
     * <p>
     * P2,     //准备调用远程服务(consumerAOP)
     * <p>
     * P3,     //被调用端接收到请求(providerAOP)
     * <p>
     * EP1,    //控制器处理过程中出错(controllerAOP)
     * <p>
     * EP2,    //feign远程调用，调用方出错(consumerAOP)
     * <p>
     * EP3,    //feign远程调用，被调用方出错(providerAOP)
     * <p>
     * G2,     //网关接收到成功请求(网关后置拦截器)
     * <p>
     * EG2,    //网关接收到错误响应(网关后置拦截器)
     * <p>
     * TC      //记录请求耗时的类型
     */
    private String rpcPhase;

    /**
     * 唯一请求号
     */
    private String traceId;

    /**
     * 节点id
     */
    private String spanId;

    /**
     * 节点父id
     */
    private String parentSpanId;

    /**
     * 日志内容
     */
    private String content;

    /**
     * 生成时间戳
     */
    private Long createTimestamp;

}
