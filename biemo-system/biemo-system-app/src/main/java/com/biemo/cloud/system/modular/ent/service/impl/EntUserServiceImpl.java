package com.biemo.cloud.system.modular.ent.service.impl;

import com.biemo.cloud.auth.api.context.LoginContext;
import com.biemo.cloud.auth.api.context.LoginUser;
import com.biemo.cloud.system.core.util.CodeUtil;
import com.biemo.cloud.system.modular.dict.entity.SysDict;
import com.biemo.cloud.system.modular.dict.entity.SysDictType;
import com.biemo.cloud.system.modular.dict.service.DictService;
import com.biemo.cloud.system.modular.dict.service.DictTypeService;
import com.biemo.cloud.system.modular.ent.entity.*;
import com.biemo.cloud.system.modular.ent.mapper.EntUserMapper;
import com.biemo.cloud.system.modular.ent.model.params.*;
import com.biemo.cloud.system.modular.ent.model.result.EntUserQueryResult;
import com.biemo.cloud.system.modular.ent.model.result.EntUserResult;
import com.biemo.cloud.system.modular.ent.service.*;
import com.biemo.cloud.system.modular.ent.utils.EntUtil;
import com.biemo.cloud.system.modular.ent.utils.SaltUtil;
import com.biemo.cloud.system.modular.sys.entity.SysRole;
import com.biemo.cloud.system.modular.sys.entity.SysUserRole;
import com.biemo.cloud.system.modular.sys.service.SysRoleService;
import com.biemo.cloud.system.modular.sys.service.SysUserRoleService;
import com.biemo.cloud.core.page.PageFactory;
import com.biemo.cloud.core.util.ToolUtil;
import com.biemo.cloud.kernel.model.enums.DeleteFlagEnum;
import com.biemo.cloud.kernel.model.enums.StatusEnum;
import com.biemo.cloud.kernel.model.exception.RequestEmptyException;
import com.biemo.cloud.kernel.model.page.PageResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * ???????????? ???????????????
 * </p>
 *
 *
 * @since 2019-10-10
 */
@Service
public class EntUserServiceImpl extends ServiceImpl<EntUserMapper, EntUser> implements EntUserService {
    @Autowired
    private DictTypeService typeService;
    @Autowired
    private DictService dictService;
    @Autowired
    private EntUserDeptService userDeptService;
    @Autowired
    private EntUserAccountService userAccountService;
    @Autowired
    private EntCompanyService entCompanyService;
    @Autowired
    private EntDeptService entDeptService;
    @Autowired
    private EntDutyService entDutyService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    /**
     * ??????????????????
     *
     *
     * @date 2019/10/15
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(EntUserParam param) {
        if (ToolUtil.isEmpty(param.getPassword())) {
            throw new RequestEmptyException("password??????");
        }
        EntUser entity = getEntity(param);
        //????????????
        entity.setName(entity.getLastName() + entity.getFirstName());
        //????????????
        entity.setOrderNo(EntUtil.getMaxSort(EntUser.class));
        //????????????
        entity.setUserCode(CodeUtil.getUserCode(entity.getName(), entity.getSex(), entity.getBirthday()));
        //????????????
        entity.setStatus(StatusEnum.ENABLE.getCode());
        this.save(entity);
        //???????????????????????????????????????
        List<EntUserDeptParam> entUserDepts = param.getEntUserDepts();
        //??????????????????
        checkUserDept(entUserDepts);
        //????????????
        entUserDepts.forEach(ep -> {
            EntUserDept entUserDept = new EntUserDept();
            ToolUtil.copyProperties(ep, entUserDept);
            entUserDept.setUserId(entity.getUserId());
            userDeptService.save(entUserDept);
        });
        //?????????????????????????????????
        EntUserAccount userAccount = packageAccountInfo(param, entity);
        this.userAccountService.save(userAccount);
    }

    /**
     * ????????????????????????
     *
     *
     * @date 2019/10/27
     */
    private void checkUserDept(List<EntUserDeptParam> entUserDepts) {
        //??????????????????????????????
        List<String> defaultFlags = entUserDepts.stream()
                .map(EntUserDeptParam::getDefaultFlag)
                .collect(Collectors.toList());

        if (defaultFlags.contains(DeleteFlagEnum.Y.name())) {
            int count = Collections.frequency(defaultFlags, DeleteFlagEnum.Y.name());
            //?????????????????????
            if (count > 1) {
                throw new RequestEmptyException("??????????????????????????????");
            }
        } else {
            //???????????????
            throw new RequestEmptyException("????????????????????????");
        }
    }

