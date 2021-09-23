package com.biemo.cloud.system.modular.ent.model.result;

import com.biemo.cloud.system.modular.ent.model.params.EntUserDeptParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.kafka.common.protocol.types.Field;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 用户信息
 * </p>
 *
 *
 * @since 2019-10-15
 */
@Data
@ApiModel
public class EntUserResult implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 用户信息id
     */
    @ApiModelProperty("用户信息id")
    private Long userId;

    /**
     * 姓名
     */
    @ApiModelProperty("姓名")
    private String name;

    /**
     * 用户编码
     */
    @ApiModelProperty("用户编码")
    private String userCode;

    /**
     * 姓
     */
    @ApiModelProperty("姓")
    private String lastName;

    /**
     * 名
     */
    @ApiModelProperty("名")
    private String firstName;

    /**
     * 姓（拼音）
     */
    @ApiModelProperty("姓（拼音）")
    private String lastNamePinyin;

    /**
     * 名（拼音）
     */
    @ApiModelProperty("名（拼音）")
    private String firstNamePinyin;

    /**
     * 性别(M:男   F:女)
     */
    @ApiModelProperty("性别(M:男   F:女)")
    private String sex;

    /**
     * 生日
     */
    @ApiModelProperty("生日")
    private Date birthday;

    /**
     * 学历（字典项）
     */
    @ApiModelProperty("学历（字典项）")
    private String education;

    /**
     * 籍贯
     */
    @ApiModelProperty("籍贯")
    private String birthplace;

    /**
     * 民族
     */
    @ApiModelProperty("民族")
    private String nation;

    /**
     * 身份证号
     */
    @ApiModelProperty("身份证号")
    private String idCard;

    /**
     * 婚姻状况（字典项）
     */
    @ApiModelProperty("婚姻状况（字典项）")
    private String matrimony;

    /**
     * 政治面貌（字典项）
     */
    @ApiModelProperty("政治面貌（字典项）")
    private String political;

    /**
     * 邮箱
     */
    @ApiModelProperty("邮箱")
    private String email;

    /**
     * 电话
     */
    @ApiModelProperty("电话")
    private String phone;

    /**
     * 手机号
     */
    @ApiModelProperty("手机号")
    private String mobilePhone;

    /**
     * 排序码
     */
    @ApiModelProperty("排序码")
    private BigDecimal orderNo;

    /**
     * 状态(1:启用  2:禁用)
     */
    @ApiModelProperty("状态(1:启用  2:禁用)")
    private Integer status;

    /**
     * 账号
     */
    @ApiModelProperty("账号")
    private String account;

    /**
     * 用户所属部门和职务的集合
     */
    @ApiModelProperty("用户所属部门和职务的集合")
    private List<EntUserDeptParam> entUserDepts;
}
