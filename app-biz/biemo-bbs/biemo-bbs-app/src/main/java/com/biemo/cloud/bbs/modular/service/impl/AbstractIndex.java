package com.biemo.cloud.bbs.modular.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biemo.cloud.bbs.core.search.IndexQueryContext;
import com.biemo.cloud.bbs.core.search.IndexSerializable;
import com.biemo.cloud.bbs.modular.service.IndexService;
import com.biemo.cloud.core.exception.CustomException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 抽象Index类
 * 主要包括 查询、创建、删除、保存、数据加载
 * @param <T>
 */
public abstract class AbstractIndex<T> implements IndexService<T> {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Autowired
    protected ObjectMapper objectMapper;

    // 构建ES 中的mapping
    public abstract XContentBuilder buildMappingContext();

    /**
     * 创建 Index
     * @param shards 分片数
     * @param replicas 备份数
     * @param rebuild 是否重建
     * @param indexName 索引名称
     * @return
     */
    protected boolean createIndex(int shards, int replicas, boolean rebuild,String indexName){
        boolean result = false;
        try {
            // 根据索引名称判断是否存在
            result = restHighLevelClient.indices().exists(new GetIndexRequest(indexName), RequestOptions.DEFAULT);
        } catch (IOException e) {
            logger.error("检查索引是否存在错误，请检查索引服务是否启动!，错误如下：{}",e);
            throw new CustomException("检查索引是否存在错误",e);
        }
        if (result && rebuild){
            try {
                // 索引存在且重建 则先删除
                restHighLevelClient.indices().delete(new DeleteIndexRequest(indexName), RequestOptions.DEFAULT);
            } catch (IOException e) {
                logger.error("删除索引错误，请检查索引服务是否启动!",e);
                throw new CustomException("删除索引错误",e);
            }
        }

        CreateIndexRequest createIndexRequest = new CreateIndexRequest(indexName);
        // 设置分片数和副本数
        createIndexRequest.settings(createSettings(shards,replicas));
        try {
            // 构建对应index的mapping
            createIndexRequest.mapping(buildMappingContext());
            // 发送创建index的请求
            CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(createIndexRequest,RequestOptions.DEFAULT);
            result = createIndexResponse.isAcknowledged();
        }catch (IOException e) {
            logger.error("创建索引出错，错误如下：{}",e);
            throw new CustomException("创建索引出错",e);
        }
        return result;
    }

    /**
     * 索引设置
     */
    protected Settings.Builder createSettings(int shards, int replicas){
        // 设置分片数和副本数
        return Settings.builder().put("index.number_of_shards", shards).put("index.number_of_replicas", replicas);
    }

    /**
     * 保存索引
     * @param indexName 索引名称
     * @param dataList 索引数据 需要实现IndexSerializable接口重写ObjectIdToElasticsearchId()和toElasticsearchJson()
     * @throws IOException
     */
    protected void saveIndex(String indexName,List<? extends IndexSerializable> dataList) throws IOException {
        if (null == dataList || dataList.isEmpty()){
            return;
        }
        //使用 es的bulk操作进行索引保存
        BulkRequest request = new BulkRequest();
        for (IndexSerializable indexEntity : dataList){
            IndexRequest indexRequest = new IndexRequest(indexName)
                    .id(indexEntity.toElasticsearchId())
                    .source(indexEntity.toElasticsearchJson(logger,objectMapper),XContentType.JSON);
            request.add(indexRequest);
        }
        restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
    }

    /**
     * 删除Index
     * @param indexName 索引名称
     * @param ids 要删除的对应的ids
     * @throws IOException
     */
    protected void deleteIndex(String indexName,List<String> ids) throws IOException {
        BulkRequest request = new BulkRequest();
        for (String id:ids){
            request.add(new DeleteRequest(indexName,id));
        }
        restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
    }

    /**
     * ES 查询
     * 1. 根据查询上下文创建查询请求SearchRequest
     * 2. 根据对应的分页条件是否进行分页搜索 根据 分页参数 是否需要进行滚动查询
     * 3. 通过响应将数据加载封装成对应的Page对象
     * @param context 查询上下文
     * @return
     */
    public Page<T> search(IndexQueryContext context){
        List<SearchHit> content = new ArrayList<>();
        long total = search(content, context);
        List<T> tList = sequenceLoadData(content);
        Page page=new Page<T>(context.getPageNum(), context.getPageSize());
        // 将值放入page中
        page.setRecords(tList);
        page.setTotal(total);
        return page;
    }

    public List<SearchHit> searchHits(IndexQueryContext context){
        List<SearchHit> content = new ArrayList<>();
        search(content, context);
        return content;
    }