    /**
     * ?????????????????????????????????
     *
     *
     * @date 2019/10/27
     */
    private EntUserAccount packageAccountInfo(EntUserParam param, EntUser entity) {
        EntUserAccount userAccount = new EntUserAccount();
        userAccount.setUserId(entity.getUserId());
        userAccount.setAccount(param.getAccount());
        String salt = SaltUtil.getRandomSalt();
        String password = SaltUtil.md5Encrypt(param.getPassword(), salt);
        userAccount.setPassword(password);
        userAccount.setSalt(salt);
        Long companyId = LoginContext.me().getLoginUser().getCompanyId();
        userAccount.setCompanyId(companyId);
        userAccount.setDelFlag(DeleteFlagEnum.N.name());
        return userAccount;
    }

    /**
     * ????????????????????????
     *
     *
     * @date 2019/10/27
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(EntUserParam param) {
        //???????????????????????????
        this.removeById(getKey(param));
        //?????????????????????????????????
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("user_id", getKey(param));
        this.userDeptService.removeByMap(paramMap);
        //???????????????????????????
        this.userAccountService.removeByMap(paramMap);
    }

    /**
     * ??????????????????
     *
     *
     * @date 2019/10/15
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(EntUserParam param) {
        EntUser oldEntity = getOldEntity(param);
        EntUser newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        oldEntity.setName(oldEntity.getLastName() + oldEntity.getFirstName());
        this.updateById(oldEntity);
        //????????????????????????
        this.userDeptService.remove(new QueryWrapper<EntUserDept>()
                .eq("user_id", oldEntity.getUserId()));
        List<EntUserDeptParam> entUserDepts = param.getEntUserDepts();
        //???????????????????????????
        checkUserDept(entUserDepts);
        //????????????
        entUserDepts.forEach(e -> {
            EntUserDept entUserDept = new EntUserDept();
            entUserDept.setUserId(oldEntity.getUserId());
            ToolUtil.copyProperties(e, entUserDept);
            this.userDeptService.save(entUserDept);
        });
    }

    @Override
    public PageResult<EntUserQueryResult> findPageBySpec(EntUserQueryParam param) {
        Page pageContext = getPageContext();
        IPage<EntUserQueryResult> page = this.baseMapper.customPageList(pageContext, param);
        return new PageResult<>(page);
    }

    /**
     * ???????????????????????????????????????
     *
     *
     * @date 2019/10/14
     */
    @Override
    public List<Map<String, Object>> queryInfoByDictType(String dictTypeCode) {
        SysDictType dictType = typeService.getOne(new QueryWrapper<SysDictType>()
                .eq("dict_type_code", dictTypeCode)
                .eq("status", StatusEnum.ENABLE.getCode()));
        if (ToolUtil.isEmpty(dictType)) {
            throw new RequestEmptyException("?????????????????????????????????????????????");
        }
        return dictService.listMaps(new QueryWrapper<SysDict>()
                .select("dict_id as dictId", "dict_name as dictName", "parent_id as parentId")
                .eq("dict_type_code", dictType.getDictTypeId())
                .eq("status", StatusEnum.ENABLE.getCode())
                .eq("del_flag", "N")
                .orderByAsc("dict_sort"));
    }

    /**
     * ??????????????????
     *
     *
     * @date 2019/10/15
     */
    @Override
    public EntUserResult detail(Long userId) {
        EntUserResult entUserResult = new EntUserResult();
        //????????????????????????
        EntUser entUser = this.getById(userId);
        ToolUtil.copyProperties(entUser, entUserResult);

        //????????????????????????
        List<EntUserDept> userDepts = this.userDeptService
                .list(new QueryWrapper<EntUserDept>().eq("user_id", userId));
        if (ToolUtil.isEmpty(userDepts)) {
            return entUserResult;
        }
        ArrayList<EntUserDeptParam> userDeptParams = new ArrayList<>();
        userDepts.forEach(entUserDept -> {
            EntUserDeptParam entUserDeptParam = new EntUserDeptParam();
            ToolUtil.copyProperties(entUserDept, entUserDeptParam);
            userDeptParams.add(entUserDeptParam);
        });
        entUserResult.setEntUserDepts(userDeptParams);

        //?????????????????????
        EntUserAccount entUserAccount = this.userAccountService
                .getOne(new QueryWrapper<EntUserAccount>()
                        .select("account").eq("user_id", userId));
        if (ToolUtil.isNotEmpty(entUserAccount)) {
            entUserResult.setAccount(entUserAccount.getAccount());
        }
        return entUserResult;
    }

