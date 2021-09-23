package com.biemo.cloud.auth.api.constants;

/**
 * 鉴权相关常量
 *
 *
 * @Date 2019/5/14 22:54
 */
public interface AuthConstants {

    /**
     * 鉴权请求头名称
     */
    String AUTH_HEADER = "Authorization";

    /**
     * 管理员角色的名字
     */
    String ADMIN_NAME = "administrator";

    /**
     * 缓存token的前缀
     */
    String TOKEN_PREFIX = "BIEMO_AUTH_TOKEN_";

}
