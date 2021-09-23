package com.biemo.cloud.system.modular.sys.model;


import com.biemo.cloud.kernel.model.tree.Tree;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 主页显示的左侧菜单的节点
 *
 *
 * @date 2016年12月6日 上午11:34:17
 */
@Data
public class MenuNode implements Comparable, Tree, Serializable {

    private static final long serialVersionUID = -4757122115282773006L;

    /**
     * 节点id
     */
    private String id;

    /**
     * 父节点
     */
    private String parentId;

    /**
     * 节点名称
     */
    private String name;

    /**
     * 节点编码
     */
    private String code;

    /**
     * 按钮级别
     */
    private Integer levels;

    /**
     * 按钮的排序
     */
    private BigDecimal num;

    /**
     * 节点的url
     */
    private String path;

    /**
     * 节点图标
     */
    private String icon;

    /**
     * 是否隐藏
     */
    private Boolean hidden;

    /**
     * 菜单提示信息
     */
    private String menuTips;

    /**
     * 菜单提示信息
     */
    private String appId;


    private String component;

    private Map<String,Object> meta;

    private String redirect;

    private Boolean alwaysShow;
    /**
     * 子节点的集合
     */
    private List<MenuNode> children;

    /**
     * 查询子节点时候的临时集合
     */
    private List<MenuNode> linkedList = new ArrayList<>();

    public MenuNode() {
        super();
    }

    public MenuNode(String id, String parentId, String name, String code, BigDecimal num, String url,
                    String icon, Boolean hidden, String menuTips, String appId,String component) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.code = code;
        this.num = num;
        this.path = url;
        this.icon = icon;
        this.hidden = hidden;
        this.menuTips = menuTips;
        this.appId = appId;
        this.component = component;
        this.alwaysShow = false;
        this.meta = new HashMap<String,Object>(){
            {
                put("title",name);
                put("icon",icon);
                put("noCache",false);
                put("link",path);
            }
        };

    }

    @Override
    public int compareTo(Object o) {
        MenuNode menuNode = (MenuNode) o;
        BigDecimal num = menuNode.getNum();
        if (num == null) {
            num = BigDecimal.valueOf(0);
        }
        if (this.num == null) {
            this.num = BigDecimal.valueOf(0);
        }
        return this.num.compareTo(num);
    }

    @Override
    public String getNodeId() {
        return id;
    }

    @Override
    public String getNodeParentId() {
        return parentId;
    }

    @Override
    public void setChildrenNodes(List childrenNodes) {
        this.children = childrenNodes;
        if(this.children.size()>0){
            this.redirect = "noRedirect";
            this.alwaysShow = true;
        }
    }
}