    /**
     * ?????????????????????????????????
     *
     *
     * @date 2019/10/15
     */
    @Override
    public void changeStatus(Long userId, StatusEnum statusEnum) {
        if (ToolUtil.isEmpty(userId)) {
            throw new RequestEmptyException("userId??????");
        }
        if (statusEnum == null) {
            throw new RequestEmptyException("?????????????????????");
        }
        EntUser user = this.getById(userId);
        user.setStatus(statusEnum.getCode());
        this.updateById(user);
    }

    /**
     * ????????????????????????
     *
     *
     * @date 2019/10/27
     */
    @Override
    public List<Map<String, Object>> getCompanySelect() {
        QueryWrapper<EntCompany> companyWrapper = new QueryWrapper<>();
        companyWrapper = companyWrapper
                .select("name AS companyName,company_id AS companyId")
                .eq("status", StatusEnum.ENABLE.getCode())
                .orderByAsc("order_no");
        return entCompanyService.listMaps(companyWrapper);
    }

    /**
     * ????????????????????????
     *
     *
     * @date 2019/10/27
     */
    @Override
    public List<Map<String, Object>> getDeptSelect(Long companyId) {
        if (ToolUtil.isEmpty(companyId)) {
            throw new RequestEmptyException("??????id??????");
        }
        QueryWrapper<EntDept> deptWrapper = new QueryWrapper<>();
        deptWrapper = deptWrapper.select("dept_id AS deptId", "dept_name AS deptName")
                .eq("status", StatusEnum.ENABLE.getCode())
                .eq("company_id", companyId)
                .orderByAsc("order_no");
        return this.entDeptService.listMaps(deptWrapper);
    }

    /**
     * ????????????????????????
     *
     *
     * @date 2019/10/27
     */
    @Override
    public List<Map<String, Object>> getDutySelect() {
        QueryWrapper<EntDuty> dutyWrapper = new QueryWrapper<>();
        dutyWrapper = dutyWrapper
                .select("duty_id AS dutyId", "duty_name AS dutyName")
                .eq("status", StatusEnum.ENABLE.getCode());
        return this.entDutyService.listMaps(dutyWrapper);
    }

    /**
     * ???????????????????????????
     *
     * @param userId
     *
     * @date 2019/10/27
     */
    @Override
    public List<Map<String, Object>> getRoles(Long userId) {
        //??????????????????
        Set<Long> appIds = this.baseMapper.getCompanyIdsByUserId(userId);
        if (ToolUtil.isEmpty(appIds)) {
            throw new RequestEmptyException("??????????????????????????????????????????");
        }
        QueryWrapper<SysRole> roleWrapper = new QueryWrapper<>();
        roleWrapper = roleWrapper.select("role_id AS roleId",
                "role_name AS roleName")
                .in("app_id", appIds)
                .eq("status", StatusEnum.ENABLE.getCode())
                .orderByAsc("order_no");
        List<Map<String, Object>> allRoles = this.sysRoleService.listMaps(roleWrapper);
        //?????????????????????????????????
        List<Long> userRoles = this.baseMapper.getUserRoles(userId);

        for (Map<String, Object> map : allRoles) {
            if (ToolUtil.isEmpty(userRoles)) {
                map.put("configFlag", "N");
            }
            Long roleId = (Long) map.get("roleId");
            if (userRoles.contains(roleId)) {
                map.put("configFlag", "Y");
            } else {
                map.put("configFlag", "N");
            }
        }
        return allRoles;
    }

