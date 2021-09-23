package com.biemo.cloud.system.modular.ent.model.params;

import lombok.Data;
import com.biemo.cloud.kernel.model.validator.BaseValidatingParam;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 企业信息
 * </p>
 *
 *
 * @since 2019-10-15
 */
@Data
@ApiModel
public class EntCompanyParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /**
     * 公司id
     */
    @ApiModelProperty("公司id")
    private Long companyId;

    /**
     * 公司名称
     */
    @ApiModelProperty("公司名称")
    private String name;

    /**
     * 公司简称
     */
    @ApiModelProperty("公司简称")
    private String shortName;

    /**
     * 公司编码
     */
    @ApiModelProperty("公司编码")
    private String cpCode;

    /**
     * 上级id
     */
    @ApiModelProperty("上级id")
    private Long parentId;

    /**
     * 上级id的集合
     */
    @ApiModelProperty("上级id的集合")
    private String parentIds;

    /**
     * 公司门户地址
     */
    @ApiModelProperty("公司门户地址")
    private String url;

    /**
     * 排序
     */
    @ApiModelProperty("排序")
    private BigDecimal orderNo;

    /**
     * 公司简介
     */
    @ApiModelProperty("公司简介")
    private String description;

    /**
     * 状态（1启用，2禁用）
     */
    @ApiModelProperty("状态（1启用，2禁用）")
    private Integer status;

    /**
     * 创建人
     */
    @ApiModelProperty(hidden = true)
    private Long createUser;

    /**
     * 创建时间
     */
    @ApiModelProperty(hidden = true)
    private Date createTime;

    /**
     * 更新人
     */
    @ApiModelProperty(hidden = true)
    private Long updateUser;

    /**
     * 更新时间
     */
    @ApiModelProperty(hidden = true)
    private Date updateTime;

    @Override
    public String checkParam() {
        return null;
    }

}
