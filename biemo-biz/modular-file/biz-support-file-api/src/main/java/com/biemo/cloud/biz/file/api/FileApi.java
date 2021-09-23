package com.biemo.cloud.biz.file.api;

import com.biemo.cloud.biz.file.api.entity.Fileinfo;
import com.biemo.cloud.biz.file.api.exception.FileApiServiceException;
import com.biemo.cloud.biz.file.api.model.FileByteInfo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 文件相关远程调用接口
 *
 *
 * @date 2018-07-27-上午10:12
 */
@RequestMapping("/api/file")
public interface FileApi {

    /**
     * 获取文件详细信息
     *
     *
     * @Date 2018/7/27 下午3:43
     */
    @RequestMapping(value = "/getFileInfo", method = RequestMethod.POST)
    Fileinfo getFileInfo(@RequestParam("fileId") Long fileId) throws FileApiServiceException;

    /**
     * 存储文件
     *
     *
     * @Date 2018/7/27 下午3:40
     */
    @RequestMapping(value = "/uploadFileAndSaveFileInfo", method = RequestMethod.POST)
    String uploadFileAndSaveFileInfo(@RequestBody FileByteInfo byteInfo,
                                     @RequestParam("fileName") String fileName,
                                     @RequestParam("size") Long size) throws FileApiServiceException;

    /**
     * 获取文件的url通过文件id
     *
     *
     * @Date 2018/7/27 下午3:59
     */
    @RequestMapping(value = "/getFileUrlById", method = RequestMethod.POST)
    String getFileUrlById(@RequestParam("fileId") Long fileId) throws FileApiServiceException;

    /**
     * 获取文件信息列表
     *
     *
     * @Date 2018/7/27 下午4:13
     */
    @RequestMapping(value = "/getFileInfoList", method = RequestMethod.POST)
    List<Fileinfo> getFileInfoList(@RequestBody Fileinfo fileinfo,
                                   @RequestParam("pageNo") Integer pageNo,
                                   @RequestParam("pageSize") Integer pageSize) throws FileApiServiceException;

}
