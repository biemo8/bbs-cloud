//package com.biemo.cloud.system.core.mq.listener;
//
//import com.biemo.cloud.system.core.mq.dto.MessageDto;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
//import org.apache.rocketmq.spring.core.RocketMQListener;
//import org.springframework.stereotype.Service;
//
///**
// * rocketmq的消费者
// *
// *
// * @Date 2019/9/4 20:56
// */
//@Service
//@Slf4j
//@RocketMQMessageListener(consumerGroup = "test-group", topic = "test-mq-topic")
//public class RocketMqListener implements RocketMQListener<MessageDto> {
//
//    @Override
//    public void onMessage(MessageDto message) {
//        log.info("收到消息：" + message);
//    }
//
//}
