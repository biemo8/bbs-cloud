package com.biemo.cloud.system.modular.dict.model;

import com.biemo.cloud.kernel.model.tree.Tree;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 字典详细信息
 *
 *
 * @date 2018-07-25-上午10:55
 */
@Data
public class TreeDictInfo implements Tree {

    /**
     * 字典id
     */
    @ApiModelProperty("字典id")
    private Long dictId;

    /**
     * 字典编码
     */
    @ApiModelProperty("字典编码")
    private String dictCode;

    /**
     * 字典名称
     */
    @ApiModelProperty("字典名称")
    private String dictName;

    /**
     * 上级代码id
     */
    @ApiModelProperty("上级代码id")
    private Long parentId;

    /**
     * tree子节点
     */
    @ApiModelProperty("tree子节点")
    private List<TreeDictInfo> children;

    @Override
    public String getNodeId() {
        if (this.dictCode == null) {
            return null;
        } else {
            return this.dictCode.toString();
        }
    }

    @Override
    public String getNodeParentId() {
        if (this.parentId == null) {
            return null;
        } else {
            return this.parentId.toString();
        }
    }

    @Override
    public void setChildrenNodes(List linkedList) {
        this.children = linkedList;
    }

}
