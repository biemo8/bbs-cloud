package com.biemo.cloud.system.modular.ent.service;

import com.biemo.cloud.system.modular.ent.entity.EntDuty;
import com.biemo.cloud.system.modular.ent.model.params.EntDutyParam;
import com.biemo.cloud.system.modular.ent.model.result.EntDutyResult;
import com.biemo.cloud.kernel.model.enums.StatusEnum;
import com.biemo.cloud.kernel.model.page.PageResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 职务管理 服务类
 * </p>
 *
 *
 * @since 2019-10-10
 */
public interface EntDutyService extends IService<EntDuty> {

    /**
     * 新增
     *
     *
     * @Date 2019-10-10
     */
    void add(EntDutyParam param);

    /**
     * 删除
     *
     *
     * @Date 2019-10-10
     */
    void delete(EntDutyParam param);

    /**
     * 更新
     *
     *
     * @Date 2019-10-10
     */
    void update(EntDutyParam param);

    /**
     * 查询分页数据，Specification模式
     *
     *
     * @Date 2019-10-10
     */
     PageResult<EntDutyResult> findPageBySpec(String dutyName,String dutyCode);

     /**
      * 职务启用禁用
      *
      *
      * @date 2019/10/13
      */
    void changeStatus(Long dutyId, StatusEnum toEnum);

    /**
     * 职务不分页列表
     *
     *
     * @date 2019/10/14
     */
    List<Map<String,Object>> queryList();
}
