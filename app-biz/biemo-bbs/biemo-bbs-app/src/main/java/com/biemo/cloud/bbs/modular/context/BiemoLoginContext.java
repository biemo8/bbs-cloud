package com.biemo.cloud.bbs.modular.context;

import com.biemo.cloud.bbs.modular.domain.BUser;
import com.biemo.cloud.bbs.modular.service.IBUserService;
import com.biemo.cloud.core.util.SpringContextHolder;

public class BiemoLoginContext {

    private IBUserService ibUserService;

    public static BiemoLoginContext  me(){
        return SpringContextHolder.getBean(BiemoLoginContext.class);
    }

    public BiemoLoginContext(IBUserService ibUserService) {
        this.ibUserService = ibUserService;
    }

    public   BUser getCurrentUser(){
        return ibUserService.getcurrent();
    }

}
