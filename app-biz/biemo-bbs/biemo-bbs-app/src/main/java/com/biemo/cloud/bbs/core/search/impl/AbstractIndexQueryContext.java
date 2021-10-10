package com.biemo.cloud.bbs.core.search.impl;

import com.biemo.cloud.bbs.core.annotation.ESMapping;
import com.biemo.cloud.bbs.core.constant.ESBoolLogicConstants;
import com.biemo.cloud.bbs.core.constant.ESCompareWayConstants;
import com.biemo.cloud.bbs.core.constant.ESHierarchyConstants;
import com.biemo.cloud.bbs.core.search.IndexQueryContext;
import com.biemo.cloud.bbs.core.search.utils.IndexKeyUtils;
import com.biemo.cloud.bbs.core.search.utils.IndexQueryContextUtils;
import com.biemo.cloud.core.util.StringUtils;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.*;

public abstract class AbstractIndexQueryContext implements IndexQueryContext {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    private Map<String,List<AbstractQueryBuilder>> firstBoolLogicQueryMap = new HashMap<>();

    private Map<String,List<AbstractQueryBuilder>> secondBoolLogicQueryMap = new HashMap<>();

    private Map<String,List<AbstractQueryBuilder>> thirdBoolLogicQueryMap = new HashMap<>();

    private Map<String,List<AbstractQueryBuilder>> nestedQueryMap = new HashMap<>();

    protected String indexName;

    protected int pageNum;
    protected int pageSize;
    protected QueryBuilder query;
    protected List<SortBuilder> sortBuilders = new ArrayList<>();
    // 精确统计  匹配数超过默认值10000时  总数返回10000
    protected boolean exactCount = true;

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setQuery(QueryBuilder query) {
        this.query = query;
    }

    public void setExactCount(boolean exactCount){ this.exactCount=exactCount; }

    @Override
    public boolean exactCount() {
        return exactCount;
    }

    @Override
    public QueryBuilder getQuery() {
        // 如果手动设置了QueryBuilder 则用手动设置的
        if(Objects.nonNull(query)) return query;

        QueryBuilder queryBuilder = buildQuery(this);
        IndexQueryContextUtils.restoreEsMapping(this);
        return queryBuilder;
    }

    private QueryBuilder buildQuery(Object object){
        return buildQuery(object,false);
    }

    private QueryBuilder buildQuery(Object object,boolean nested){
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        Map<Field, ESMapping> fieldESMappingMap = IndexQueryContextUtils.getFieldESMappingMap(object);

        Map<Field,ESMapping> orderFieldESMapping = IndexQueryContextUtils.getOrderFiledESMappingMap(object);
        try{

            for(Map.Entry<Field,ESMapping> entry:orderFieldESMapping.entrySet()){
                Field key = entry.getKey();
                ESMapping value = entry.getValue();
                SortOrder sortOrder = value.order();
                sortBuilders.add(SortBuilders.fieldSort(key.getName()).order(sortOrder));
            }

            for(Map.Entry<Field,ESMapping> entry:fieldESMappingMap.entrySet()){
                Field key = entry.getKey();
                ESMapping value = entry.getValue();
                String type = value.type();
                // 根据类型  匹配不同的queryBuilder
                key.setAccessible(true);
                matchType(type,key.getName(),key.get(object),value,nested);
            }
            // 从map 中构建query
            if(nested){
                buildQueryFromMap(boolQueryBuilder,nestedQueryMap);
            }else{
                buildQueryFromHierarchyMap(boolQueryBuilder);
            }

        }catch (Exception e){
            logger.error("获取QueryBuilder失败,错误如下{}",e);
            throw new RuntimeException("获取QueryBuilder失败",e);
        }
        return boolQueryBuilder;
    }

