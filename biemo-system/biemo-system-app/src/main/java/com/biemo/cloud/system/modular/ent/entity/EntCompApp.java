package com.biemo.cloud.system.modular.ent.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 企业应用配置
 * </p>
 *
 *
 * @since 2019-10-15
 */
@TableName("biemo_ent_comp_app")
public class EntCompApp implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 公司id
     */
    @TableField("company_id")
    private Long companyId;

    /**
     * 应用ID
     */
    @TableField("app_id")
    private Long appId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    @Override
    public String toString() {
        return "EntCompApp{" +
        "id=" + id +
        ", companyId=" + companyId +
        ", appId=" + appId +
        "}";
    }
}
