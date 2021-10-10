package com.biemo.cloud.bbs.core.search.utils;

import com.biemo.cloud.bbs.core.annotation.ESMapping;
import com.biemo.cloud.bbs.core.constant.ESBoolLogicConstants;
import com.biemo.cloud.bbs.core.constant.QueryBuilderDefinitionConstants;
import com.biemo.cloud.bbs.core.search.impl.AbstractIndexQueryContext;
import com.biemo.cloud.core.util.BeanUtils;
import com.biemo.cloud.core.util.StringUtils;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 用于修改默认ESmapping的映射值，从而实现动态修改queryBuilder
 */
public class IndexQueryContextUtils {

    private static final Logger log = LoggerFactory.getLogger(IndexQueryContextUtils.class);

    private IndexQueryContextUtils() {
        throw new AssertionError("Utility class must not be instantiated");
    }

    // 初始属性map
    private static ConcurrentHashMap<String,Object> initMap = new ConcurrentHashMap<>();
    /**
     * 修改boolLogic值
     * @param indexQueryContext
     * @param boolLogic
     * @param contextFiledNames
     */
    public static void modifyBoolLogic(AbstractIndexQueryContext indexQueryContext, String boolLogic, String... contextFiledNames){
        modify(indexQueryContext, QueryBuilderDefinitionConstants.BOOL_LOGIC,boolLogic,contextFiledNames);
    }

    /**
     * 修改字段间 查询连接条件为must==and
     * @param indexQueryContext
     * @param contextFiledNames
     */
    public static void modifyBoolLogicMust(AbstractIndexQueryContext indexQueryContext,String... contextFiledNames){
        modifyBoolLogic(indexQueryContext, ESBoolLogicConstants.MUST,contextFiledNames);
    }

    /**
     * 修改字段间 查询连接条件为mustNot==not
     * @param indexQueryContext
     * @param contextFiledNames
     */
    public static void modifyBoolLogicMustNot(AbstractIndexQueryContext indexQueryContext,String... contextFiledNames){
        modifyBoolLogic(indexQueryContext,ESBoolLogicConstants.MUST_NOT,contextFiledNames);
    }

    /**
     * 修改字段间 查询连接条件为should==or
     * @param indexQueryContext
     * @param contextFiledNames
     */
    public static void modifyBoolLogicShould(AbstractIndexQueryContext indexQueryContext,String... contextFiledNames){
        modifyBoolLogic(indexQueryContext,ESBoolLogicConstants.SHOULD,contextFiledNames);
    }

    /**
     * 修改字段查询权重
     * @param indexQueryContext
     * @param boost 权重值
     * @param contextFiledNames 指定字段名
     */
    public static void modifyBoost(AbstractIndexQueryContext indexQueryContext,float boost ,String... contextFiledNames){
        modify(indexQueryContext,QueryBuilderDefinitionConstants.BOOST,boost,contextFiledNames);
    }

    /**
     * 修改查询器的类型
     * @param indexQueryContext
     * @param type  查询器类型 名称
     * @param contextFiledNames
     */
    public static void modifyType(AbstractIndexQueryContext indexQueryContext,String type ,String... contextFiledNames){
        modify(indexQueryContext,QueryBuilderDefinitionConstants.TYPE,type,contextFiledNames);
    }

    /**
     * 修改查询器为 模糊查询
     * @param indexQueryContext
     * @param contextFiledNames
     */
    public static void modifyTypeWildcard(AbstractIndexQueryContext indexQueryContext ,String... contextFiledNames){
        modifyType(indexQueryContext, WildcardQueryBuilder.NAME,contextFiledNames);
    }

    /**
     * 修改 指定字段的排序
     * @param indexQueryContext
     * @param sortOrder
     * @param cover  是否覆盖原有排序
     * @param contextFiledNames
     */
    public static void modifyOrder(AbstractIndexQueryContext indexQueryContext, SortOrder sortOrder ,boolean cover, String... contextFiledNames){
        // 是否覆盖  原有排序
        if(cover)
            modify(indexQueryContext,QueryBuilderDefinitionConstants.SORT,false);

        modify(indexQueryContext,QueryBuilderDefinitionConstants.SORT,true,contextFiledNames);
        modify(indexQueryContext,QueryBuilderDefinitionConstants.ORDER,sortOrder,contextFiledNames);
    }

    /**
     * 修改排序，默认覆盖原来的排序
     * @param indexQueryContext
     * @param sortOrder
     * @param contextFiledNames
     */
    public static void modifyOrder(AbstractIndexQueryContext indexQueryContext, SortOrder sortOrder , String... contextFiledNames){
        modifyOrder(indexQueryContext,sortOrder,true,contextFiledNames);
    }


