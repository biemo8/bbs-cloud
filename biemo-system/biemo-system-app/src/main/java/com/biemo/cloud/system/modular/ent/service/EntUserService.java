package com.biemo.cloud.system.modular.ent.service;

import com.biemo.cloud.system.modular.ent.entity.EntUser;
import com.biemo.cloud.system.modular.ent.model.params.*;
import com.biemo.cloud.system.modular.ent.model.result.EntUserQueryResult;
import com.biemo.cloud.system.modular.ent.model.result.EntUserResult;
import com.biemo.cloud.kernel.model.enums.StatusEnum;
import com.biemo.cloud.kernel.model.page.PageResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户信息 服务类
 * </p>
 *
 *
 * @since 2019-10-10
 */
public interface EntUserService extends IService<EntUser> {

    /**
     * 新增
     *
     *
     * @Date 2019-10-10
     */
    void add(EntUserParam param);

    /**
     * 删除
     *
     *
     * @Date 2019-10-10
     */
    void delete(EntUserParam param);

    /**
     * 更新
     *
     *
     * @Date 2019-10-10
     */
    void update(EntUserParam param);

    /**
     * 查询分页数据，Specification模式
     *
     *
     * @Date 2019-10-10
     */
     PageResult<EntUserQueryResult> findPageBySpec(EntUserQueryParam param);

     /**
      * 获取用户在字典表对应的信息
      *
      *
      * @date 2019/10/14
      */
    List<Map<String,Object>> queryInfoByDictType(String dictTypeCode);

    /**
     * 获取人员详情
     *
     *
     * @date 2019/10/15
     */
    EntUserResult detail(Long userId);

    /**
     * 改变人员的状态
     *
     *
     * @date 2019/10/15
     */
    void changeStatus(Long userId, StatusEnum toEnum);

    /**
     * 获取公司下拉列表
     *
     *
     * @date 2019/10/27
     */
    List<Map<String,Object>> getCompanySelect();

    /**
     * 获取部门下拉列表
     *
     *
     * @date 2019/10/27
     */
    List<Map<String,Object>> getDeptSelect(Long companyId);

    /**
     * 获取职务下拉列表
     *
     *
     * @date 2019/10/27
     */
    List<Map<String,Object>> getDutySelect();

    /**
     * 获取所有的角色信息
     *
     *
     * @date 2019/10/27
     * @param userId
     */
    List<Map<String,Object>> getRoles(Long userId);

    /**
     * 保存用户的角色信息
     *
     *
     * @date 2019/10/28
     */
    void saveRoles(EntUserRoleParam param);

    /**
     * 获取当前登录人的信息
     *
     *
     * @date 2019/10/30
     * @return
     */
    Map<String, Object> getCurrentUser();

    /**
     * 保存当前登录人信息
     *
     *
     * @date 2019/10/30
     * @param
     */
    void saveCurrentUser(EntCurrentParam param);

    /**
     * 修改密码
     *
     *
     * @date 2019/10/30
     */
    void updatePassword(EntPasswordParam passwordParam);

    /**
     * 重置密码
     *
     *
     * @date 2019/10/30
     */
    String resetPassword(Long uerId);
}
