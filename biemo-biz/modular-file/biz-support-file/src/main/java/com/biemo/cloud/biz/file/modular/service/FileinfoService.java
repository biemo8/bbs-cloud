package com.biemo.cloud.biz.file.modular.service;

import cn.hutool.core.io.IoUtil;
import com.biemo.cloud.biz.file.api.entity.Fileinfo;
import com.biemo.cloud.biz.file.core.exceptions.FileExceptionEnum;
import com.biemo.cloud.biz.file.core.storage.FileOperator;
import com.biemo.cloud.biz.file.core.util.FileUtil;
import com.biemo.cloud.biz.file.modular.factory.FileFactory;
import com.biemo.cloud.biz.file.modular.mapper.FileinfoMapper;
import com.biemo.cloud.core.page.PageFactory;
import com.biemo.cloud.core.util.ToolUtil;
import com.biemo.cloud.kernel.model.exception.RequestEmptyException;
import com.biemo.cloud.kernel.model.exception.ServiceException;
import com.biemo.cloud.kernel.model.exception.enums.CoreExceptionEnum;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 文件信息表 服务实现类
 * </p>
 *
 *
 * @since 2018-07-27
 */
@Service
public class FileinfoService extends ServiceImpl<FileinfoMapper, Fileinfo> {

    @Autowired
    private FileOperator fileOperator;

    /**
     * 获取文件详细信息
     *
     *
     * @Date 2018/7/27 下午3:43
     */
    public Fileinfo getFileInfo(Long fileId) {
        Fileinfo fileinfo = this.getById(fileId);
        if (fileinfo == null) {
            throw new ServiceException(CoreExceptionEnum.FILE_NOT_FOUND);
        } else {
            return fileinfo;
        }
    }

    /**
     * 存储文件
     *
     *
     * @Date 2018/7/27 下午3:40
     */
    @Transactional(rollbackFor = Exception.class)
    public String uploadFileAndSaveFileInfo(InputStream inputStream, String fileName, Long size) {

        try {
            //保存文件
            long uuid = IdWorker.getId();
            String storageName = uuid + "." + FileUtil.getFileSuffix(fileName);
            fileOperator.storageFile(storageName, inputStream);

            //生成文件信息
            Fileinfo fileInfo = FileFactory.getFileInfo("", fileName, size, uuid, storageName);

            //存储文件信息到数据库
            this.save(fileInfo);

            //返回文件的持久化id
            return Long.toString(uuid);
        } finally {
            IoUtil.close(inputStream);
        }
    }

    /**
     * 获取文件的url通过文件id
     *
     *
     * @Date 2018/7/27 下午3:59
     */
    public String getFileUrlById(Long fileId) {
        if (ToolUtil.isEmpty(fileId)) {
            throw new RequestEmptyException("文件id为空！");
        }
        Fileinfo fileinfo = this.getById(fileId);
        if (fileinfo == null) {
            throw new ServiceException(FileExceptionEnum.FILE_NOT_FOUND);
        }

        return fileOperator.getFileAuthUrl(fileinfo.getFileStorageName());
    }

    /**
     * 获取文件信息列表
     *
     *
     * @Date 2018/7/27 下午4:13
     */
    public List<Fileinfo> getFileInfoList(Page page, Fileinfo fileinfo) {
        if (page == null) {
            page = PageFactory.defaultPage();
        }
        if (fileinfo == null) {
            fileinfo = new Fileinfo();
        }
        return this.baseMapper.getFileInfoList(page, fileinfo);
    }

}
