package com.biemo.cloud.bbs.core.annotation;

import com.biemo.cloud.bbs.core.constant.ESBoolLogicConstants;
import com.biemo.cloud.bbs.core.constant.ESCompareWayConstants;
import com.biemo.cloud.bbs.core.constant.ESHierarchyConstants;
import com.biemo.cloud.core.constant.Constants;
import org.elasticsearch.index.query.AbstractQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.sort.SortOrder;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ESMapping {

    // queryBuilder 类型 默认精确匹配
    String type() default TermQueryBuilder.NAME;

    // 时间 格式
    String format() default Constants.EMPTY;

    // 当type = range 时 需要指定比较方式
    String compareWay() default ESCompareWayConstants.GTE;

    // 查询字段
    String[] queryField() default {};

    // bool 连接的逻辑  must  should mustNot
    String boolLogic() default ESBoolLogicConstants.MUST;

    // 是否开启排序
    boolean sort() default false;

    // path 用于嵌套查询
    String path() default Constants.EMPTY;

    // 查询 层级
    int hierarchy() default ESHierarchyConstants.FIRST;

    // 排序方式
    SortOrder order() default SortOrder.DESC;

    // 查询权重
    float boost() default AbstractQueryBuilder.DEFAULT_BOOST;

}