    protected long search(List<SearchHit> content, IndexQueryContext context){
        // 用于判断 是否 返回所有数据
        boolean flag = true;
        // 总数量
        long total = 0;
        // 分页大小
        int pageSize = context.getPageSize();
        // 分页页码
        int pageNo = context.getPageNum();
        //
        int from = pageNo<0 ? 0 : (pageNo - 1) * pageSize;
        // 待查询页面内第一条记录的下标
        int firstRowNum = from ;
        // 最后一行
        int lastRowNum = from + pageSize;
        // 创建查询请求
        SearchRequest searchRequest = new SearchRequest(context.getIndexName());
        // 构建SearchSourceBuilder
        SearchSourceBuilder searchSourceBuilder = buildSearchSourceBuilder(context);
        searchRequest.source(searchSourceBuilder);
        // 不分页 最多返回10000条
        if(pageNo==0 && pageSize==0){
            searchSourceBuilder.from(0).size(MAX_RESULT_WINDOW);
        }else{
            // 分页
            // 小于  MAX_RESULT_WINDOW  10000
            if(lastRowNum<MAX_RESULT_WINDOW){
                searchSourceBuilder.from(from).size(pageSize);
                flag = false;
            }
        }
        SearchResponse searchResponse;
        try {
            // 查询数据超过第10000
            if (lastRowNum >= MAX_RESULT_WINDOW) {
                // 滚动搜索
                total =  searchScroll(content,firstRowNum,lastRowNum,searchSourceBuilder,searchRequest);

            }else{
                searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
                SearchHits hits = searchResponse.getHits();
                total  = hits.getTotalHits().value;
                // 不分页情况下 总条数超过10000
                if(total>MAX_RESULT_WINDOW&&flag){
                    searchScroll(content,firstRowNum,(int)total,searchSourceBuilder,searchRequest);
                }else{
                    content = loadDataByResponse(content, searchResponse);
                }
            }
        } catch (IOException e) {
            logger.error("查询索引错误，错误如下：{}",e);
            throw new CustomException("查询索引出错",e);
        }
        return total;
    }

    /**
     * 创建 buildSearchSourceBuilder 包括精确统计 查询器 排序器
     * @param context
     * @return
     */
    private SearchSourceBuilder buildSearchSourceBuilder(IndexQueryContext context){
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 精确统计 不设置该属性 当总数超过10000时 total只返回10000
        searchSourceBuilder.trackTotalHits(context.exactCount());
        // 查询器
        searchSourceBuilder.query(context.getQuery());
        // 排序器
        List<SortBuilder> sorts = context.getSorts();
        // 排序
        if(Objects.nonNull(sorts)&&!sorts.isEmpty()){
            sorts.forEach(sortBuilder-> searchSourceBuilder.sort(sortBuilder));
        }
        return searchSourceBuilder;
    }

    /**
     * 滚动搜索
     * @param scrollId 滚动Id
     * @return
     * @throws IOException
     */
    protected SearchResponse searchScroll(String scrollId) throws IOException {
        SearchScrollRequest searchScrollRequest = new SearchScrollRequest(scrollId);
        searchScrollRequest.scroll(SCROLL_TIME);
        return restHighLevelClient.scroll(searchScrollRequest, RequestOptions.DEFAULT);
    }

