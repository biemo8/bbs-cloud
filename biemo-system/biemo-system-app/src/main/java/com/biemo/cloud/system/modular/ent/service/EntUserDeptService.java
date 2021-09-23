package com.biemo.cloud.system.modular.ent.service;

import com.biemo.cloud.system.modular.ent.entity.EntUserDept;
import com.biemo.cloud.system.modular.ent.model.params.EntUserDeptParam;
import com.biemo.cloud.system.modular.ent.model.result.EntUserDeptResult;
import com.biemo.cloud.kernel.model.page.PageResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户部门关联表 服务类
 * </p>
 *
 *
 * @since 2019-10-10
 */
public interface EntUserDeptService extends IService<EntUserDept> {

    /**
     * 新增
     *
     *
     * @Date 2019-10-10
     */
    void add(EntUserDeptParam param);

    /**
     * 删除
     *
     *
     * @Date 2019-10-10
     */
    void delete(EntUserDeptParam param);

    /**
     * 更新
     *
     *
     * @Date 2019-10-10
     */
    void update(EntUserDeptParam param);

    /**
     * 查询单条数据，Specification模式
     *
     *
     * @Date 2019-10-10
     */
    EntUserDeptResult findBySpec(EntUserDeptParam param);

    /**
     * 查询列表，Specification模式
     *
     *
     * @Date 2019-10-10
     */
    List<EntUserDeptResult> findListBySpec(EntUserDeptParam param);

    /**
     * 查询分页数据，Specification模式
     *
     *
     * @Date 2019-10-10
     */
    PageResult<EntUserDeptResult> findPageBySpec(EntUserDeptParam param);

    /**
     * 获取用户默认部门
     *
     *
     * @Date 2019/10/11 20:18
     */
    EntUserDept getUserDefaultDept(Long userId);
}
