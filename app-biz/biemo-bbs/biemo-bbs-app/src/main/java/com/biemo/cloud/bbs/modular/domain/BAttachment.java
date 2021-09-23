package com.biemo.cloud.bbs.modular.domain;



import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

@TableName("b_attachment")
public class BAttachment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 文件名称
     */
    @TableField
    private String fileName;

    /**
     * 用户ID
     */
    @TableField
    private String userId;

    @TableField
    private String userName;

    /**
     * 上传时间
     */
    @TableField
    private Date uploadDate;

    /**
     * 上传的ID
     */
    @TableField
    private String uploadIp;

    /**
     * 文件扩展名
     */
    @TableField
    private String fileExtname;

    /**
     * 文件路径
     */
    @TableField
    private String filePath;

    /**
     * 文件大小
     */
    @TableField
    private Float fileSize;
    /**
     * 附件网址
     */
    @TableField
    private String url;

    @TableField
    private String originalFileName;

    @TableField
    private Integer type;

    /**
     * key
     */
    @TableField
    private String fileKey;

    public String getFileKey() {
        return fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取文件名称
     *
     * @return file_name - 文件名称
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * 设置文件名称
     *
     * @param fileName 文件名称
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * 获取用户ID
     *
     * @return user_id - 用户ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户ID
     *
     * @param userId 用户ID
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return username
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 获取上传时间
     *
     * @return upload_date - 上传时间
     */
    public Date getUploadDate() {
        return uploadDate;
    }

    /**
     * 设置上传时间
     *
     * @param uploadDate 上传时间
     */
    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    /**
     * 获取上传的ID
     *
     * @return upload_ip - 上传的ID
     */
    public String getUploadIp() {
        return uploadIp;
    }

    /**
     * 设置上传的ID
     *
     * @param uploadIp 上传的ID
     */
    public void setUploadIp(String uploadIp) {
        this.uploadIp = uploadIp;
    }

    /**
     * 获取文件扩展名
     *
     * @return file_extname - 文件扩展名
     */
    public String getFileExtname() {
        return fileExtname;
    }

    /**
     * 设置文件扩展名
     *
     * @param fileExtname 文件扩展名
     */
    public void setFileExtname(String fileExtname) {
        this.fileExtname = fileExtname;
    }

    /**
     * 获取文件路径
     *
     * @return file_path - 文件路径
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * 设置文件路径
     *
     * @param filePath 文件路径
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * 获取文件大小
     *
     * @return file_size - 文件大小
     */
    public Float getFileSize() {
        return fileSize;
    }

    /**
     * 设置文件大小
     *
     * @param fileSize 文件大小
     */
    public void setFileSize(Float fileSize) {
        this.fileSize = fileSize;
    }

    /**
     * 获取附件网址
     *
     * @return url - 附件网址
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置附件网址
     *
     * @param url 附件网址
     */
    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
