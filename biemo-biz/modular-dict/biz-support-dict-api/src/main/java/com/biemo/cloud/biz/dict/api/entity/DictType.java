package com.biemo.cloud.biz.dict.api.entity;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 字典类型表
 * </p>
 *
 *
 * @since 2018-07-25
 */
@TableName("sys_dict_type")
public class DictType implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 字典类型id
     */
    @TableId("DICT_TYPE_ID")
    private Long dictTypeId;
    /**
     * 类型1：业务类型2：系统类型
     */
    @TableField("DICT_TYPE_CLASS")
    private Integer dictTypeClass;
    /**
     * 字典类型编码
     */
    @TableField("DICT_TYPE_CODE")
    private String dictTypeCode;
    /**
     * 字典类型名称
     */
    @TableField("DICT_TYPE_NAME")
    private String dictTypeName;
    /**
     * 字典描述
     */
    @TableField("DICT_TYPE_DESC")
    private String dictTypeDesc;
    /**
     * 状态1：启用2：禁用
     */
    @TableField("STATUS")
    private Integer status;
    /**
     * 应用编码
     */
    @TableField("APP_CODE")
    private String appCode;
    /**
     * 添加时间
     */
    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 修改时间
     */
    @TableField(value = "UPDATE_TIME", fill = FieldFill.UPDATE)
    private Date updateTime;


    public Long getDictTypeId() {
        return dictTypeId;
    }

    public void setDictTypeId(Long dictTypeId) {
        this.dictTypeId = dictTypeId;
    }

    public Integer getDictTypeClass() {
        return dictTypeClass;
    }

    public void setDictTypeClass(Integer dictTypeClass) {
        this.dictTypeClass = dictTypeClass;
    }

    public String getDictTypeCode() {
        return dictTypeCode;
    }

    public void setDictTypeCode(String dictTypeCode) {
        this.dictTypeCode = dictTypeCode;
    }

    public String getDictTypeName() {
        return dictTypeName;
    }

    public void setDictTypeName(String dictTypeName) {
        this.dictTypeName = dictTypeName;
    }

    public String getDictTypeDesc() {
        return dictTypeDesc;
    }

    public void setDictTypeDesc(String dictTypeDesc) {
        this.dictTypeDesc = dictTypeDesc;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
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
        return "DictType{" +
                ", dictTypeId=" + dictTypeId +
                ", dictTypeClass=" + dictTypeClass +
                ", dictTypeCode=" + dictTypeCode +
                ", dictTypeName=" + dictTypeName +
                ", dictTypeDesc=" + dictTypeDesc +
                ", status=" + status +
                ", appCode=" + appCode +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                "}";
    }
}