    private void buildQueryFromHierarchyMap(BoolQueryBuilder boolQueryBuilder) {
        if(!firstBoolLogicQueryMap.isEmpty()){
            buildQueryFromMap(boolQueryBuilder,firstBoolLogicQueryMap);
            if(!secondBoolLogicQueryMap.isEmpty()){
                BoolQueryBuilder secondBoolQuery = QueryBuilders.boolQuery();
                buildQueryFromMap(secondBoolQuery,secondBoolLogicQueryMap);
                boolQueryBuilder.must(secondBoolQuery);
                if(!thirdBoolLogicQueryMap.isEmpty()){
                    BoolQueryBuilder thirdBoolQuery = QueryBuilders.boolQuery();
                    buildQueryFromMap(thirdBoolQuery,thirdBoolLogicQueryMap);
                    secondBoolQuery.must(thirdBoolQuery);
                }
            }

        }
    }


    @Override
    public List<SortBuilder> getSorts() {

        return sortBuilders;
    }



    /**
     * 构建queryBuild从 map中
     * @param boolQueryBuilder
     */
    private void buildQueryFromMap(BoolQueryBuilder boolQueryBuilder,Map<String,List<AbstractQueryBuilder>> queryMap) {
        if(!queryMap.isEmpty()){
            List<AbstractQueryBuilder> mustList = queryMap.getOrDefault(ESBoolLogicConstants.MUST,new ArrayList<>());
            for (AbstractQueryBuilder queryBuilder:mustList){
                boolQueryBuilder = boolQueryBuilder.must(queryBuilder);
            }
            List<AbstractQueryBuilder> mustNotList = queryMap.getOrDefault(ESBoolLogicConstants.MUST_NOT,new ArrayList<>());
            for (AbstractQueryBuilder queryBuilder:mustNotList){
                boolQueryBuilder = boolQueryBuilder.mustNot(queryBuilder);
            }
            List<AbstractQueryBuilder> shouldList = queryMap.getOrDefault(ESBoolLogicConstants.SHOULD,new ArrayList<>());
            for (AbstractQueryBuilder queryBuilder:shouldList){
                boolQueryBuilder = boolQueryBuilder.should(queryBuilder);
            }
        }

        queryMap.clear();
    }

