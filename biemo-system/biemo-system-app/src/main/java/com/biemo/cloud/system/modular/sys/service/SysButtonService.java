package com.biemo.cloud.system.modular.sys.service;

import com.biemo.cloud.system.modular.sys.entity.SysButton;
import com.biemo.cloud.system.modular.sys.model.params.SysButtonParam;
import com.biemo.cloud.kernel.model.enums.StatusEnum;
import com.biemo.cloud.kernel.model.page.PageResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 按钮表 服务类
 * </p>
 *
 *
 * @since 2019-11-02
 */
public interface SysButtonService extends IService<SysButton> {

    /**
     * 新增
     *
     *
     * @Date 2019-11-02
     */
    void add(SysButtonParam param);

    /**
     * 删除
     *
     * @param buttonIds
     *
     * @Date 2019-11-02
     */
    void delete(Long[] buttonIds);

    /**
     * 更新
     *
     *
     * @Date 2019-11-02
     */
    void update(SysButtonParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @return
     *
     * @Date 2019-11-02
     */
    PageResult<Map<String, Object>> findPageBySpec(Long menuId, String buttonName, String buttonCode);

    /**
     * 获取按钮详情
     *
     *
     * @date 2019/11/2
     */
    SysButton getDetail(Long buttonId);

    /**
     * 改变按钮状态
     *
     *
     * @date 2019/11/2
     */
    void changeStatus(Long buttonId, StatusEnum toEnum);

    /**
     * 根据应用id获取按钮资源集合
     *
     *
     * @date 2019/11/3
     */
    Set<String> getButtonResCodesByAppId(Long appId);

    /**
     * 获取资源级联列表
     *
     *
     * @date 2019/11/3
     */
    List<Map<String, Object>> getResources(Long appId);
}
