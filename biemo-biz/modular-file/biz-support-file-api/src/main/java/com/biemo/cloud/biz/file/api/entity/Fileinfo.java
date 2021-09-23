package com.biemo.cloud.biz.file.api.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 文件信息表
 * </p>
 *
 *
 * @since 2018-07-27
 */
@TableName("sys_fileinfo")
public class Fileinfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文件ID
     */
    @TableId("FILE_ID")
    private Long fileId;
    /**
     * 应用编码
     */
    @TableField("APP_CODE")
    private String appCode;
    /**
     * 文件路径
     */
    @TableField("FILE_URL")
    private String fileUrl;
    /**
     * 文件名称
     */
    @TableField("FILE_ORIGIN_NAME")
    private String fileOriginName;
    /**
     * 文件后缀
     */
    @TableField("FILE_SUFFIX")
    private String fileSuffix;
    /**
     * 文件大小
     */
    @TableField("FILE_SIZE")
    private Long fileSize;
    /**
     * 文件唯一名称
     */
    @TableField("FILE_STORAGE_NAME")
    private String fileStorageName;
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


    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileOriginName() {
        return fileOriginName;
    }

    public void setFileOriginName(String fileOriginName) {
        this.fileOriginName = fileOriginName;
    }

    public String getFileSuffix() {
        return fileSuffix;
    }

    public void setFileSuffix(String fileSuffix) {
        this.fileSuffix = fileSuffix;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileStorageName() {
        return fileStorageName;
    }

    public void setFileStorageName(String fileStorageName) {
        this.fileStorageName = fileStorageName;
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
        return "Fileinfo{" +
                ", fileId=" + fileId +
                ", appCode=" + appCode +
                ", fileUrl=" + fileUrl +
                ", fileOriginName=" + fileOriginName +
                ", fileSuffix=" + fileSuffix +
                ", fileSize=" + fileSize +
                ", fileStorageName=" + fileStorageName +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                "}";
    }
}
