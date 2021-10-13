package com.biemo.cloud.bbs.modular.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biemo.cloud.bbs.core.constant.IndexConstants;
import com.biemo.cloud.bbs.core.search.IndexQueryContext;
import com.biemo.cloud.bbs.core.search.impl.TopicIndexQueryContext;
import com.biemo.cloud.bbs.core.search.utils.IndexKeyUtils;
import com.biemo.cloud.bbs.core.search.utils.IndexQueryContextUtils;
import com.biemo.cloud.bbs.modular.domain.BTopic;
import com.biemo.cloud.bbs.modular.domain.event.TopicSaveEvent;
import com.biemo.cloud.bbs.modular.service.IBTopicService;
import com.biemo.cloud.bbs.utils.MarkDown2HtmlUtils;
import com.biemo.cloud.core.exception.CustomException;
import com.biemo.cloud.core.page.PageFactory;
import com.biemo.cloud.core.util.StringUtils;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service("topicIndexService")
public class TopicIndexServiceImpl extends AbstractIndex<BTopic> {

    /** 索引名称 */
    public static final String INDEX_NAME = IndexConstants.TOPIC_INDEX;
    private String similarityName = "custom_similarity";
    private String k1 = "1.2";
    private String b = "0.75";


    @Autowired
    IBTopicService topicService;

    @Override
    public XContentBuilder buildMappingContext() {
        try( XContentBuilder builder = XContentFactory.jsonBuilder()){
            builder.startObject();
            //数据只索引不存储
            builder.startObject("_source").field("enabled","false").endObject();
            builder.startObject("properties");
            builder.startObject("id").field("type","long").endObject();
            //builder.startObject("titleAnalyzer").field("type","text").field("analyzer", "standard").field("similarity", similarityName).endObject();
            //builder.startObject("contentAnalyzer").field("type","text").field("analyzer", "standard").field("similarity", similarityName).endObject();
            builder.startObject("title").field("type","text").field("analyzer", "standard").endObject();
            builder.startObject("content").field("type","text").field("analyzer", "standard").endObject();
            builder.startObject("createTime").field("type","date").field("format", "yyyy-MM-dd HH:mm:ss.SSS||yyyy-MM-dd HH:mm:ss||yyyyMMddHHmmss||yyyyMMdd||epoch_millis").endObject();
            builder.endObject();
            builder.endObject();
            return builder;
        }
        catch (IOException e) {
            logger.error("创建索引出错，错误如下：{}",e);
            throw new CustomException("创建索引出错",e);
        }
    }

    public Page<BTopic> search(TopicIndexQueryContext topicIndexQueryContext) {
        // 标题或内容
//        if(Objects.nonNull(topicIndexQueryContext.getType())&&3==topicIndexQueryContext.getType()){
            IndexQueryContextUtils.modifyBoolLogicShould(topicIndexQueryContext,"topicTitleKeyword","topicContentKeyword");
//        }
        return super.search(topicIndexQueryContext);
    }

    protected BTopic loadData(IndexQueryContext context, SearchHit hit) {
        String id = hit.getId();
        if (Objects.isNull(id) || StringUtils.isEmpty(id)) return null;
        return topicService.getById(id);
    }

    public List<BTopic> batchLoadData(IndexQueryContext context,SearchHit[] hitArr) {
        List<String> ids = new ArrayList<>();
        for(SearchHit hit:hitArr){
            String id = hit.getId();
            if (Objects.isNull(id) || StringUtils.isEmpty(id)) continue;
            ids.add(id);
        }
        return topicService.listByIds(ids);
    }

    public boolean createIndex(int shards, int replicas, boolean rebuild) {
        return this.createIndex(shards,replicas,rebuild,INDEX_NAME);
    }

    @Override
    public void buildIndex() throws IOException {
        if(true){
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.orderByDesc("create_time");
            Page<BTopic> page = topicService.page(PageFactory.defaultPage(),queryWrapper);
            List<BTopic> bTopics = page.getRecords();
            if(bTopics==null||bTopics.size()==0){
                return;
            }
            this.saveIndex(INDEX_NAME,bTopics);
        }
    }

    @Override
    @EventListener(classes = {TopicSaveEvent.class})
    public void saveIndex(ApplicationEvent event) throws IOException {
        TopicSaveEvent saveEvent = (TopicSaveEvent)event;
        List<BTopic> bTopics = saveEvent.getTopics();
        this.saveIndex(INDEX_NAME,bTopics);
    }

    @Override
    public void deleteIndex(ApplicationEvent event) {

    }

    /**
     * 索引设置
     */
    @Override
    protected Settings.Builder createSettings(int shards, int replicas) {
        // 设置分片数和副本数、自定义相似性算法
        return Settings.builder().put("index.number_of_shards", shards).put("index.number_of_replicas", replicas)
                .put("similarity." + similarityName + ".type","BM25").put("similarity." + similarityName + ".k1",k1).put("similarity." + similarityName + ".b", b)
                .put("similarity." + similarityName + ".discount_overlaps","false");
    }
}