    /**
     * 根据类型 匹配 出对应的 QueryBuilder
     * @param type
     * @param key
     * @param value
     * @param esMapping
     * @return
     */
    private void matchType(String type, String key, Object value, ESMapping esMapping,boolean nested){
        String boolLogic = esMapping.boolLogic();
        validBoolLogic(boolLogic,key);
        AbstractQueryBuilder queryBuilder;
        String[] queryFields = esMapping.queryField();
        if(Objects.nonNull(queryFields)&&queryFields.length>0){
            key = queryFields[0];
        }
        int hierarchy = esMapping.hierarchy();
        float boost = esMapping.boost();
        String path = esMapping.path();
        switch (type){
            case IdsQueryBuilder.NAME:
                queryBuilder = QueryBuilders.idsQuery().addIds(((Collection<String>)value).stream().toArray(String[]::new));
                break;
            case WildcardQueryBuilder.NAME:
                queryBuilder = QueryBuilders.wildcardQuery(key,"*"+value.toString()+"*");
                break;
            case NestedQueryBuilder.NAME:
                // 默认会平均所有匹配的nested文本的分数。当然，也可以通过设定score_modec参数为avg,max,sum,或者甚至为none(根文本获得一致评分1.0)。
                // 根据 类型 选择不同的查询器
                if(!path.isEmpty()){
                    key = path;
                }
                queryBuilder =
                    QueryBuilders.nestedQuery(key, buildQuery(value,true), ScoreMode.None);
                break;
            case TermsQueryBuilder.NAME:
                if(value instanceof Collection){
                    queryBuilder = QueryBuilders.termsQuery(key,(Collection)value);
                }else{
                    queryBuilder = QueryBuilders.termsQuery(key,value);
                }
                break;
            case MatchQueryBuilder.NAME:
                queryBuilder = QueryBuilders.matchQuery(key,value.toString());
                break;
            case MatchPhraseQueryBuilder.NAME:

                queryBuilder  = QueryBuilders.matchPhraseQuery(key,value);
                break;
            case RangeQueryBuilder.NAME:

                RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(key);
                String format = esMapping.format();
                if(!format.isEmpty()){
                    rangeQueryBuilder.format(format);
                }
                String compareWay = esMapping.compareWay();
                validCompareWay(compareWay,key);
                switch (compareWay){
                    case ESCompareWayConstants.GTE:
                        // 大于等于
                        rangeQueryBuilder.gte(value);
                        break;
                    case ESCompareWayConstants.LTE:
                        // 小于 等于
                        rangeQueryBuilder.lte(value);
                        break;
                    case ESCompareWayConstants.LT:
                        // 小于
                        rangeQueryBuilder.lt(value);
                        break;
                    case ESCompareWayConstants.GT:
                        // 大于
                        rangeQueryBuilder.gt(value);
                        break;
                }
                queryBuilder = rangeQueryBuilder;
                break;
            case QueryStringQueryBuilder.NAME:
                QueryStringQueryBuilder queryStringQueryBuilder =QueryBuilders.queryStringQuery(IndexKeyUtils.strToESExp(value.toString()));
                if(Objects.nonNull(queryFields)&&queryFields.length>0){
                    for (String queryField:queryFields){
                        if(StringUtils.isNotEmpty(queryField)){
                            queryStringQueryBuilder.field(queryField);
                        }
                    }
                }
                queryBuilder = queryStringQueryBuilder;
                break;
            case SimpleQueryStringBuilder.NAME:
                SimpleQueryStringBuilder simpleQueryStringBuilder =QueryBuilders.simpleQueryStringQuery(IndexKeyUtils.strToESExp(value.toString()));
                if(Objects.nonNull(queryFields)&&queryFields.length>0){
                    for (String queryField:queryFields){
                        if(StringUtils.isNotEmpty(queryField)){
                            simpleQueryStringBuilder.field(queryField);
                        }
                    }
                }
                queryBuilder = simpleQueryStringBuilder;
                break;
            case ExistsQueryBuilder.NAME:
                boolLogic = (Boolean)value?ESBoolLogicConstants.MUST:ESBoolLogicConstants.MUST_NOT;
                queryBuilder = QueryBuilders.existsQuery(key);
                break;
            default:
                queryBuilder = QueryBuilders.termQuery(key,value.toString());
                break;
        }
        // 添加权重
        queryBuilder.boost(boost);
        // nested 嵌套查询时不放入map中
        if(nested){
            nestedQueryMap.computeIfAbsent(boolLogic,k->new ArrayList<>()).add(queryBuilder);
        }else{
            if(hierarchy== ESHierarchyConstants.FIRST){
                firstBoolLogicQueryMap.computeIfAbsent(boolLogic,k->new ArrayList<>()).add(queryBuilder);
            }else if(hierarchy==ESHierarchyConstants.SECOND){
                secondBoolLogicQueryMap.computeIfAbsent(boolLogic,k->new ArrayList<>()).add(queryBuilder);
            }else if(hierarchy==ESHierarchyConstants.THIRD){
                thirdBoolLogicQueryMap.computeIfAbsent(boolLogic,k->new ArrayList<>()).add(queryBuilder);
            }else{
                logger.error("请手动构建query builder");
                throw new RuntimeException("请手动构建 query builder");
            }
        }

    }

    /**
     * 检测 boolLogic 是否存在
     * @param boolLogic
     * @param key 字段名称
     */
    private void validBoolLogic(String boolLogic,String key){
        if(!ESBoolLogicConstants.allEsBoolLogic().contains(boolLogic)){
            logger.error(key+"字段上@ESMapping boolLogic属性错误");
            throw new RuntimeException(key+"字段上@ESMapping boolLogic属性不存在对应的类型："+boolLogic);
        }
    }

    private void validCompareWay(String compareWay,String key){
        if(!ESCompareWayConstants.allCompareWay().contains(compareWay)){
            logger.error(key+"字段上@ESMapping compareWay属性错误");
            throw new RuntimeException(key+"字段上@ESMapping compareWay属性不存在对应的比较方式："+compareWay);
        }
    }
}
