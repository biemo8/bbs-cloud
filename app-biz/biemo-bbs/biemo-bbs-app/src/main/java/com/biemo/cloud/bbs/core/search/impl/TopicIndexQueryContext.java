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

    /** 类别 */
    @ESMapping
    private Integer type;

    @ESMapping(type= TermsQueryBuilder.NAME,queryField = "type")
    private List<Integer> types;

    /** 主题名称 */
    @ESMapping
    private String title;

    @ESMapping(type= TermsQueryBuilder.NAME,queryField = "title")
    private List<String> titles;

    /** 标题查询  */
    @ESMapping(type = MatchPhraseQueryBuilder.NAME, queryField = "titleAnalyzer")
    private String titleAnalyzer;

    /** 内容查询 */
    @ESMapping(type = MatchPhraseQueryBuilder.NAME,queryField = "contentAnalyzer")
    private String content;

    /** 标题匹配条件 */
    @ESMapping(type = QueryStringQueryBuilder.NAME,queryField = "titleAnalyzer",hierarchy = ESHierarchyConstants.SECOND, boost = 1000.0f)
    private String topicTitleKeyword;

    /** 标题过滤条件 */
    @ESMapping(type = QueryStringQueryBuilder.NAME,queryField = "titleAnalyzer",boolLogic = ESBoolLogicConstants.MUST_NOT)
    private String topicTitleFilterKeyword;

    /** 内容匹配条件 */
    @ESMapping(type = QueryStringQueryBuilder.NAME,queryField = "contentAnalyzer",hierarchy = ESHierarchyConstants.SECOND, boost = 1.0f)
    private String topicContentKeyword;

    /** 内容过滤条件 */
    @ESMapping(type = QueryStringQueryBuilder.NAME,queryField = "contentAnalyzer",boolLogic = ESBoolLogicConstants.MUST_NOT)
    private String topicContentFilterKeyword;

    /** 文章创建时间 */
    @ESMapping(type = RangeQueryBuilder.NAME,format = "yyyy-MM-dd",queryField = "createTime",compareWay = ESCompareWayConstants.GTE)
    private String createTime;

    private String orderBy;

    private String sort;

}
