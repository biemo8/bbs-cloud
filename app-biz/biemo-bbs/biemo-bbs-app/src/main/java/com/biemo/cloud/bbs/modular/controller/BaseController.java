package com.biemo.cloud.bbs.modular.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biemo.cloud.core.util.DateUtils;
import com.biemo.cloud.core.constant.HttpStatus;
import com.biemo.cloud.core.page.TableDataInfo;
import com.biemo.cloud.core.util.StringUtils;
import com.biemo.cloud.kernel.model.response.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.util.Date;

/**
 * web层通用数据处理
 *
 */
@Slf4j
public class BaseController
{

    /**
     * 将前台传递过来的日期格式的字符串，自动转化为Date类型
     */
    @InitBinder
    public void initBinder(WebDataBinder binder)
    {
        // Date 类型转换
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport()
        {
            @Override
            public void setAsText(String text)
            {
                setValue(DateUtils.parseDate(text));
            }
        });
    }

    /**
     * 响应请求分页数据
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected TableDataInfo getDataTable(Page<?> page)
    {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(HttpStatus.SUCCESS);
        rspData.setMsg("查询成功");
        rspData.setRows(page.getRecords());
        rspData.setTotal(page.getTotal());
        return rspData;
    }

    /**
     * 响应返回结果
     *
     * @param rows 影响行数
     * @return 操作结果
     */
    protected ResponseData toAjax(int rows)
    {
        return rows > 0 ? ResponseData.success() : ResponseData.error("操作失败！");
    }

    /**
     * 响应返回结果
     *
     * @param result 结果
     * @return 操作结果
     */
    protected ResponseData toAjax(boolean result)
    {
        return result ? success() : error();
    }

    /**
     * 返回成功
     */
    public ResponseData success()
    {
        return ResponseData.success();
    }

    /**
     * 返回失败消息
     */
    public ResponseData error()
    {
        return ResponseData.error("操作失败！");
    }

    /**
     * 返回成功消息
     */
    public ResponseData success(String message)
    {
        return ResponseData.success(message);
    }

    /**
     * 返回失败消息
     */
    public ResponseData error(String message)
    {
        return ResponseData.error(message);
    }

    /**
     * 页面跳转
     */
    public String redirect(String url)
    {
        return StringUtils.format("redirect:{}", url);
    }
}