    /**
     * 修改esmapping的对应值
     * @param indexQueryContext
     * @param key 修改的esmapping的key
     * @param value 修改后的值
     * @param contextFiledNames 需要修改的字段名称
     */
    public static void modify(AbstractIndexQueryContext indexQueryContext,String key,Object value,String... contextFiledNames){
        Class clazz = indexQueryContext.getClass();
        try{
            Field[] fields = clazz.getDeclaredFields();
            if (contextFiledNames.length > 0) {
                for(int i=0;i<contextFiledNames.length;i++){
                    if( StringUtils.isNotEmpty(contextFiledNames[i])){
                        dynamicModify(clazz.getName(),clazz.getDeclaredField(contextFiledNames[i]),key,value);
                    }
                }
            }else{
                Arrays.stream(fields).forEach(field -> dynamicModify(clazz.getName(),field,key,value));
            }

        }catch (Exception e){
            log.error(key+"属性无法修改");
            throw new RuntimeException(key+"属性无法修改");
        }
    }


    private static void dynamicModify(String clazzName,Field field, String name, Object value){
        ESMapping esMapping = field.getAnnotation(ESMapping.class);
        if(Objects.isNull(esMapping)) return;
        //获取 ESMapping 这个代理实例所持有的 InvocationHandler
        InvocationHandler invocationHandler = Proxy.getInvocationHandler(esMapping);
        // 获取 AnnotationInvocationHandler 的 memberValues 字段
        Field hField = null;
        try {
            hField = invocationHandler.getClass().getDeclaredField("memberValues");
            // 因为这个字段是 private final 修饰，所以要打开权限
            hField.setAccessible(true);
            // 获取 memberValues
            Map memberValues = (Map) hField.get(invocationHandler);
            initMap.putIfAbsent(clazzName+":"+field.getName()+":"+name,memberValues.get(name));
            memberValues.put(name, value);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static ESMapping getEsMapping(AbstractIndexQueryContext indexQueryContext,String filedName){
        Class clazz = indexQueryContext.getClass();
        Field field = null;
        try {
            field = clazz.getDeclaredField(filedName);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return getFieldESMappingMap(indexQueryContext).get(field);
    }

    public static Map<Field,ESMapping> getFieldESMappingMap(Object object){
        Class clazz = object.getClass();
        List<Field> esMappingFieldList = Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> Objects.nonNull(field.getAnnotation(ESMapping.class))).collect(Collectors.toList());
        // 是否有重新设置
        Map<Field,ESMapping> fieldESMappingMap = new HashMap<>();
        Map<String,Object> map = BeanUtils.bean2Map(object);
        try{
            for(Field field:esMappingFieldList){
                if(map.containsKey(field.getName())){
                    ESMapping esMapping = field.getAnnotation(ESMapping.class);
                    fieldESMappingMap.put(field,esMapping);
                }
            }
        }catch (Exception e){
            log.error("获取QueryBuilder失败,错误如下{}",e);
            throw new RuntimeException("获取QueryBuilder失败",e);
        }

        return fieldESMappingMap;
    }

    /**
     * 根据 ESmapping 注解 获取排序字段
     * @param
     * @return
     */
    public static Map<Field,ESMapping> getOrderFiledESMappingMap(Object object){
        Class clazz = object.getClass();
        List<Field> esMappingFieldList = Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> Objects.nonNull(field.getAnnotation(ESMapping.class))).collect(Collectors.toList());
        Map<Field,ESMapping> orderFieldESMappingMap = new HashMap<>();
        for(Field field:esMappingFieldList){
            ESMapping esMapping = field.getAnnotation(ESMapping.class);
            if(esMapping.sort())
                orderFieldESMappingMap.put(field,esMapping);
        }
        return orderFieldESMappingMap;
    }

    /**
     * 还原注解
     * @param indexQueryContext
     */
    public static void restoreEsMapping(AbstractIndexQueryContext indexQueryContext) {
        if(initMap.isEmpty()) return;
        Class clazz = indexQueryContext.getClass();
        Field[] fields = clazz.getDeclaredFields();

        Arrays.stream(fields).forEach(field -> {

            ESMapping esMapping = field.getAnnotation(ESMapping.class);
            if(Objects.nonNull(esMapping)) {
                InvocationHandler invocationHandler = Proxy.getInvocationHandler(esMapping);
                Field hField = null;
                try {
                    hField = invocationHandler.getClass().getDeclaredField("memberValues");
                    // 因为这个字段是 private final 修饰，所以要打开权限
                    hField.setAccessible(true);
                    // 获取 memberValues
                    Map<String, Object> memberValues = (Map) hField.get(invocationHandler);
                    for (String key : memberValues.keySet()) {
                        if (initMap.containsKey(clazz.getName() + ":" + field.getName() + ":" + key)) {
                            memberValues.put(key, initMap.get(clazz.getName() + ":" + field.getName() + ":" + key));
                        }
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
