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

import com.biemo.cloud.kernel.logger.constants.KafkaConstants;
import com.biemo.cloud.kernel.logger.entity.SendingCommonLog;
import com.biemo.cloud.kernel.logger.entity.SendingTCLog;
import com.biemo.cloud.kernel.logger.entity.SendingTraceLog;
import com.biemo.cloud.kernel.logger.service.LogProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * 发送日志到消息队列的实现类
 *
 *
 * @date 2018-04-25 10:37
 */
public class LogProducerServiceImpl implements LogProducerService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private ExecutorService executorService = Executors.newFixedThreadPool(10);

    @Autowired
    private KafkaTemplate<String, Object> template;

    @Override
    public void sendMsg(SendingCommonLog log) {

        executorService.execute(() -> {
            try {
                template.send(KafkaConstants.LOG_TOPIC, KafkaConstants.LOG_TOPIC_KEY, log);
            } catch (Exception e) {
                logger.error("记录普通日志到kafka错误!", e);
            }
        });

    }

    @Override
    public void sendTraceMsg(SendingTraceLog sendingTraceLog) {

        executorService.execute(() -> {
            try {
                template.send(KafkaConstants.TRACE_LOG_TOPIC, KafkaConstants.TRACE_LOG_TOPIC_KEY, sendingTraceLog);
            } catch (Exception e) {
                logger.error("记录trace日志到kafka错误!", e);
            }
        });

    }

    @Override
    public void sendTcMsg(SendingTCLog sendingTCLog) {

        executorService.execute(() -> {
            try {
                template.send(KafkaConstants.TC_LOG_TOPIC, KafkaConstants.TC_LOG_TOPIC_KEY, sendingTCLog);
            } catch (Exception e) {
                logger.error("记录trace日志到kafka错误!", e);
            }
        });
    }

}
