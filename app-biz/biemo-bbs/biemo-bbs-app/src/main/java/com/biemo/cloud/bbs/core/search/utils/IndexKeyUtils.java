package com.biemo.cloud.bbs.core.search.utils;

import com.biemo.cloud.bbs.core.constant.CatalogConstants;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ES 跨度查询字符串处理
 * @date 2021-03-30 10:44
 */
public class IndexKeyUtils {

    public static String clean(String content) {
        if (StringUtils.isBlank(content)) {
            return CatalogConstants.EMPTY;
        }
        for (String key : CatalogConstants.KW_STANDARD_RULE.keySet()) {
            String value=CatalogConstants.KW_STANDARD_RULE.get(key);
            content=content.replaceAll(key,value);
        }
        return content;
    }

    public static String handleCharacterIndex(String exp) {
        if (StringUtils.isBlank(exp)) {
            return CatalogConstants.EMPTY;
        }
        for (String key : CatalogConstants.KW_INDEX_WORD_RULE.keySet()) {
            String value=CatalogConstants.KW_INDEX_WORD_RULE.get(key);
            exp=String.valueOf(exp.replaceAll(key,value));

            //System.out.println(key+"___"+value+"___"+content);
        }
        if (exp.startsWith(CatalogConstants.BRACKET_LEFT)==false){
            exp=CatalogConstants.DOUBLE_QUOTATION_MARKS+exp;
        }
        if (exp.endsWith(CatalogConstants.BRACKET_RIGHT)==false){
            exp=exp+CatalogConstants.DOUBLE_QUOTATION_MARKS;
        }
        return exp;
    }


    public static String toLunceneExp(String exp) {
        if (StringUtils.isBlank(exp)){
            return CatalogConstants.EMPTY;
        }
        return exp.replaceAll(CatalogConstants.SYMBOL_REGEX_OR,CatalogConstants.BLANK + CatalogConstants.SYMBOL_OR + CatalogConstants.BLANK)
                .replaceAll(CatalogConstants.SYMBOL_REGEX_AND,CatalogConstants.BLANK + CatalogConstants.SYMBOL_AND + CatalogConstants.BLANK);
    }

    public static String strToESExp(String str){
        return toLunceneExp(handleCharacterIndex(clean(str)));
    }

    /** 关键字抽取 */
    public static Set<String> expAnalysis(String str){
        String regEx="[\n`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。， 、？]";
        Matcher matcher = Pattern.compile(regEx).matcher(clean(str));
        String trim = matcher.replaceAll(" ").trim();
        String[] split = trim.split(" ");

        Set<String> resultStrSet = new HashSet<>();
        for (String s : split) {
            if(!s.isEmpty())
                resultStrSet.add(s);
        }

        return resultStrSet;
    }
}
