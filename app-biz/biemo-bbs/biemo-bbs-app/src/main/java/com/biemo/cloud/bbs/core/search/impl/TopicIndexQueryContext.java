package com.biemo.cloud.bbs.core.search.impl;

import com.biemo.cloud.bbs.core.annotation.ESMapping;
import com.biemo.cloud.bbs.core.constant.ESBoolLogicConstants;
import com.biemo.cloud.bbs.core.constant.ESCompareWayConstants;
import com.biemo.cloud.bbs.core.constant.ESHierarchyConstants;
import com.biemo.cloud.bbs.core.constant.IndexConstants;
import org.elasticsearch.index.query.*;

import java.util.List;

public class TopicIndexQueryContext extends AbstractIndexQueryContext {


    public String getIndexName() {
        return IndexConstants.TOPIC_INDEX;
    }


    public boolean isValid() {
        return true;
    }


    /** 主键 */
    @ESMapping(queryField = "id")
    private Long id;

    @ESMapping(type= IdsQueryBuilder.NAME)
    private List<Long> ids;

    /** 主题名称 */
    @ESMapping( type= MatchPhraseQueryBuilder.NAME, queryField = "title",boolLogic = ESBoolLogicConstants.SHOULD)
    private String title;

//    @ESMapping(type= TermsQueryBuilder.NAME,queryField = "title")
//    private List<String> titles;

    /** 内容查询 */
    @ESMapping(type = MatchPhraseQueryBuilder.NAME,queryField = "content",boolLogic = ESBoolLogicConstants.SHOULD )
    private String content;

    /** 文章创建时间 */
    @ESMapping(type = RangeQueryBuilder.NAME,format = "yyyy-MM-dd",queryField = "createTime",compareWay = ESCompareWayConstants.GTE)
    private String createTime;

    private String orderBy;

    private String sort;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
