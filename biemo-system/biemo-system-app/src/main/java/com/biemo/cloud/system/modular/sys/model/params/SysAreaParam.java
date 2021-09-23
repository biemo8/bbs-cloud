package com.biemo.cloud.system.modular.sys.model.params;

import lombok.Data;
import com.biemo.cloud.kernel.model.validator.BaseValidatingParam;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 基础字典
 * </p>
 *
 *
 * @since 2019-10-15
 */
@Data
@ApiModel
public class SysAreaParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /**
     * 区域id
     */
    @ApiModelProperty("区域id")
    private String areaId;

    /**
     * 区域编码
     */
    @ApiModelProperty("区域编码")
    private String areaCode;

    /**
     * 区域全称
     */
    @ApiModelProperty("区域全称")
    private String fullName;

    /**
     * 区域简称
     */
    @ApiModelProperty("区域简称")
    private String shortName;

    /**
     * 邮政编码
     */
    @ApiModelProperty("邮政编码")
    private String bincode;

    /**
     * 上级区域id
     */
    @ApiModelProperty("上级区域id")
    private String parentId;

    /**
     * 区域级别
     */
    @ApiModelProperty("区域级别")
    private String areaLevel;

    @Override
    public String checkParam() {
        return null;
    }

}
