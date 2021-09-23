package com.biemo.cloud.biz.file.core.storage;

import java.io.InputStream;

/**
 * 文件操纵者
 *
 *
 * @date 2018-06-27-下午12:37
 */
public interface FileOperator {

    /**
     * 判断是否存在文件
     *
     *
     * @Date 2018/6/27 下午1:14
     */
    boolean isExistingFile(String fileName);

    /**
     * 获取文件字节
     *
     *
     * @Date 2018/6/27 下午1:15
     */
    byte[] getFileBytes(String fileName);

    /**
     * 存储文件
     *
     *
     * @Date 2018/6/27 下午1:16
     */
    void storageFile(String fileName, InputStream inputStream);

    /**
     * 存储文件
     *
     *
     * @Date 2018/6/27 下午1:16
     */
    void storageFile(String fileName, byte[] bytes);

    /**
     * 描述:拷贝一份新的原文件信息
     *
     * @author 张建堂
     * @date 2018年6月28日 下午1:26:32
     */
    boolean copyFile(String sourceFileFinalName, String newFileFinalName);

    /**
     * 获取文件的下载地址（带鉴权的）
     *
     *
     * @Date 2018/7/7 上午11:27
     */
    String getFileAuthUrl(String fileFinalName);

}
