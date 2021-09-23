package com.biemo.cloud.core.constant;

/**
 * 通用常量信息
 *
 */
public class Constants
{
    /**
     * UTF-8 字符集
     */
    public static final String UTF8 = "UTF-8";

    /**
     * GBK 字符集
     */
    public static final String GBK = "GBK";

    /**
     * http请求
     */
    public static final String HTTP = "http://";

    /**
     * https请求
     */
    public static final String HTTPS = "https://";

    /**
     * 通用成功标识
     */
    public static final String SUCCESS = "0";

    /**
     * 通用失败标识
     */
    public static final String FAIL = "1";

    /**
     * 登录成功
     */
    public static final String LOGIN_SUCCESS = "Success";

    /**
     * 注销
     */
    public static final String LOGOUT = "Logout";

    /**
     * 登录失败
     */
    public static final String LOGIN_FAIL = "Error";

    /**
     * 验证码 redis key
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";

    /**
     * 登录用户 redis key
     */
    public static final String LOGIN_TOKEN_KEY = "login_tokens:";

    /**
     * 防重提交 redis key
     */
    public static final String REPEAT_SUBMIT_KEY = "repeat_submit:";

    /**
     * 验证码有效期（分钟）
     */
    public static final Integer CAPTCHA_EXPIRATION = 2;

    /**
     * 令牌
     */
    public static final String TOKEN = "token";

    /**
     * 令牌前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";


    /**
     * 请求头令牌
     */
    public static final String AUTH_HEADER = "Authorization";


    /**
     * 令牌前缀
     */
    public static final String LOGIN_USER_KEY = "login_user_key";

    /**
     * 用户ID
     */
    public static final String JWT_USERID = "userid";

    /**
     * 用户名称
     */
    public static final String JWT_USERNAME = "sub";

    /**
     * 用户头像
     */
    public static final String JWT_AVATAR = "avatar";

    /**
     * 创建时间
     */
    public static final String JWT_CREATED = "created";

    /**
     * 用户权限
     */
    public static final String JWT_AUTHORITIES = "authorities";

    /**
     * 参数管理 cache key
     */
    public static final String SYS_CONFIG_KEY = "sys_config:";

    /**
     * 字典管理 cache key
     */
    public static final String SYS_DICT_KEY = "sys_dict:";

    /**
     * 本体管理 cache key
     */
    public static final String ONTOLOGY_CFG = "ontology_cfg:";

    /**
     * 描述词管理 cache key
     */
    public static final String DESCRIPTOR = "descriptor:";

    /**
     * 描述词管理 cache key
     */
    public static final String ATTRIBUTE_CFG = "attributeCfg:";

    /**
     * 关系类型配置管理 cache key
     */
    public static final String RELATIONSHIP_CFG = "relationship_cfg:";

    /**
     * 省边界坐标 cache key
     */
    public static final String PROVINCE_BORDER = "province_border:";

    /**
     * 资源映射路径 前缀
     */
    public static final String RESOURCE_PREFIX = "/profile";

    /**
     * RMI 远程方法调用
     */
    public static final String LOOKUP_RMI = "rmi://";

    /**
     * 空
     */
    public static final String EMPTY = "";

    /**
     * 空格
     */
    public static final String BLANK = "";

    /**
     * 正斜杠
     */
    public static final String FORWARD_SLASH = "/";

    /**
     * 双正斜杠
     */
    public static final String DOUBLE_FORWARD_SLASH = "//";

    /**
     * 文件导入int类型默认值
     */
    public static final String IMPORT_FILE_DEFAULT_INT = "-1";
}
