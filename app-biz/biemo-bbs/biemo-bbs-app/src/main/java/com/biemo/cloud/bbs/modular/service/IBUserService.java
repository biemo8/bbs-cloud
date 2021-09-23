package com.biemo.cloud.bbs.modular.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.biemo.cloud.bbs.api.vo.BUserVo;
import com.biemo.cloud.bbs.modular.domain.BUser;
import com.biemo.cloud.kernel.model.response.ResponseData;

/**
 * Service接口
 *
 * @author makesoft
 * @date 2021-08-11
 */
public interface IBUserService extends IService<BUser>
{

    ResponseData signin(String captchaId, String captchaCode, String username, String password);

    BUser getcurrent();

    ResponseData msgrecent();

    boolean signout(String userToken);

    int updateCache(BUser user);

    ResponseData signup(BUserVo bUserVo);
}
