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
package com.biemo.cloud.kernel.logger.service.impl;

import com.biemo.cloud.kernel.logger.entity.SendingCommonLog;
import com.biemo.cloud.kernel.logger.entity.SendingTCLog;
import com.biemo.cloud.kernel.logger.entity.SendingTraceLog;
import com.biemo.cloud.kernel.logger.service.LogProducerService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

/**
 * 采用slf4j日志
 *
 *
 * @Date 2020/2/13 22:00
 */
@Slf4j
public class Slf4jLogProducerServiceImpl implements LogProducerService {

    @Override
    public void sendMsg(SendingCommonLog sendingCommonLog) {
        log.info(JSON.toJSONString(sendingCommonLog));
    }

    @Override
    public void sendTraceMsg(SendingTraceLog sendingTraceLog) {
        log.info(JSON.toJSONString(sendingTraceLog));
    }

    @Override
    public void sendTcMsg(SendingTCLog sendingTCLog) {
        log.info(JSON.toJSONString(sendingTCLog));
    }

}
