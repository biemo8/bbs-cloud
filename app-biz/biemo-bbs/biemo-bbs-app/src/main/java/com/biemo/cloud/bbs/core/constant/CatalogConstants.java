package com.biemo.cloud.bbs.core.constant;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 数据专题配置表文档类别和匹配类别
 * @date 2021-03-25 15:44
 */
public class CatalogConstants {
    /** 新闻 */
    public static  final int JOURNALISM_TYPE = 1;
    /** 公众号 */
    public static  final int OFFICIAL_ACCOUNTS_TYPE = 2;
    /** 态势 */
    public static  final int SITUATION_TYPE = 3;
    /** 仅标题 */
    public static  final int TITLE_MATCH = 1;
    /** 仅内容 */
    public static  final int CONTENT_MATCH = 2;
    /** 标题或内容 */
    public static  final int TITLE_OR_CONTENT_MATCH = 3;
    /** 标题且内容 */
    public static  final int TITLE_AND_CONTENT_MATCH = 4;



    public static final String VALUE_INTERVAL_DAY = "day";
    public static final String VALUE_INTERVAL_WEEK = "week";
    public static final String VALUE_INTERVAL_MONTH = "month";
    public static final String VALUE_INTERVAL_YEAR = "year";

    public static final String VALUE_ANALYSER_TERMS = "terms";
    public static final String VALUE_ANALYSER_DATE = "date";

    public static final String VALUE_ADMIN_ID = "-1";

    public static final String TAG_KW_START = "<font color=red>";
    public static final String TAG_KW_END = "</font>";

    //***************************************************************
    //系统状态定义
    //***************************************************************
    public static final int STATUS_OK = 1;
    public static final int STATUS_ERR = 0;
    public static final int STATUS_ERR_ILLEGALARG = -2;
    public static final int STATUS_ERR_SYS = -2;

    public static final String DESC="desc";
    public static final String ASC="asc";

    public static final String[] DF_STRING_DATETIME = {"yyyyMMddHHmmss","yyyyMMdd"};

    public static final String DF_DATE_YYYY = "yyyy";
    public static final String DF_DATE_YYYYMM = "yyyyMM";
    public static final String DF_DATE_YYYYMMDD = "yyyyMMdd";
    public static final String DF_DATE_YYYYWW = "yyyyww";

    public static final String VALUE_DATE_STARTTIME = "000000";
    public static final String VALUE_DATE_ENDTIME = "235959";


    public static final String FORMAT = "%s(%s)";
    public static final String EMPTY = "";
    public static final String BLANK = " ";
    public static final String NEWLINE = "\n";
    public static final String COLON=":";
    public static final String COMMA=",";
    public static final String HASHTAG="#";
    public static final String UNDERLINE="_";

    public static final String FIELD_LASTMODIFIEDTIME="lastModifiedTime";


    public static final String INDEX_NEWS = "news";
    //public static final String INDEX_BASE = "base";

    public static final String TABLE_HOTWORDS = "Hotwords";


    public static final String FILED_PAGENO = "pageNo";
    public static final String FILED_PAGESIZE = "pageSize";
    public static final String FILED_KEYWORD = "keyword";
    public static final String FILED_KEYWORDTYPE = "keywordType";

    public static final String FILED_MONGODB_ID = "_id";
    public static final String FILED_SUBJECTID = "subjectId";
    public static final String FILED_KEYWORDID = "keywordId";
    public static final String FILED_AUTHOR = "author";
    public static final String FILED_CONTENT = "content";
    public static final String FILED_SITE_ID = "siteId";
    public static final String FILED_SITE_NAME = "siteName";
    public static final String FILED_SITETYPE_ID = "siteTypeId";
    public static final String FILED_SITETYPE_NAME = "siteTypeName";
    public static final String FILED_CHANNEL_ID = "channelId";
    public static final String FILED_CHANNEL_NAME = "channelName";
    public static final String FILED_CHANNEL_TYPEID = "channelTypeId";
    public static final String FILED_CHANNEL_TYPENAME = "channelTypeName";

    public static final String FILED_TITLESIMHASH = "titleSimHash";


    public static final String FILED_PUBLISHTIME = "publishTime";
    public static final String FILED_CREATETIME = "createTime";
    public static final String FILED_USERID = "userId";

    public static final String FILED_KEY = "key";
    public static final String FILED_VALUE = "value";

    public static final String FILED_AREA = "area";
    public static final String FILED_URL = "url";
    public static final String FILED_TOP = "top";
    public static final String FILED_PADDING = "padding";
    public static final String FILED_HASCONTENT = "hasContent";
    public static final String FILED_STARTTIME = "startTime";
    public static final String FILED_ENDTIME = "endTime";
    public static final String FILED_LIMIT = "limit";
    public static final String FILED_ORDERBY = "orderBy";
    public static final String FILED_SORT = "sort";
    public static final String FILED_GROUPFIELDNAME = "groupFieldName";
    public static final String FILED_GROUPDATEINTERVAL = "groupDateInterval";

    public static final String INDEX_FILED_TITLE = "titleAnalyzer";
    public static final String INDEX_FILED_CONTENT = "contentAnalyzer";


    //***************************************************************
    //索引表达式符号定义
    //***************************************************************
    public static final String SYMBOL_COLON = "\\\\:";
    public static final String SYMBOL_REGEX_OR = "\\|";
    public static final String SYMBOL_REGEX_AND = "\\+";
    public static final String SYMBOL_REGEX_START_BRACKET = "\\(";
    public static final String SYMBOL_REGEX_END_BRACKET = "\\)";
    public static final String SYMBOL_AND = "AND";
    public static final String SYMBOL_OR = "OR";



