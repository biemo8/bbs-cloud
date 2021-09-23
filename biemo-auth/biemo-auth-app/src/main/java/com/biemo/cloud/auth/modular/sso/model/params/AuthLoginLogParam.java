package com.biemo.cloud.auth.modular.sso.model.params;

import com.biemo.cloud.kernel.model.validator.BaseValidatingParam;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 登录日志表
 * </p>
 *
 *
 * @since 2019-09-27
 */
@Data
public class AuthLoginLogParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */
    private Long loginLogId;

    /**
     * 账号
     */
    private String account;

    /**
     * 姓名
     */
    private String name;

    /**
     * 公司id
     */
    private Long companyId;

    /**
     * 应用客户端id
     */
    private Long clientId;

    /**
     * 操作内容
     */
    private String operation;

    /**
     * 登录ip
     */
    private String ipAddress;

    /**
     * 登录地点
     */
    private String localAddress;

    /**
     * 登陆时间
     */
    private Date loginTime;

    @Override
    public String checkParam() {
        return null;
    }

}