    /**
     * scroll 查询 返回总条数
     * @param content
     * @param context
     * @param firstRowNum
     * @param lastRowNum
     * @param searchSourceBuilder
     * @param searchRequest
     * @return
     */
    private long searchScroll(List<T> content,IndexQueryContext context,int firstRowNum,int lastRowNum,SearchSourceBuilder searchSourceBuilder,SearchRequest searchRequest){
        long total = 0L;
        SearchResponse searchResponse;
        // 初始位置
        int startPosition;
        // 结束位置
        int endPosition;
        // 页面大小
        int pageSize = lastRowNum-firstRowNum;
        // 第一条数据所在滚动指针
        int firstScrollCursor = firstRowNum/MAX_RESULT_WINDOW;
        // 最后一条数据所在指针
        int lastScrollCursor = lastRowNum/MAX_RESULT_WINDOW;
        // 滚动次数
        int scrollCount = 0;
        // 滚动id
        String scrollId;
        try {
                searchSourceBuilder.size(MAX_RESULT_WINDOW);
                searchRequest.scroll(SCROLL_TIME);
                // scroll 查询 获取前10000 条数据和scrollId
                searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
                SearchHits hits = searchResponse.getHits();
                total  = hits.getTotalHits().value;
                // 1分钟 内的scrollId 第一次
                scrollId = searchResponse.getScrollId();
                // 部分数据在10000 以内 先获取一部分
                if(firstRowNum<MAX_RESULT_WINDOW){

                    startPosition = firstRowNum - scrollCount*MAX_RESULT_WINDOW>0?firstRowNum - scrollCount*MAX_RESULT_WINDOW:0;
                    endPosition = lastRowNum-scrollCount*MAX_RESULT_WINDOW>MAX_RESULT_WINDOW?MAX_RESULT_WINDOW:lastRowNum-scrollCount*MAX_RESULT_WINDOW;
                    content.addAll(loopLoadDataByPosition(context, startPosition, endPosition, hits));

                }
                if(StringUtils.isNotEmpty(scrollId)){
                    // 指针还没有到最后的指针
                    while (scrollCount<lastScrollCursor){
                        searchResponse = searchScroll(scrollId);
                        scrollId = searchResponse.getScrollId();
                        scrollCount++;
                        //
                        if(scrollCount>=firstScrollCursor){
                            // 从hits取对应数据 剩余容量
                            int capacity = pageSize-content.size();
                            if(capacity>0){
                                hits = searchResponse.getHits();
                                // 命中数量
                                int hitCount = hits.getHits().length;
                                startPosition = firstRowNum - scrollCount*MAX_RESULT_WINDOW>0?firstRowNum - scrollCount*MAX_RESULT_WINDOW:0;
                                endPosition = lastRowNum-scrollCount*MAX_RESULT_WINDOW>MAX_RESULT_WINDOW?MAX_RESULT_WINDOW:lastRowNum-scrollCount*MAX_RESULT_WINDOW;
                                // 超过命中次数  取命中次数
                                endPosition = endPosition>hitCount?hitCount:endPosition;
//                                if(scrollCount==firstScrollCursor)
//                                    startPosition--;
                                if(firstScrollCursor!=lastScrollCursor&&scrollCount==lastScrollCursor)
                                    endPosition=capacity>MAX_RESULT_WINDOW?MAX_RESULT_WINDOW:capacity-startPosition;
                                content.addAll(loopLoadDataByPosition(context, startPosition, endPosition, hits));
                            }
                        }

                    }
                }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return total;
    }
    /**
     * scroll 查询 返回总条数
     * @param searchHitList
     * @param firstRowNum
     * @param lastRowNum
     * @param searchSourceBuilder
     * @param searchRequest
     * @return
     */
    private long searchScroll(List<SearchHit> searchHitList,int firstRowNum,int lastRowNum,SearchSourceBuilder searchSourceBuilder,SearchRequest searchRequest){
        long total = 0L;
        SearchResponse searchResponse;
        // 初始位置
        int startPosition;
        // 结束位置
        int endPosition;
        // 页面大小
        int pageSize = lastRowNum-firstRowNum;
        // 第一条数据所在滚动指针
        int firstScrollCursor = firstRowNum/MAX_RESULT_WINDOW;
        // 最后一条数据所在指针
        int lastScrollCursor = lastRowNum/MAX_RESULT_WINDOW;
        // 滚动次数
        int scrollCount = 0;
        // 滚动id
        String scrollId;
        try {
                searchSourceBuilder.size(MAX_RESULT_WINDOW);
                searchRequest.scroll(SCROLL_TIME);
                // scroll 查询 获取前10000 条数据和scrollId
                searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
                SearchHits hits = searchResponse.getHits();
                total  = hits.getTotalHits().value;
                // 1分钟 内的scrollId 第一次
                scrollId = searchResponse.getScrollId();
                // 部分数据在10000 以内 先获取一部分
                if(firstRowNum<MAX_RESULT_WINDOW){
                    startPosition = firstRowNum - scrollCount*MAX_RESULT_WINDOW>0?firstRowNum - scrollCount*MAX_RESULT_WINDOW:0;
                    endPosition = lastRowNum-scrollCount*MAX_RESULT_WINDOW>MAX_RESULT_WINDOW?MAX_RESULT_WINDOW:lastRowNum-scrollCount*MAX_RESULT_WINDOW;
                    loopLoadDataByPosition(searchHitList, startPosition, endPosition, hits);
                }
                if(StringUtils.isNotEmpty(scrollId)){
                    // 指针还没有到最后的指针
                    while (scrollCount<lastScrollCursor){
                        searchResponse = searchScroll(scrollId);
                        scrollId = searchResponse.getScrollId();
                        scrollCount++;
                        //
                        if(scrollCount>=firstScrollCursor){
                            // 从hits取对应数据 剩余容量
                            int capacity = pageSize-searchHitList.size();
                            if(capacity>0){
                                hits = searchResponse.getHits();
                                // 命中数量
                                int hitCount = hits.getHits().length;
                                startPosition = firstRowNum - scrollCount*MAX_RESULT_WINDOW>0?firstRowNum - scrollCount*MAX_RESULT_WINDOW:0;
                                endPosition = lastRowNum-scrollCount*MAX_RESULT_WINDOW>MAX_RESULT_WINDOW?MAX_RESULT_WINDOW:lastRowNum-scrollCount*MAX_RESULT_WINDOW;
                                // 超过命中次数  取命中次数
                                endPosition = endPosition>hitCount?hitCount:endPosition;
                                if(scrollCount==firstScrollCursor)
                                    startPosition--;
                                if(firstScrollCursor!=lastScrollCursor&&scrollCount==lastScrollCursor)
                                    endPosition=capacity>MAX_RESULT_WINDOW?MAX_RESULT_WINDOW:capacity-startPosition;
                                loopLoadDataByPosition(searchHitList, startPosition, endPosition, hits);
                            }
                        }

                    }
                }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return total;
    }

    /**
     * 循环批量加载数据
     * @param context
     * @param startPosition
     * @param endPosition
     * @param hits
     */
    private List<T> loopLoadDataByPosition( IndexQueryContext context, int startPosition, int endPosition, SearchHits hits) {
        int length = hits.getHits().length;
        int destLength = endPosition-startPosition;
        destLength = destLength>length?length:destLength;
        SearchHit[] hitArr = new SearchHit[destLength];
        System.arraycopy(hits.getHits(),startPosition,hitArr,0,hitArr.length);
        return batchLoadData(context,hitArr);
    }
    /**
     * 循环批量加载hit数据
     * @param searchHitList
     * @param startPosition
     * @param endPosition
     * @param hits
     */
    private List<SearchHit> loopLoadDataByPosition(List<SearchHit> searchHitList, int startPosition, int endPosition, SearchHits hits) {
        int length = hits.getHits().length;
        int destLength = endPosition-startPosition;
        destLength = destLength>length?length:destLength;
        SearchHit[] hitArr = new SearchHit[destLength];
        System.arraycopy(hits.getHits(),startPosition,hitArr,0,hitArr.length);
        searchHitList.addAll(Arrays.asList(hitArr));
        return searchHitList;
    }

    /**
     * 通过响应加载数据
     * @param context
     * @param content
     * @param searchResponse
     * @return
     */
    private List<T> loadDataByResponse(IndexQueryContext context, List<T> content, SearchResponse searchResponse) {
        SearchHits hits = searchResponse.getHits();
        // 条数过多 批量加载 减少数据库连接
        SearchHit[] hitArr = hits.getHits();
        if(Objects.nonNull(hitArr)&&hitArr.length>=BATCH_LOAD_SIZE){
            content = batchLoadData(context,hitArr);
        }else{
            //逐条加载
            for (SearchHit hit : hitArr) {

                String id=hit.getId();
                if (StringUtils.isBlank(id)){
                    continue;
                }
                T data=loadData(context,hit);
                if (data==null){
                    continue;
                }
                content.add(data);
            }
        }
        return content;
    }
    /**
     * 通过响应加载hit数据
     * @param searchHitList
     * @param searchResponse
     * @return
     */
    private List<SearchHit> loadDataByResponse(List<SearchHit> searchHitList, SearchResponse searchResponse) {
        SearchHits hits = searchResponse.getHits();
        // 条数过多 批量加载 减少数据库连接
        SearchHit[] hitArr = hits.getHits();
        if(Objects.nonNull(hitArr)&&hitArr.length>=BATCH_LOAD_SIZE){
            searchHitList.addAll(Arrays.asList(hitArr));
        }else{
            //逐条加载
            for (SearchHit hit : hitArr) {

                String id=hit.getId();
                if (StringUtils.isBlank(id)){
                    continue;
                }
                searchHitList.add(hit);
            }
        }
        return searchHitList;
    }

    protected List<T> sequenceLoadData(List<SearchHit> content){
        return content.stream().map(x -> loadData(null, x)).filter(x -> x != null).collect(Collectors.toList());
    }

    /**
     * 逐条加载数据
     * @param context
     * @param hit
     * @return
     */
    protected abstract T loadData(IndexQueryContext context,SearchHit hit);

    /**
     * 批量加载数据
     * @param context
     * @param hitArr
     * @return
     */
    public abstract List<T> batchLoadData(IndexQueryContext context,SearchHit[] hitArr);

}
