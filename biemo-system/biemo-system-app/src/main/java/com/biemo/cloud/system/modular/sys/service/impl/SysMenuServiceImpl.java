package com.biemo.cloud.system.modular.sys.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.biemo.cloud.auth.api.context.LoginContext;
import com.biemo.cloud.kernel.model.page.PageQuery;
import com.biemo.cloud.system.modular.res.enums.AppExceptionEnum;
import com.biemo.cloud.system.modular.sys.entity.SysApp;
import com.biemo.cloud.system.modular.sys.entity.SysMenu;
import com.biemo.cloud.system.modular.sys.enums.MenuExceptionEnum;
import com.biemo.cloud.system.modular.sys.mapper.SysMenuMapper;
import com.biemo.cloud.system.modular.sys.mapper.SysResourceMapper;
import com.biemo.cloud.system.modular.sys.model.MenuNode;
import com.biemo.cloud.system.modular.sys.model.TreeNode;
import com.biemo.cloud.system.modular.sys.model.params.MenuCondition;
import com.biemo.cloud.system.modular.sys.model.params.SysMenuParam;
import com.biemo.cloud.system.modular.sys.model.result.SysMenuResult;
import com.biemo.cloud.system.modular.sys.service.SysAppService;
import com.biemo.cloud.system.modular.sys.service.SysMenuService;
import com.biemo.cloud.system.modular.sys.service.SysRoleService;
import com.biemo.cloud.core.page.PageFactory;
import com.biemo.cloud.core.treebuild.DefaultTreeBuildFactory;
import com.biemo.cloud.core.util.ToolUtil;
import com.biemo.cloud.kernel.logger.util.LogUtil;
import com.biemo.cloud.kernel.model.enums.StatusEnum;
import com.biemo.cloud.kernel.model.enums.YesOrNotEnum;
import com.biemo.cloud.kernel.model.exception.RequestEmptyException;
import com.biemo.cloud.kernel.model.exception.ServiceException;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.*;

/**
 * <p>
 * 菜单 服务实现类
 * </p>
 *
 *
 * @since 2019-09-10
 */