    /**
     * ?????????????????????????????????
     *
     *
     * @date 2019/10/28
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRoles(EntUserRoleParam param) {
        Long userId = param.getUserId();
        //????????????id
        Long accountId = this.baseMapper.getAccountIdByUserId(userId);
        if (ToolUtil.isEmpty(accountId)) {
            throw new RequestEmptyException("???????????????id");
        }
        Long[] roleIds = param.getRoleIds();
        for (Long roleId : roleIds) {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setAccountId(accountId);
            sysUserRole.setRoleId(roleId);
            this.sysUserRoleService.save(sysUserRole);
        }
    }

    /**
     * ???????????????????????????
     *
     * @return
     *
     * @date 2019/10/30
     */
    @Override
    public Map<String, Object> getCurrentUser() {
        LoginUser loginUser = LoginContext.me().getLoginUser();
        Long userId = loginUser.getUserId();
        //??????????????????
        EntUserAccount entUserAccount = this.userAccountService.getOne(new QueryWrapper<EntUserAccount>()
                .select("account").eq("user_id", userId));
        if (ToolUtil.isEmpty(entUserAccount)) {
            throw new RequestEmptyException("??????????????????????????????");
        }

        //????????????????????????
        Map<String, Object> userMap = this.getMap(new QueryWrapper<EntUser>()
                .select("user_id AS userId", "sex", "email", "last_name AS nickName","user_code AS userName",
                        "first_name AS firstName","create_time AS createTime", "birthday", "phone AS phonenumber", "avatar_url as avatar")
                .eq("user_id", userId));
        if (ToolUtil.isEmpty(userMap)) {
            throw new RequestEmptyException("???????????????????????????");
        }
        userMap.put("account", entUserAccount.getAccount());
        return userMap;
    }

    /**
     * ???????????????????????????
     *
     * @param
     *
     * @date 2019/10/30
     */
    @Override
    public void saveCurrentUser(EntCurrentParam param) {
        EntUser entUser = new EntUser();
        ToolUtil.copyProperties(param, entUser);
        this.updateById(entUser);
    }

    /**
     * ????????????
     *
     *
     * @date 2019/10/30
     */
    @Override
    public void updatePassword(EntPasswordParam passwordParam) {
        LoginUser loginUser = LoginContext.me().getLoginUser();
        Long userId = loginUser.getUserId();
        EntUserAccount entUserAccount = this.userAccountService.getOne(new QueryWrapper<EntUserAccount>()
                .eq("user_id", userId));
        if (ToolUtil.isEmpty(entUserAccount)) {
            throw new RequestEmptyException("????????????????????????????????????");
        }
        //??????????????????
        String password = entUserAccount.getPassword();
        String salt = entUserAccount.getSalt();
        String inputOldPassword = SaltUtil.md5Encrypt(passwordParam.getOldPassword(), salt);
        if (!inputOldPassword.equals(password)) {
            throw new RequestEmptyException("????????????????????????");
        }
        //???????????????
        String newSalt = SaltUtil.getRandomSalt();
        String newPassword = SaltUtil.md5Encrypt(passwordParam.getNewPassword(), newSalt);
        EntUserAccount newUserAccount = new EntUserAccount();
        newUserAccount.setAccountId(entUserAccount.getAccountId());
        newUserAccount.setSalt(newSalt);
        newUserAccount.setPassword(newPassword);
        this.userAccountService.updateById(newUserAccount);
    }

    /**
     * ????????????
     *
     *
     * @date 2019/10/30
     */
    @Override
    public String resetPassword(Long userId) {
        EntUserAccount userAccount = this.userAccountService
                .getOne(new QueryWrapper<EntUserAccount>()
                        .eq("user_id", userId));
        if (ToolUtil.isEmpty(userAccount)) {
            throw new RequestEmptyException("??????????????????????????????");
        }
        String passWord = ToolUtil.getRandomString(6);
        String salt = SaltUtil.getRandomSalt();
        String saltPassword = SaltUtil.md5Encrypt(passWord, salt);
        EntUserAccount entUserAccount = new EntUserAccount();
        entUserAccount.setAccountId(userAccount.getAccountId());
        entUserAccount.setSalt(salt);
        entUserAccount.setPassword(saltPassword);
        this.userAccountService.updateById(entUserAccount);
        return passWord;
    }

    private Serializable getKey(EntUserParam param) {
        return param.getUserId();
    }

    private Page getPageContext() {
        return PageFactory.defaultPage();
    }

    private EntUser getOldEntity(EntUserParam param) {
        return this.getById(getKey(param));
    }

    private EntUser getEntity(EntUserParam param) {
        EntUser entity = new EntUser();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
