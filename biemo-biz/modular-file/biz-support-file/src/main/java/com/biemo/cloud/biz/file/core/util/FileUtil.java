package com.biemo.cloud.biz.file.core.util;


import com.biemo.cloud.core.util.ToolUtil;

/**
 * 文件相关的工具类
 *
 *
 * @Date 2018/7/27 下午3:08
 */
public class FileUtil {

    public static final String FILE_DEFAULT_NAME = "default";

    public static final String FILE_DEFAULT_SUFFIX = "txt";

    /**
     * 获取文件后缀名 不包含点
     *
     *
     * @Date 2018/6/27 下午3:50
     */
    public static String getFileSuffix(String fileWholeName) {
        int lastIndexOf = fileWholeName.lastIndexOf(".");
        return fileWholeName.substring(lastIndexOf + 1);
    }

    /**
     * 获取文件名称(考虑文件名和后缀为空的情况，返回默认的文件名和后缀)
     *
     *
     * @Date 2018/7/27 下午3:47
     */
    public static String getFileName(String fileName, String suffix) {
        if (ToolUtil.isEmpty(fileName)) {
            if (ToolUtil.isEmpty(suffix)) {
                return FILE_DEFAULT_NAME + "." + FILE_DEFAULT_SUFFIX;
            } else {
                return FILE_DEFAULT_NAME + "." + suffix;
            }
        } else {
            return fileName;
        }
    }
}