    public static final String DOUBLE_QUOTATION_MARKS="\"";
    public static final String SINGLE_QUOTATION_MARKS="'";
    public static final String BRACKET_LEFT="(";
    public static final String BRACKET_RIGHT=")";

    public static final String REGEX_BRACKET="(\\[[^\\]]*\\])";

    public static final String CHARSET_UTF8="utf-8";

    //***************************************************************
    //关键字清洗规则定义
    //***************************************************************
    public static final Map<String,String> KW_STANDARD_RULE=new LinkedHashMap<String,String>();

    public static final Map<String,String> KW_CLEAN_RULE=new LinkedHashMap<String,String>();

    public static final Map<String,String> KW_INDEX_WORD_RULE=new LinkedHashMap<String,String>();

    static{
        KW_STANDARD_RULE.put("\\（", SYMBOL_REGEX_START_BRACKET);
        KW_STANDARD_RULE.put("\\）", SYMBOL_REGEX_END_BRACKET);
        KW_STANDARD_RULE.put("\\｜", SYMBOL_REGEX_OR);
        KW_STANDARD_RULE.put("\\＋", SYMBOL_REGEX_AND);

        KW_STANDARD_RULE.put("\\s*\\(\\s*", SYMBOL_REGEX_START_BRACKET);
        KW_STANDARD_RULE.put("\\s*\\)\\s*", SYMBOL_REGEX_END_BRACKET);
        KW_STANDARD_RULE.put("\\s*\\|\\s*", SYMBOL_REGEX_OR);
        KW_STANDARD_RULE.put("\\s*\\+\\s*", SYMBOL_REGEX_AND);

        KW_STANDARD_RULE.put("\\s*\\(\\s*", SYMBOL_REGEX_START_BRACKET);
        KW_STANDARD_RULE.put("\\s*\\)\\s*", SYMBOL_REGEX_END_BRACKET);
        KW_STANDARD_RULE.put("\\s*\\|\\s*", SYMBOL_REGEX_OR);
        KW_STANDARD_RULE.put("\\s*\\+\\s*", SYMBOL_REGEX_AND);

        KW_STANDARD_RULE.put("\\s*\\(\\s*", SYMBOL_REGEX_START_BRACKET);
        KW_STANDARD_RULE.put("\\s*\\)\\s*", SYMBOL_REGEX_END_BRACKET);
        KW_STANDARD_RULE.put("\\s*\\|\\s*", SYMBOL_REGEX_OR);
        KW_STANDARD_RULE.put("\\s*\\+\\s*", SYMBOL_REGEX_AND);


        KW_CLEAN_RULE.put(SYMBOL_REGEX_START_BRACKET, EMPTY);
        KW_CLEAN_RULE.put(SYMBOL_REGEX_END_BRACKET, EMPTY);
        KW_CLEAN_RULE.put(SYMBOL_AND, COMMA);
        KW_CLEAN_RULE.put(SYMBOL_OR, COMMA);
        KW_CLEAN_RULE.put(BLANK, EMPTY);



        KW_INDEX_WORD_RULE.put("\\|", "'|'");
        KW_INDEX_WORD_RULE.put("\\+", "'+'");

        KW_INDEX_WORD_RULE.put("\\(", "('");
        KW_INDEX_WORD_RULE.put("\\)", "')");

        KW_INDEX_WORD_RULE.put("\\+'\\(", "+(");
        KW_INDEX_WORD_RULE.put("\\)'\\+", ")+");

        KW_INDEX_WORD_RULE.put("'\\(", "(");
        KW_INDEX_WORD_RULE.put("\\)'", ")");

        KW_INDEX_WORD_RULE.put("'", "\"");
    }

    public static final Map<String,String> CONTENT_STANDARD_RULE=new LinkedHashMap<String,String>();

    static{
        CONTENT_STANDARD_RULE.put(" ", ",");
        CONTENT_STANDARD_RULE.put("，", ",");
        CONTENT_STANDARD_RULE.put("：", ":");
        CONTENT_STANDARD_RULE.put("。", ",");
        CONTENT_STANDARD_RULE.put("“", "\"");
        CONTENT_STANDARD_RULE.put("”", "\"");
        CONTENT_STANDARD_RULE.put("！", "!");
        CONTENT_STANDARD_RULE.put("？", "?");
        CONTENT_STANDARD_RULE.put("～", "-");

        //CONTENT_STANDARD_RULE.put("...", EMPTY);

        CONTENT_STANDARD_RULE.put("《", "[");
        CONTENT_STANDARD_RULE.put("》", "]");
        CONTENT_STANDARD_RULE.put("<", "[");
        CONTENT_STANDARD_RULE.put(">", "]");
        CONTENT_STANDARD_RULE.put("｛", "[");
        CONTENT_STANDARD_RULE.put("｝", "]");
        //CONTENT_STANDARD_RULE.put("<", EMPTY);
        //CONTENT_STANDARD_RULE.put(">", EMPTY);
        CONTENT_STANDARD_RULE.put("【", "[");
        CONTENT_STANDARD_RULE.put("】", "]");
        CONTENT_STANDARD_RULE.put("（", "[");
        CONTENT_STANDARD_RULE.put("）", "]");
        CONTENT_STANDARD_RULE.put(".SZ", "");
        CONTENT_STANDARD_RULE.put(".SH", "");

        CONTENT_STANDARD_RULE.put("[^0-9a-zA-Z\u4e00-\u9fa5\\[\\],:%\"\"!?·-]+", "");
    }

}
