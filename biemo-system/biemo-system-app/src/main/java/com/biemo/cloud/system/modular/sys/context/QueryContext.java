package com.biemo.cloud.system.modular.sys.context;

import cn.hutool.core.util.StrUtil;
import com.biemo.cloud.system.modular.sys.entity.SysApp;
import com.biemo.cloud.system.modular.sys.entity.SysMenu;
import com.biemo.cloud.system.modular.sys.entity.SysResource;
import com.biemo.cloud.system.modular.sys.mapper.SysAppMapper;
import com.biemo.cloud.system.modular.sys.mapper.SysMenuMapper;
import com.biemo.cloud.system.modular.sys.mapper.SysResourceMapper;
import com.biemo.cloud.core.util.SpringContextHolder;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 通用快捷查询方法
 *
 *
 * @date 2018-01-23 16:21
 */
@Component
public class QueryContext {

    /**
     * 空查询对应的值
     */
    private static final String EMPTY_QUERY_NAME = "";

    @Autowired
    private SysAppMapper appMapper;

    @Autowired
    private SysMenuMapper menuMapper;

    @Autowired
    private SysResourceMapper resourceMapper;

    public static QueryContext me() {
        return SpringContextHolder.getBean(QueryContext.class);
    }

    /**
     * 通过应用id获取应用名称
     */
    public String getAppNameById(String appId) {
        if (StrUtil.isBlank(appId)) {
            return EMPTY_QUERY_NAME;
        }
        SysApp result = appMapper.selectById(appId);
        if (result != null) {
            return result.getAppName();
        } else {
            return EMPTY_QUERY_NAME;
        }
    }

    /**
     * 获取菜单名称通过id
     */
    public String getMenuNameById(String menuId) {
        if (StrUtil.isBlank(menuId)) {
            return EMPTY_QUERY_NAME;
        }
        SysMenu menu = this.menuMapper.selectById(menuId);
        if (menu != null) {
            return menu.getName();
        } else {
            return EMPTY_QUERY_NAME;
        }
    }

    /**
     * 获取资源名称通过资源code
     */
    public String getResourceNameByCode(String resCode) {
        if (StrUtil.isBlank(resCode)) {
            return EMPTY_QUERY_NAME;
        }
        SysResource resourceName = this.resourceMapper.selectOne(new QueryWrapper<SysResource>().eq("code", resCode));
        if (resourceName != null) {
            return resourceName.getName();
        } else {
            return EMPTY_QUERY_NAME;
        }
    }

}
