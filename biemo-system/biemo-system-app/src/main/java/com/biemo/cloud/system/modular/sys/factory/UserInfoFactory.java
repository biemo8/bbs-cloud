package com.biemo.cloud.system.modular.sys.factory;

import com.biemo.cloud.system.api.model.BaseUserInfo;
import com.biemo.cloud.system.modular.ent.entity.EntUserAccount;
import com.biemo.cloud.system.modular.ent.entity.EntUserDept;
import com.biemo.cloud.system.modular.ent.entity.EntUser;
import com.biemo.cloud.system.modular.sys.model.params.RoleInfo;

import java.util.*;

/**
 * 用户创建工厂
 *
 *
 * @date 2019-05-29-14:37
 */
public class UserInfoFactory {

    /**
     * 创建用户基本信息
     *
     *
     * @Date 2019-09-11 18:37
     */
    public static BaseUserInfo createUserInfo(EntUserAccount accountInfo,
                                              EntUser userInfo,
                                              EntUserDept entUserDeptDuty,
                                              List<RoleInfo> userRoleInfo,
                                              Set<String> resourcesUrls) {

        BaseUserInfo baseUserInfo = new BaseUserInfo();

        //基础信息
        baseUserInfo.setAccountId(accountInfo.getAccountId());
        baseUserInfo.setName(userInfo.getName());
        baseUserInfo.setIdCard(userInfo.getIdCard());
        baseUserInfo.setCompanyId(accountInfo.getCompanyId());
        baseUserInfo.setUserId(accountInfo.getUserId());
        baseUserInfo.setAccount(accountInfo.getAccount());
        baseUserInfo.setPassword(accountInfo.getPassword());
        baseUserInfo.setSalt(accountInfo.getSalt());
        baseUserInfo.setAvatar(userInfo.getAvatarUrl());


        //设置资源
        if(resourcesUrls!=null&&resourcesUrls.size()>0){
            baseUserInfo.setResourceUrls(resourcesUrls);
        }else{
            resourcesUrls.add("---");
            baseUserInfo.setResourceUrls(resourcesUrls);
        }

        //设置默认部门
        if (entUserDeptDuty != null) {
            baseUserInfo.setDeptId(entUserDeptDuty.getDeptId());
        }

        //设置角色信息
        if (userRoleInfo != null && userRoleInfo.size() > 0) {

            HashSet<Long> roleIds = new HashSet<>();
            HashSet<String> roleNames = new HashSet<>();

            for (RoleInfo roleInfo : userRoleInfo) {
                roleIds.add(roleInfo.getRoleId());
                roleNames.add(roleInfo.getRoleName());
            }

            baseUserInfo.setRoleIds(roleIds);
            baseUserInfo.setRoleNames(roleNames);
        }else{
            HashSet<Long> roleIds = new HashSet<>();
            HashSet<String> roleNames = new HashSet<>();
            roleIds.add(0L);
            roleNames.add("null");
            baseUserInfo.setRoleIds(roleIds);
            baseUserInfo.setRoleNames(roleNames);
        }

        return baseUserInfo;
    }

}
