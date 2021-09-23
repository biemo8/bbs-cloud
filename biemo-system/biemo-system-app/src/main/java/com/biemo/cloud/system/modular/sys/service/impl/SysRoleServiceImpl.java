package com.biemo.cloud.system.modular.sys.service.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.biemo.cloud.auth.api.context.LoginContext;
import com.biemo.cloud.system.core.error.SysExceptionEnum;
import com.biemo.cloud.system.modular.res.enums.AppExceptionEnum;
import com.biemo.cloud.system.modular.sys.entity.*;
import com.biemo.cloud.system.modular.sys.mapper.*;
import com.biemo.cloud.system.modular.sys.model.RoleParam;
import com.biemo.cloud.system.modular.sys.model.params.RoleInfo;
import com.biemo.cloud.system.modular.sys.model.params.SysRoleParam;
import com.biemo.cloud.system.modular.sys.model.result.SysRoleResult;
import com.biemo.cloud.system.modular.sys.service.*;
import com.biemo.cloud.core.page.PageFactory;
import com.biemo.cloud.core.util.ToolUtil;
import com.biemo.cloud.kernel.model.enums.StatusEnum;
import com.biemo.cloud.kernel.model.enums.YesOrNotEnum;
import com.biemo.cloud.kernel.model.exception.RequestEmptyException;
import com.biemo.cloud.kernel.model.exception.ServiceException;
import com.biemo.cloud.kernel.model.page.PageResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 *
 * @since 2019-09-10
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Autowired
    private SysPermissionService sysPermissionService;

    @Resource
    private SysUserRoleMapper userRoleMapper;

    @Resource
    private SysPermissionResourceMapper permissionResourceMapper;

    @Resource
    private SysRolePermissionService sysRolePermissionService;

    @Resource
    private SysRoleMapper roleMapper;

    @Resource
    private SysRolePermissionMapper rolePermissionMapper;

    @Resource
    private SysPermissionMapper permissionMapper;

    @Autowired
    private SysAppService appService;

    @Override
    public void add(SysRoleParam param) {
        SysRole entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(SysRoleParam param) {
        this.removeById(getKey(param));
    }

    @Override
    public void update(SysRoleParam param) {
        SysRole oldEntity = getOldEntity(param);
        SysRole newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public SysRoleResult findBySpec(SysRoleParam param) {
        return null;
    }

    @Override
    public List<SysRoleResult> findListBySpec(SysRoleParam param) {
        return null;
    }

    @Override
    public PageResult findPageBySpec(SysRoleParam param) {
        Page pageContext = getPageContext();
        IPage page = this.baseMapper.customPageList(pageContext, param);
        return new PageResult(page);
    }

    @Override
    public Set<String> getUserResByAccountId(Long accountId) {

        // 获取用户角色
        List<RoleInfo> userRoleInfo = sysUserRoleService.getUserRoleInfo(accountId);
        if (userRoleInfo == null || userRoleInfo.size() == 0) {
            return new HashSet<>();
        }

        // 获取用户权限
        return sysPermissionService.getUserResources(userRoleInfo);
    }

    private Serializable getKey(SysRoleParam param) {
        return param.getRoleId();
    }

    private Page getPageContext() {
        return PageFactory.defaultPage();
    }

    private SysRole getOldEntity(SysRoleParam param) {
        return this.getById(getKey(param));
    }

    private SysRole getEntity(SysRoleParam param) {
        SysRole entity = new SysRole();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

    @Override
    public Set<Long> getUserRoles(Long accountId) {

        Set<Long> roleIds = new HashSet<>();
//        List<SysUserRole> userRoles = userRoleMapper.selectList(new QueryWrapper<SysUserRole>().eq("ACCOUNT_ID",
//        accountId));
        List<SysUserRole> userRoles = userRoleMapper.userRoles(accountId);
        for (SysUserRole userRole : userRoles) {
            roleIds.add(userRole.getRoleId());
        }
        return roleIds;
    }

    @Override
    public Set<Long> getUserPermission(Long accountId) {

        Set<Long> roleIds = this.getUserRoles(accountId);
        Set<Long> permissions = new HashSet<>();

        if (roleIds == null || roleIds.size() == 0) {
            return permissions;
        }

        List<SysRolePermission> rolePermissions =
                rolePermissionMapper.selectList(new QueryWrapper<SysRolePermission>().in("role_id", roleIds));
        for (SysRolePermission rolePermission : rolePermissions) {
            permissions.add(rolePermission.getPermissionId());
        }
        return permissions;
    }

    @Override
    public Set<String> getUserResources(Long accountId) {

        Set<Long> permissionIds = this.getUserPermission(accountId);
        Set<String> resources = new HashSet<>();

        if (permissionIds == null || permissionIds.size() == 0) {
            return resources;
        }

        List<SysPermissionResource> permissionResources =
                permissionResourceMapper.selectList(
                        new QueryWrapper<SysPermissionResource>().in("permission_id", permissionIds));
        for (SysPermissionResource permissionResource : permissionResources) {
            resources.add(permissionResource.getResourceId());
        }
        return resources;
    }

    @Override
    public Set<String> getUserResourcesByApp(Long accountId, Long appId) {

        if (ToolUtil.isOneEmpty(accountId, appId)) {
            throw new ServiceException(SysExceptionEnum.REQUEST_NULL);
        }

        //通过appId获取appCode
        SysApp app = appService.getById(appId);
        if (ToolUtil.isEmpty(app)) {
            throw new ServiceException(AppExceptionEnum.APP_NOT_FOUND);
        }
        String appCode = app.getAppCode();

        Set<String> userResources = this.getUserResources(accountId);
        HashSet<String> results = new HashSet<>();
        for (String userResource : userResources) {
            if (userResource.startsWith(appCode)) {
                results.add(userResource);
            }
        }

        return results;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addRole(SysRole role) {

        //校验必填项
        validateRequired(role);

        //校验重复项
        validateRepeat(role);

        // 状态
        if (role.getStatus() == null) {
            role.setStatus(StatusEnum.ENABLE.getCode());
        }

        // 登录人
        role.setRoleId(IdWorker.getId());
        role.setCreateTime(new Date());
        role.setCreateUser(LoginContext.me().getAccountId());
        role.setDelFlag(YesOrNotEnum.N.name());
        try {
            roleMapper.insert(role);
        } catch (DuplicateKeyException e) {
            throw new RequestEmptyException("code码已被使用");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateRole(SysRole role) {

        //校验必填项
        validateRequired(role);

        //校验重复项
        if (role != null) {
            SysRole roleTemp = roleMapper.selectById(role.getRoleId());
            if (StrUtil.isNotBlank(roleTemp.getRoleCode())) {
                role.setRoleCode(null);
            }
        }

        validateRepeat(role);

        role.setUpdateUser(LoginContext.me().getAccountId());
        role.setUpdateTime(new Date());

        return roleMapper.updateById(role);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateStatus(Long roleId, int status) {

        // 状态判断
        if (StrUtil.isEmpty(StatusEnum.getNameByCode(status))) {
            throw new RequestEmptyException("状态为空");
        }

        // 获取roleId 对应的实体
        SysRole role = this.getById(roleId);
        if (role == null) {
            throw new RequestEmptyException();
        }

        role.setStatus(status);
        role.setUpdateUser(LoginContext.me().getAccountId());
        role.setUpdateTime(new Date());

        return roleMapper.updateById(role);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRole(Long roleId) {

        // 获取roleId 对应的实体
        SysRole role = this.getById(roleId);
        if (role == null) {
            throw new RequestEmptyException();
        }

        role.setDelFlag(YesOrNotEnum.Y.name());
        role.setUpdateUser(LoginContext.me().getAccountId());
        role.setUpdateTime(new Date());
        roleMapper.updateById(role);
        Map<String, Object> removeParam = MapUtil.builder(new HashMap<String, Object>())
                .put("role_id", roleId).build();
        this.rolePermissionMapper.deleteByMap(removeParam);
    }

    @Override
    public Page<RoleParam> findRolePage(SysRole role) {
        role.setDelFlag(YesOrNotEnum.N.name());
        Page<RoleParam> page = PageFactory.defaultPage();
        page.setRecords(roleMapper.findRolePage(page, role));
        return page;
    }

    @Override
    public List<RoleParam> findRoleList(SysRole role) {

        role.setDelFlag(YesOrNotEnum.N.name());
        return roleMapper.findRoleList(role);
    }

    @Override
    public RoleParam findRoleDetail(SysRole role) {

        role.setDelFlag(YesOrNotEnum.N.name());
        return roleMapper.findRoleDetail(role);
    }

    @Override
    public boolean validateName(Long appId, Long roleId, String roleName) {

        //验空
        if (ToolUtil.isEmpty(appId)) {
            throw new RequestEmptyException("appId为空");
        }

        //验空
        if (StrUtil.isBlank(roleName)) {
            throw new RequestEmptyException("角色名称为空");
        }

        SysRole role = new SysRole();
        role.setRoleName(roleName);
        role.setAppId(appId);
        role.setDelFlag(YesOrNotEnum.N.name());
        RoleParam roleVo = roleMapper.findRoleDetail(role);

        //验重
        if (roleVo == null) {
            return true;
        } else if (roleVo.getRoleId().equals(String.valueOf(roleId))) {
            return true;
        }
        return false;
    }

    @Override
    public boolean validateCode(Long appId, Long roleId, String roleCode) {

        //验空
        if (ToolUtil.isEmpty(appId)) {
            throw new RequestEmptyException("appId为空");
        }

        //验空
        if (StrUtil.isNotBlank(roleCode)) {
            SysRole role = new SysRole();
            role.setRoleCode(roleCode);
            role.setAppId(appId);
            role.setDelFlag(YesOrNotEnum.N.name());
            RoleParam roleVo = roleMapper.findRoleDetail(role);

            //验重
            if (roleVo == null) {
                return true;
            } else if (StrUtil.equals(roleVo.getRoleId(), String.valueOf(roleId))) {
                return true;
            }
        }
        return false;
    }

    private boolean validateRepeat(SysRole role) {

        //验重 角色名称
        if (!validateName(role.getAppId(), role.getRoleId(), role.getRoleName())) {
            throw new ServiceException(SysExceptionEnum.ROLE_NAME_REPEAT);
        }

        //验重 角色编码
        if (StrUtil.isNotBlank(role.getRoleCode())) {
            if (!validateCode(role.getAppId(), role.getRoleId(), role.getRoleCode())) {
                throw new ServiceException(SysExceptionEnum.ROLE_CODE_REPEAT);
            }
        }
        return true;
    }

    private boolean validateRequired(SysRole role) {

        // 角色名称不为空
        if (StrUtil.isEmpty(role.getRoleName())) {
            throw new RequestEmptyException("角色名称为空");
        }

        // 所属应用不为空
        if (ToolUtil.isEmpty(role.getAppId())) {
            throw new RequestEmptyException("应用id为空");
        }

        //角色排序码
        if (role.getOrderNo() != null && role.getOrderNo().compareTo(new BigDecimal(10000)) > 0) {
            throw new RequestEmptyException("排序码为空");
        }

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void assignPermission(Long roleId, List<Long> permissionIds) {

        Map<String, Object> param = new HashMap<>();
        param.put("roleId", roleId);
        param.put("permissionIds", permissionIds);

        rolePermissionMapper.deleteByRoleID(param);

        ArrayList<SysRolePermission> sysRolePermissions = new ArrayList<>();
        for (Long permissionId : permissionIds) {
            SysRolePermission sysRolePermission = new SysRolePermission();
            sysRolePermission.setRoleId(roleId);
            sysRolePermission.setPermissionId(permissionId);
            sysRolePermissions.add(sysRolePermission);
        }
        sysRolePermissionService.saveBatch(sysRolePermissions);
    }

    @Override
    public List<Map<String, Object>> findPermissions(Long roleId, Long appId) {

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("roleId", roleId);
        param.put("appId", appId);
        param.put("delFlag", YesOrNotEnum.N.name());
        param.put("status", StatusEnum.ENABLE.getCode());

        return permissionMapper.findPermissionByRole(param);
    }

    @Override
    public List<Long> findRoleAllPermissions(Long roleId) {

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("roleId", roleId);

        return permissionMapper.findRoleAllPermissions(param);
    }

    @Override
    public Page selectRoleByAccountId(Page page, Long accountId) {

        // 获取角色id集合。
        List<Long> roleIds = userRoleMapper.findRoleIdsByAccountId(accountId);
        List<Map<String, Object>> roleInfoList = null;
        if (roleIds != null && roleIds.size() > 0) {
            roleInfoList = roleMapper.findRoleInfoList(page, roleIds);
        }
        page.setRecords(roleInfoList);

        return page;
    }

}
