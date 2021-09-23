package com.biemo.cloud.auth.modular.ssomgr.util;

import com.biemo.cloud.core.page.PageFactory;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * 手动分页工具类
 *
 *
 * @Date 2019/12/4 21:16
 */
public class PageUtil {

    @SuppressWarnings("unchecked")
    public static <T> Page<T> createPageList(List<T> list) {
        Page page = PageFactory.defaultPage();

        page.setTotal(list.size());
        int size = (int) page.getSize();

        //计算总页数
        double totalSize = list.size();
        int totalPage = (int) Math.ceil(totalSize / size);

        //获取当前页
        int current = (int) page.getCurrent();

        if (list.size() > size) {
            //最后一页可能不足十条数据
            if (current == totalPage) {
                page.setRecords(list.subList((current - 1) * size, list.size()));
            } else {
                page.setRecords(list.subList((current - 1) * size, current * size));
            }
        } else {
            page.setRecords(list.subList(0, list.size()));
        }

        return page;
    }
}
