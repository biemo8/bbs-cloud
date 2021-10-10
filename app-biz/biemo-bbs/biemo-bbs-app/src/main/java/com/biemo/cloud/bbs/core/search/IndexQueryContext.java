package com.biemo.cloud.bbs.core.search;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.sort.SortBuilder;

import java.util.List;

public interface IndexQueryContext{

    String getIndexName();

    QueryBuilder getQuery();

    boolean isValid();

    int getPageNum();

    int getPageSize();

    List<SortBuilder> getSorts();

    boolean exactCount();
}
