package com.biemo.cloud.biz.dict.api.entity;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 基础字典
 * </p>
 *
 *
 * @since 2018-07-25
 */
@TableName("sys_dict")
public class Dict implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 字典id
     */
    @TableId("DICT_ID")
    private Long dictId;
    /**
     * 字典类型id
     */
    @TableField("DICT_TYPE_CODE")
    private String dictTypeCode;
    /**
     * 字典编码
     */
    @TableField("DICT_CODE")
    private String dictCode;
    /**
     * 字典名称
     */
    @TableField("DICT_NAME")
    private String dictName;
    /**
     * 简称
     */
    @TableField("DICT_SHORT_NAME")
    private String dictShortName;
    /**
     * 字典简拼
     */
    @TableField("DICT_SHORT_CODE")
    private String dictShortCode;
    /**
     * 上级代码id
     */
    @TableField("PARENT_ID")
    private Long parentId;
    /**
     * 状态(1:启用,2:禁用)
     */
    @TableField("STATUS")
    private Integer status;
    /**
     * 创建时间
     */
    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 更新时间
     */
    @TableField(value = "UPDATE_TIME", fill = FieldFill.UPDATE)
    private Date updateTime;


    public Long getDictId() {
        return dictId;
    }

    public void setDictId(Long dictId) {
        this.dictId = dictId;
    }

    public String getDictTypeCode() {
        return dictTypeCode;
    }

    public void setDictTypeCode(String dictTypeCode) {
        this.dictTypeCode = dictTypeCode;
    }

    public String getDictCode() {
        return dictCode;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public String getDictShortName() {
        return dictShortName;
    }

    public void setDictShortName(String dictShortName) {
        this.dictShortName = dictShortName;
    }

    public String getDictShortCode() {
        return dictShortCode;
    }

    public void setDictShortCode(String dictShortCode) {
        this.dictShortCode = dictShortCode;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Dict{" +
                ", dictId=" + dictId +
                ", dictTypeCode=" + dictTypeCode +
                ", dictCode=" + dictCode +
                ", dictName=" + dictName +
                ", dictShortName=" + dictShortName +
                ", dictShortCode=" + dictShortCode +
                ", parentId=" + parentId +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                "}";
    }
}
