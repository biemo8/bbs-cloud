package com.biemo.cloud.system.modular.ent.service;

import com.biemo.cloud.kernel.model.enums.StatusEnum;
import com.biemo.cloud.kernel.model.page.PageResult;
import com.biemo.cloud.system.modular.ent.entity.EntDept;
import com.biemo.cloud.system.modular.ent.model.params.EntDeptParam;
import com.biemo.cloud.system.modular.ent.model.result.EntDeptResult;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 部门表 服务类
 * </p>
 *
 *
 * @since 2019-10-10
 */
public interface EntDeptService extends IService<EntDept> {

    /**
     * 新增
     *
     *
     * @Date 2019-10-10
     */
    void add(EntDeptParam param);

    /**
     * 删除
     *
     *
     * @Date 2019-10-10
     */
    void delete(EntDeptParam param);

    /**
     * 更新
     *
     *
     * @Date 2019-10-10
     */
    void update(EntDeptParam param);

    /**
     * 查询分页数据，Specification模式
     *
     *
     * @Date 2019-10-10
     */
     PageResult<EntDeptResult> findPageBySpec(EntDeptParam param);

     /**
      * 获取部门详情
      *
      *
      * @date 2019/10/13
      */
    EntDeptResult detail(Long deptId);

    /**
     * 部门禁用启用
     *
     *
     * @date 2019/10/13
     */
    void changeStatus(Long deptId, StatusEnum toEnum);

    /**
     * 获取部门树列表
     *
     *
     * @date 2019/10/13
     */
    List<Map<String,Object>> queryDeptTree(Long companyId);

}