@Service
@Slf4j
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    private SysAppService sysAppService;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysResourceMapper sysResourceMapper;

    @Override
    public void add(SysMenuParam param) {
        SysMenu entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(SysMenuParam param) {
        this.removeById(getKey(param));
    }

    @Override
    public void update(SysMenuParam param) {
        SysMenu oldEntity = getOldEntity(param);
        SysMenu newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public SysMenuResult findBySpec(SysMenuParam param) {
        return null;
    }

    @Override
    public List<SysMenuResult> findListBySpec(SysMenuParam param) {
        return null;
    }

    @Override
    public List<MenuNode> getLeftMenuList(Long appId, Long accountId) {
        if (log.isDebugEnabled()) {
            LogUtil.debug("开始获取左侧菜单---appId：" + appId + "---accountId:" + accountId);
        }

        //通过appId获取appCode
        SysApp app = this.sysAppService.getById(appId);
        if (app == null) {
            throw new ServiceException(AppExceptionEnum.APP_NOT_FOUND);
        }

        if (log.isDebugEnabled()) {
            LogUtil.debug("获取到appCode---appCode：" + app.getAppCode());
        }

        //获取用户的资源
        Set<String> userResourceUrls = sysRoleService.getUserResByAccountId(accountId);
        if (userResourceUrls == null || userResourceUrls.size() == 0) {
            return new ArrayList<>();
        }

        //获取资源ids
        List<Map<String, Object>> resourcesCodes = this.sysResourceMapper.getResourceLeftList(userResourceUrls,
                app.getAppCode());

        //用户指定应用的资源id
        List<String> resCodes = new ArrayList<>();
        for (Map resource : resourcesCodes) {
            resCodes.add((String) resource.get("code"));
        }

        if (log.isDebugEnabled()) {
            LogUtil.debug("获取到用户的资源---resourceIds：" + JSON.toJSONString(resCodes));
        }

        //获取资源对应的菜单
        List<SysMenu> menus = null;
        if (resCodes.isEmpty()) {
            menus = new ArrayList<>();
        } else {
            menus = this.list(
                    new QueryWrapper<SysMenu>()
                            .in("res_code", resCodes)
                            .and(i -> i.eq("status", StatusEnum.ENABLE.getCode()))
                            .orderByAsc("order_no"));
                            //.and(i -> i.eq("app_id", app.getAppId())).orderByAsc("order_no"));
        }

        if (log.isDebugEnabled()) {
            LogUtil.debug("获取资源对应的菜单---menus：" + JSON.toJSONString(menus));
        }

        //将菜单实体转化为左侧菜单节点类型
        List<MenuNode> menuNodes = conversionMenu(menus, appId);

        //构建树结构
        DefaultTreeBuildFactory<MenuNode> treeBuildFactory = new DefaultTreeBuildFactory<>();
        treeBuildFactory.setRootParentId("-1");
        List<MenuNode> result = treeBuildFactory.doTreeBuild(menuNodes);

        //对菜单排序
        Collections.sort(result);

        //对菜单的子菜单进行排序
        for (MenuNode menuNode : result) {
            if (menuNode.getChildren() != null && menuNode.getChildren().size() > 0) {
                Collections.sort(menuNode.getChildren());
            }
        }

        //如果子菜单都是隐藏菜单 那么将父菜单也隐藏
        hiddenParent(result);

        return result;
    }

    @Override
    public boolean haveSameMenu(SysMenu menu) {
        List<SysMenu> sameMenus = this.baseMapper.getSameMenu(menu);
        if (sameMenus != null && sameMenus.size() > 0) {
            for (SysMenu sameMenu : sameMenus) {

                //不同的父级id允许名字重复
                if (menu.getName().equals(sameMenu.getName())) {
                    if (menu.getPid() != null && (menu.getPid().equals(sameMenu.getPid()))) {
                        return true;
                    }
                }

                //编码不能重复
                if (menu.getCode().equals(sameMenu.getCode())) {
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }

    @Override
    public void addMenu(SysMenuParam sysMenuParam) {
        SysMenu menu = getEntity(sysMenuParam);

        //检查同应用是否存在同名或者同code的菜单
        List<SysMenu> sameMenus = this.baseMapper.getSameMenuWhenAdd(menu);
        if (sameMenus != null && sameMenus.size() > 0) {
            for (SysMenu sameMenu : sameMenus) {

                //不同的父级id允许名字重复
                if (menu.getName().equals(sameMenu.getName())) {
                    if (menu.getPid() != null && (menu.getPid().equals(sameMenu.getPid()))) {
                        throw new ServiceException(MenuExceptionEnum.DICT_TYPE_EMPTY);
                    }
                }

                //编码不能重复
                if (menu.getCode().equals(sameMenu.getCode())) {
                    throw new ServiceException(MenuExceptionEnum.DICT_TYPE_EMPTY);
                }
            }
        }

        menu.setStatus(StatusEnum.ENABLE.getCode());
        menu.setHiddenFlag(YesOrNotEnum.N.name());
        menu.setCreateTime(new Date());
        menu.setCreateUser(LoginContext.me().getAccountId());
        this.save(menu);

    }

    @Override
    public void updateMenu(SysMenuParam menuParam) {
        SysMenu menu = getEntity(menuParam);

        //获取旧菜单属性
        SysMenu oldMenu = this.getById(menu.getMenuId());
        if (oldMenu == null) {
            throw new ServiceException(MenuExceptionEnum.MENU_NOT_FOUND);
        }

        //检查同应用是否存在同名或者同code的菜单
        if (haveSameMenu(menu)) {
            throw new ServiceException(MenuExceptionEnum.DICT_TYPE_EMPTY);
        }

        BeanUtils.copyProperties(menu, oldMenu);
        oldMenu.setUpdateTime(new Date());
        oldMenu.setUpdateUser(LoginContext.me().getAccountId());
        this.updateById(oldMenu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMenu(Long menuId) {
        this.removeById(menuId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMenuBatch(Long[] menuIds) {
        for (Long menuId : menuIds) {
            deleteMenu(menuId);
        }
    }

    @Override
    public IPage<Map<String, Object>> getMenuList(MenuCondition menuCondition) {
        Page page = PageFactory.defaultPage();
        return this.baseMapper.customPageMapList(page, menuCondition);
    }

    @Override
    public List<TreeNode> getSelectMenuTreeList(Long appId) {
        QueryWrapper entityWrapper = new QueryWrapper<>();
        entityWrapper.select("MENU_ID AS id, NAME AS name, PID AS pId");
        entityWrapper.eq("APP_ID", appId);
        List<Map<String, Object>> list = this.baseMapper.selectMaps(entityWrapper);

        ArrayList<TreeNode> treeSelectNodes = new ArrayList<>();
        for (Map<String, Object> item : list) {
            TreeNode treeSelectNode = BeanUtil.mapToBean(item, TreeNode.class, true);
            String parentId = treeSelectNode.getPId();
            if (parentId == null || parentId.equals(TreeNode.ROOT_NODE_ID)) {

                //如果是父节点就展开它
                treeSelectNode.setOpen(true);
            }
            treeSelectNodes.add(treeSelectNode);
        }

        //添加根节点
        treeSelectNodes.add(TreeNode.createRoot());

        return treeSelectNodes;
    }

    @Override
    public void changeStatus(Long menuId, StatusEnum statusEnum) {
        if (statusEnum == null) {
            throw new RequestEmptyException("请求菜单状态不合法");
        }

        SysMenu menu = this.getById(menuId);
        menu.setStatus(statusEnum.getCode());
        this.updateById(menu);
    }

    /**
     * 根据应用id获取菜单集合
     *
     *
     * @date 2019/11/3
     */
    @Override
    public Set<String> getMenuResCodesByAppId(Long appId) {
        return this.baseMapper.getMenuResCodesByAppId(appId);
    }

    /**
     * 通过Menu构建MenuNode对象
     *
     *
     * @Date 2018/2/28 16:12
     */
    private MenuNode buildMenuNode(SysMenu menu) {

        return new MenuNode(
                String.valueOf(menu.getMenuId()), String.valueOf(menu.getPid()), menu.getName(), menu.getCode(),
                menu.getOrderNo(), menu.getUrl(), menu.getIcon(), "Y".equals(menu.getHiddenFlag())?true:false, menu.getMenuTips(),
                String.valueOf(menu.getAppId()),menu.getComponent());
    }

    /**
     * 将菜单实体转化为左侧菜单节点类型
     *
     *
     * @Date 2019/9/26 23:01
     */
    private List<MenuNode> conversionMenu(List<SysMenu> menus, Long appId) {

        List<MenuNode> menuNodes = new ArrayList<>();
        for (SysMenu menu : menus) {
            MenuNode menuNode = buildMenuNode(menu);

            //如果是一级菜单 level设置为1,如果不是一级菜单,需要循环找出他的层级,并设置level为2
            if (Long.valueOf(TreeNode.ROOT_NODE_ID).equals(menu.getPid())) {
                menuNode.setLevels(1);
                addMenuNode(menuNodes, menuNode);
            } else {

                //当前菜单不是1级菜单先加入到容器
                menuNode.setLevels(2);
                addMenuNode(menuNodes, menuNode);

                //查询菜单的上级,直到查询到这个节点所属的一级菜单为止
                Long pidParam = menu.getPid();
                while (!Long.valueOf(TreeNode.ROOT_NODE_ID).equals(pidParam)) {
                    SysMenu levelOneMenu = this.getById(pidParam);
                    if (levelOneMenu != null && levelOneMenu.getMenuId().equals(levelOneMenu.getPid())) {
                        break;
                    }

                    //如果有错误数据,则跳过本菜单
                    if (levelOneMenu == null) {
                        break;
                    }

                    //如果查询到的菜单的应用id和当前应用不匹配则不添加该菜单
//                    if (levelOneMenu.getAppId() != null && !levelOneMenu.getAppId().equals(appId)) {
//                        break;
//                    }

                    MenuNode buildedNode = buildMenuNode(levelOneMenu);
                    if (Long.valueOf(TreeNode.ROOT_NODE_ID).equals(levelOneMenu.getPid())) {
                        buildedNode.setLevels(1);
                    } else {
                        buildedNode.setLevels(2);
                    }
                    addMenuNode(menuNodes, buildedNode);
                    pidParam = levelOneMenu.getPid();
                }
            }
        }
        return menuNodes;
    }

    /**
     * 如果子菜单都是隐藏菜单 那么将父菜单也隐藏
     *
     *
     * @Date 2019/9/26 23:02
     */
    private void hiddenParent(List<MenuNode> menuNods) {

        if (menuNods == null || menuNods.size() == 0) {
            return;
        }

        for (MenuNode node : menuNods) {
            hiddenParent(node.getChildren());
            if (childrenIsAllHidden(node.getChildren())) {
                node.setHidden(true);
            }
        }

        return;
    }

    /**
     * 子菜单是否全部隐藏
     *
     *
     * @Date 2019/9/26 23:02
     */
    private boolean childrenIsAllHidden(List<MenuNode> menuNods) {
        if (menuNods == null || menuNods.size() == 0) {
            return false;
        }
        for (MenuNode node : menuNods) {
            if (!node.getHidden()) {
                return false;
            }
        }

        return true;
    }

    /**
     * 添加menuNode到容器里,添加之前会检查有没有重复的
     *
     *
     * @Date 2019/9/26 23:02
     */
    private void addMenuNode(List<MenuNode> menuNodes, MenuNode addItem) {

        for (MenuNode menuNode : menuNodes) {
            if (menuNode.getId().equals(addItem.getId())) {
                return;
            }
        }
        menuNodes.add(addItem);
    }

    private Serializable getKey(SysMenuParam param) {
        return param.getMenuId();
    }

    private Page getPageContext() {
        return PageFactory.defaultPage();
    }

    private SysMenu getOldEntity(SysMenuParam param) {
        return this.getById(getKey(param));
    }

    private SysMenu getEntity(SysMenuParam param) {
        SysMenu entity = new SysMenu();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
