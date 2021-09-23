package com.biemo.cloud.core.page;

import java.util.HashSet;
import java.util.Set;

import static com.biemo.cloud.core.page.PageFactory.*;

/**
 * 分页参数字段名称集合
 * <p>
 * 说明：为了兼容系统内分页参数不统一的问题
 *
 *
 * @date 2019-09-29-10:17
 */
public class PageFieldNamesContainer {

    private static PageFieldNamesContainer pageFieldNamesContainer = new PageFieldNamesContainer();

    /**
     * 分页大小字段名称集合
     */
    private Set<String> pageSizeFieldNames = new HashSet<>();

    /**
     * 分页位置字段名称集合
     */
    private Set<String> pageNoFieldNames = new HashSet<>();

    /**
     * 升序或降序
     */
    private Set<String> sortFieldNames = new HashSet<>();

    /**
     * 根据那个字段排序的param名称
     */
    private Set<String> orderByFieldNames = new HashSet<>();

    private PageFieldNamesContainer() {
        //默认的字段名称
        pageSizeFieldNames.add(PAGE_SIZE_PARAM_NAME);
        pageNoFieldNames.add(PAGE_NO_PARAM_NAME);
        sortFieldNames.add(SORT_PARAM_NAME);
        orderByFieldNames.add(ORDER_BY_PARAM_NAME);
    }

    /**
     * 获取实例
     *
     *
     * @Date 2019-09-29 16:03
     */
    public static PageFieldNamesContainer getInstance() {
        return pageFieldNamesContainer;
    }

    /**
     * 初始化分页大小字段集合
     *
     *
     * @Date 2019-09-29 10:20
     */
    public void initPageSizeFieldNames(Set<String> pageSizeFieldNames) {
        this.pageSizeFieldNames.addAll(pageSizeFieldNames);
    }

    /**
     * 初始化分页位置字段集合
     *
     *
     * @Date 2019-09-29 10:20
     */
    public void initPageNoFieldNames(Set<String> pageNoFieldNames) {
        this.pageNoFieldNames.addAll(pageNoFieldNames);
    }

    /**
     * 升序或降序
     *
     *
     * @Date 2019-09-29 10:20
     */
    public void initSortFieldNames(Set<String> sortFieldNames) {
        this.sortFieldNames.addAll(sortFieldNames);
    }

    /**
     * 根据那个字段排序的param名称字段集合
     *
     *
     * @Date 2019-09-29 10:20
     */
    public void initOrderByFieldNames(Set<String> orderByFieldNames) {
        this.orderByFieldNames.addAll(orderByFieldNames);
    }

    /**
     * 获取分页大小字段名称集合
     *
     *
     * @Date 2019-09-29 10:24
     */
    public Set<String> getPageSizeFieldNames() {
        return pageFieldNamesContainer.pageSizeFieldNames;
    }

    /**
     * 获取分页位置字段名称集合
     *
     *
     * @Date 2019-09-29 10:24
     */
    public Set<String> getPageNoFieldNames() {
        return pageFieldNamesContainer.pageNoFieldNames;
    }

    /**
     * 升序或降序
     *
     *
     * @Date 2019-09-29 10:24
     */
    public Set<String> getSortFieldNames() {
        return pageFieldNamesContainer.sortFieldNames;
    }

    /**
     * 根据那个字段排序的param名称字段集合
     *
     *
     * @Date 2019-09-29 10:24
     */
    public Set<String> getOrderByFieldNames() {
        return pageFieldNamesContainer.orderByFieldNames;
    }

}
