package com.biemo.cloud.bbs.modular.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biemo.cloud.bbs.modular.domain.BAttachment;
import com.biemo.cloud.bbs.modular.mapper.BAttachmentMapper;
import com.biemo.cloud.bbs.modular.service.IBAttachmentService;
import org.springframework.stereotype.Service;

@Service
public class BAttachmentServiceImpl extends ServiceImpl<BAttachmentMapper, BAttachment> implements IBAttachmentService {
}
