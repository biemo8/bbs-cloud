package com.biemo.cloud.system.api.model.req;

import lombok.Data;

import java.util.Set;

/**
 * 获取用户列表接口的请求
 *
 *
 * @Date 2019/12/1 17:47
 */
@Data
public class UserListReq {

    Set<Long> accountIds;

    Set<Long> roleIds;
}
