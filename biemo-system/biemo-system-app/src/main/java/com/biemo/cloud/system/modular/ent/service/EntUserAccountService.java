package com.biemo.cloud.system.modular.ent.service;

import com.biemo.cloud.system.modular.ent.entity.EntUserAccount;
import com.biemo.cloud.system.modular.ent.model.params.EntUserAccountParam;
import com.biemo.cloud.system.modular.ent.model.result.EntUserAccountResult;
import com.biemo.cloud.kernel.model.page.PageResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 登录账号 服务类
 * </p>
 *
 *
 * @since 2019-10-10
 */
public interface EntUserAccountService extends IService<EntUserAccount> {

    /**
     * 新增
     *
     *
     * @Date 2019-10-10
     */
    void add(EntUserAccountParam param);

    /**
     * 删除
     *
     *
     * @Date 2019-10-10
     */
    void delete(EntUserAccountParam param);

    /**
     * 更新
     *
     *
     * @Date 2019-10-10
     */
    void update(EntUserAccountParam param);

    /**
     * 查询单条数据，Specification模式
     *
     *
     * @Date 2019-10-10
     */
    EntUserAccountResult findBySpec(EntUserAccountParam param);

    /**
     * 查询列表，Specification模式
     *
     *
     * @Date 2019-10-10
     */
    List<EntUserAccountResult> findListBySpec(EntUserAccountParam param);

    /**
     * 查询分页数据，Specification模式
     *
     *
     * @Date 2019-10-10
     */
    PageResult<EntUserAccountResult> findPageBySpec(EntUserAccountParam param);

    /**
     * 通过账号获取用户账号信息
     *
     *
     * @Date 2019/10/11 20:17
     */
    EntUserAccount getAccountInfoByAccount(String account);
}
