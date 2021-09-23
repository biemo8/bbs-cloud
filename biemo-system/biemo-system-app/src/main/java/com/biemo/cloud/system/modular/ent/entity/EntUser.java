package com.biemo.cloud.system.modular.ent.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 用户信息
 * </p>
 *
 *
 * @since 2019-10-15
 */
@TableName("biemo_ent_user")
@Data
public class EntUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户信息id
     */
    @TableId(value = "user_id", type = IdType.ASSIGN_ID)
    private Long userId;

    /**
     * 姓名
     */
    @TableField("name")
    private String name;

    /**
     * 用户编码
     */
    @TableField("user_code")
    private String userCode;

    /**
     * 姓
     */
    @TableField("last_name")
    private String lastName;

    /**
     * 名
     */
    @TableField("first_name")
    private String firstName;

    /**
     * 姓（拼音）
     */
    @TableField("last_name_pinyin")
    private String lastNamePinyin;

    /**
     * 名（拼音）
     */
    @TableField("first_name_pinyin")
    private String firstNamePinyin;

    /**
     * 性别(M:男   F:女)
     */
    @TableField("sex")
    private String sex;

    /**
     * 生日
     */
    @TableField("birthday")
    private Date birthday;

    /**
     * 学历（字典项）
     */
    @TableField("education")
    private String education;

    /**
     * 籍贯
     */
    @TableField("birthplace")
    private String birthplace;

    /**
     * 民族
     */
    @TableField("nation")
    private String nation;

    /**
     * 身份证号
     */
    @TableField("id_card")
    private String idCard;

    /**
     * 婚姻状况（字典项）
     */
    @TableField("matrimony")
    private String matrimony;

    /**
     * 政治面貌（字典项）
     */
    @TableField("political")
    private String political;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 电话
     */
    @TableField("phone")
    private String phone;

    /**
     * 手机号
     */
    @TableField("mobile_phone")
    private String mobilePhone;

    /**
     * 用户头像
     */
    @TableField("avatar_url")
    private String avatarUrl;


    /**
     * 排序码
     */
    @TableField("order_no")
    private BigDecimal orderNo;

    /**
     * 状态(1:启用  2:禁用)
     */
    @TableField("status")
    private Integer status;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 创建人
     */
    @TableField(value = "create_user", fill = FieldFill.INSERT)
    private Long createUser;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private Date updateTime;

    /**
     * 更新人
     */
    @TableField(value = "update_user", fill = FieldFill.UPDATE)
    private Long updateUser;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastNamePinyin() {
        return lastNamePinyin;
    }

    public void setLastNamePinyin(String lastNamePinyin) {
        this.lastNamePinyin = lastNamePinyin;
    }

    public String getFirstNamePinyin() {
        return firstNamePinyin;
    }

    public void setFirstNamePinyin(String firstNamePinyin) {
        this.firstNamePinyin = firstNamePinyin;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getBirthplace() {
        return birthplace;
    }

    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getMatrimony() {
        return matrimony;
    }

    public void setMatrimony(String matrimony) {
        this.matrimony = matrimony;
    }

    public String getPolitical() {
        return political;
    }

    public void setPolitical(String political) {
        this.political = political;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public BigDecimal getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(BigDecimal orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    @Override
    public String toString() {
        return "EntUser{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", userCode='" + userCode + '\'' +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastNamePinyin='" + lastNamePinyin + '\'' +
                ", firstNamePinyin='" + firstNamePinyin + '\'' +
                ", sex='" + sex + '\'' +
                ", birthday=" + birthday +
                ", education='" + education + '\'' +
                ", birthplace='" + birthplace + '\'' +
                ", nation='" + nation + '\'' +
                ", idCard='" + idCard + '\'' +
                ", matrimony='" + matrimony + '\'' +
                ", political='" + political + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", orderNo=" + orderNo +
                ", status=" + status +
                ", createTime=" + createTime +
                ", createUser=" + createUser +
                ", updateTime=" + updateTime +
                ", updateUser=" + updateUser +
                '}';
    }
}
