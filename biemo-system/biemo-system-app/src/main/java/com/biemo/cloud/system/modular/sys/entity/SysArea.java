package com.biemo.cloud.system.modular.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 基础字典
 * </p>
 *
 *
 * @since 2019-10-15
 */
@TableName("biemo_sys_area")
public class SysArea implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 区域id
     */
    @TableId(value = "area_id", type = IdType.ASSIGN_ID)
    private String areaId;

    /**
     * 区域编码
     */
    @TableField("area_code")
    private String areaCode;

    /**
     * 区域全称
     */
    @TableField("full_name")
    private String fullName;

    /**
     * 区域简称
     */
    @TableField("short_name")
    private String shortName;

    /**
     * 邮政编码
     */
    @TableField("bincode")
    private String bincode;

    /**
     * 上级区域id
     */
    @TableField("parent_id")
    private String parentId;

    /**
     * 区域级别
     */
    @TableField("area_level")
    private String areaLevel;


    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getBincode() {
        return bincode;
    }

    public void setBincode(String bincode) {
        this.bincode = bincode;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getAreaLevel() {
        return areaLevel;
    }

    public void setAreaLevel(String areaLevel) {
        this.areaLevel = areaLevel;
    }

    @Override
    public String toString() {
        return "SysArea{" +
        "areaId=" + areaId +
        ", areaCode=" + areaCode +
        ", fullName=" + fullName +
        ", shortName=" + shortName +
        ", bincode=" + bincode +
        ", parentId=" + parentId +
        ", areaLevel=" + areaLevel +
        "}";
    }
}
