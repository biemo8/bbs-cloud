package com.biemo.cloud.bbs.core.constant;


import com.biemo.cloud.bbs.config.CommonDataConfig;
import com.biemo.cloud.core.util.SpringContextHolder;

/**
 * @Program: makesoft
 * @ClassName: IndexConstants
 * @Description: es索引
 * @CreateDate: 2021-03-18 11:42
 **/
public class IndexConstants {
    private static CommonDataConfig commonDataConfig;

    static {
        commonDataConfig = SpringContextHolder.getBean(CommonDataConfig.class);
    }

    /** ES本体实例索引 */
    public static String TOPIC_INDEX = commonDataConfig.getTopicIndexName();
    public static String ARTICLE_INDEX = commonDataConfig.getArticleIndexName();


}
