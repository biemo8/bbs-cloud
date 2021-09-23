package com.biemo.cloud.system.api.model.req;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

/**
 * 获取用户列表接口的请求
 *
 *
 * @Date 2019/12/1 17:47
 */
@Data
public class UserByRoleReq {

    Long roleId;

    Page page;
}
