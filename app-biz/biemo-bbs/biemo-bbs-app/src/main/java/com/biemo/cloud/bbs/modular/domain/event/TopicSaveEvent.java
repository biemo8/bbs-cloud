package com.biemo.cloud.bbs.modular.domain.event;

import com.biemo.cloud.bbs.modular.domain.BTopic;
import org.springframework.context.ApplicationEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * @author makesoft
 * @date 2021-03-19 17:13
 */
public class TopicSaveEvent extends ApplicationEvent {

    private List<BTopic> topics;

    public TopicSaveEvent(Object source, BTopic topic) {
        super(source);
        this.topics=new ArrayList<>(1);
        this.topics.add(topic);
    }

    public TopicSaveEvent(Object source, List<BTopic> topics) {
        super(source);
        this.topics=topics;
    }

    public List<BTopic> getTopics() {
        return topics;
    }

    public void setTopics(List<BTopic> topics) {
        this.topics = topics;
    }
}
