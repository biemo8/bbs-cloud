package com.biemo.cloud.system.modular.ent.model.result;

import lombok.Data;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 企业应用配置
 * </p>
 *
 *
 * @since 2019-10-15
 */
@Data
@ApiModel
public class EntCompAppResult implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * id
     */
    @ApiModelProperty("id")
    private Long id;

    /**
     * 公司id
     */
    @ApiModelProperty("公司id")
    private Long companyId;

    /**
     * 应用ID
     */
    @ApiModelProperty("应用ID")
    private Long appId;

}
