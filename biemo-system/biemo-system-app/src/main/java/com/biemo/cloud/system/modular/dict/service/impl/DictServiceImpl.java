package com.biemo.cloud.system.modular.dict.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.StrUtil;
import com.biemo.cloud.system.modular.dict.entity.SysDict;
import com.biemo.cloud.system.modular.dict.enums.DictExceptionEnum;
import com.biemo.cloud.system.modular.dict.mapper.DictMapper;
import com.biemo.cloud.system.modular.dict.model.DictInfo;
import com.biemo.cloud.system.modular.dict.model.TreeDictInfo;
import com.biemo.cloud.system.modular.dict.service.DictService;
import com.biemo.cloud.core.page.PageFactory;
import com.biemo.cloud.core.treebuild.DefaultTreeBuildFactory;
import com.biemo.cloud.core.util.ToolUtil;
import com.biemo.cloud.kernel.model.enums.StatusEnum;
import com.biemo.cloud.kernel.model.exception.RequestEmptyException;
import com.biemo.cloud.kernel.model.exception.ServiceException;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 基础字典 服务实现类
 * </p>
 *
 *
 * @since 2018-07-24
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, SysDict> implements DictService {

    @Override
    public void addDict(SysDict dict) {
        if (ToolUtil.isOneEmpty(dict, dict.getDictCode(), dict.getDictName())) {
            throw new RequestEmptyException();
        }

        dict.setDictId(null);

        if (ToolUtil.isEmpty(dict.getParentId())) {
            dict.setParentId(-1L); // 默认的根节点
        } else {
            // 判断字典的父id是否存在
            SysDict sysDict = this.getById(dict.getParentId());
            if (sysDict == null) {
                throw new ServiceException(DictExceptionEnum.PARENT_NOT_EXISTED);
            }
        }
        dict.setDelFlag("N");
        dict.setStatus(StatusEnum.ENABLE.getCode());
        dict.setDictId(IdWorker.getId());
        this.baseMapper.insert(dict);
    }

    @Override
    public void updateDict(SysDict dict) {
        if (ToolUtil.isOneEmpty(dict, dict.getDictId(), dict.getDictCode(), dict.getDictName())) {
            throw new RequestEmptyException();
        }

        // 不能修改类型
        dict.setDictTypeCode(null);

        SysDict oldDict = this.baseMapper.selectById(dict.getDictId());
        if (oldDict == null) {
            throw new ServiceException(DictExceptionEnum.NOT_EXISTED);
        }

        // 判断编码是否重复
        Wrapper<SysDict> wrapper = new QueryWrapper<SysDict>()
                .eq("DICT_CODE", dict.getDictCode())
                .ne("DICT_ID", oldDict.getDictId());
        int dicts = this.baseMapper.selectCount(wrapper);
        if (dicts > 0) {
            throw new ServiceException(DictExceptionEnum.REPEAT_DICT_TYPE);
        }

        BeanUtil.copyProperties(dict, oldDict, CopyOptions.create().setIgnoreNullValue(true));
        this.updateById(oldDict);
    }

    @Override
    public void deleteDict(String dictId) {
        if (ToolUtil.isEmpty(dictId)) {
            throw new RequestEmptyException();
        }

        this.baseMapper.deleteById(dictId);
    }

    @Override
    public void updateDictStatus(String dictId, Integer status) {
        if (ToolUtil.isOneEmpty(dictId, status)) {
            throw new RequestEmptyException();
        }

        // 检查状态是否合法
        if (StatusEnum.toEnum(status) == null) {
            throw new ServiceException(DictExceptionEnum.WRONG_STATUS);
        }

        SysDict dict = this.baseMapper.selectById(dictId);
        dict.setStatus(status);
        this.updateById(dict);
    }

    @Override
    public List<DictInfo> getDictList(DictInfo dictInfo) {

        if (dictInfo == null) {
            dictInfo = new DictInfo();
        }

        return baseMapper.getDictList(dictInfo);
    }

    @Override
    public Page<DictInfo> getDictPage(DictInfo dictInfo) {

        Page<DictInfo> page = PageFactory.defaultPage();

        if (dictInfo == null) {
            dictInfo = new DictInfo();
        }
        List<DictInfo> dictList = baseMapper.getDictList(page, dictInfo);
        if (ToolUtil.isEmpty(dictList)) {
            return page;
        }
        String rootId = "-1";
        for (DictInfo dict :dictList) {
            String parentId = dict.getParentId();
            if (rootId.equals(parentId)) {
                dict.setParentName("顶级");
                continue;
            }
            SysDict parentDict = this.getById(parentId);
            dict.setParentName(parentDict.getDictName());
        }
        page.setRecords(dictList);

        return page;
    }

    @Override
    public List<SysDict> getDictListByTypeCode(String dictTypeCode) {

        if (ToolUtil.isEmpty(dictTypeCode)) {
            throw new RequestEmptyException("字典type编码为空！");
        }

        return this.baseMapper.selectList(new QueryWrapper<SysDict>().eq("DICT_TYPE_CODE", dictTypeCode));
    }

    @Override
    public List<TreeDictInfo> getTreeDictList(String dictTypeCode) {
        if (ToolUtil.isEmpty(dictTypeCode)) {
            throw new RequestEmptyException();
        }

        // 获取对应字典类型的所有字典列表
        Wrapper<SysDict> wrapper = new QueryWrapper<SysDict>().eq("DICT_TYPE_CODE", dictTypeCode);
        List<SysDict> dicts = this.baseMapper.selectList(wrapper);
        if (dicts == null || dicts.isEmpty()) {
            return new ArrayList<>();
        }

        ArrayList<TreeDictInfo> treeDictInfos = new ArrayList<>();
        for (SysDict dict : dicts) {
            TreeDictInfo treeDictInfo = new TreeDictInfo();
            treeDictInfo.setDictId(dict.getDictId());
            treeDictInfo.setDictCode(dict.getDictCode());
            treeDictInfo.setParentId(dict.getParentId());
            treeDictInfo.setDictName(dict.getDictName());
            treeDictInfos.add(treeDictInfo);
        }

        // 构建菜单树
        return new DefaultTreeBuildFactory<TreeDictInfo>().doTreeBuild(treeDictInfos);
    }

    @Override
    public List<DictInfo> getDictListByTypeCodeAndPid(String dictTypeCode, String parentCode) {
        if (ToolUtil.isEmpty(dictTypeCode)) {
            throw new RequestEmptyException("字典type编码为空！");
        }

        Map<String, Object> map = new HashMap<>();
        map.put("dictTypeCode", dictTypeCode);
        map.put("parentCode", parentCode);
        return this.baseMapper.getDictListByTypeCodeAndPid(map);
    }

    @Override
    public boolean checkCode(String dictId, String dictCode) {
        QueryWrapper<SysDict> wrapper = new QueryWrapper<SysDict>();
        wrapper.eq("DICT_CODE", dictCode);
        if (!StrUtil.isEmpty(dictId)) {
            wrapper.ne("DICT_ID", dictId);
        }
        Integer selectCount = this.baseMapper.selectCount(wrapper);
        if (selectCount <= 0) {
            return true;
        }
        return false;
    }

    @Override
    public List<DictInfo> getDictListByTypeCodeAndNotPids(String dictTypeCode, List<String> parentIds) {
        if (ToolUtil.isEmpty(dictTypeCode)) {
            throw new RequestEmptyException("字典type编码为空！");
        }

        Map<String, Object> map = new HashMap<>();
        map.put("dictTypeCode", dictTypeCode);
        map.put("parentIds", parentIds);
        return this.baseMapper.getDictListByTypeCodeAndNotPid(map);
    }

    @Override
    public String translateCode(String dictCode) {
        if (ToolUtil.isEmpty(dictCode)) {
            return "";
        }
        List<String> dictNames = this.baseMapper.getDictName(dictCode);
        if (ToolUtil.isEmpty(dictNames)) {
            return "";
        }
        return dictNames.get(0);
    }

    @Override
    public String getDictCode(String dictTypeCode, String dictName) {
        if (ToolUtil.isEmpty(dictTypeCode) || ToolUtil.isEmpty(dictName)) {
            return "";
        }
        List<String> dictNames = this.baseMapper.getDictCode(dictTypeCode, dictName);
        if (ToolUtil.isEmpty(dictNames)) {
            return "";
        }
        return dictNames.get(0);
    }

    @Override
    public String idsToNames(String ids) {

        StringBuilder sb = new StringBuilder();
        if (ToolUtil.isEmpty(ids)) {
            return sb.append("无").toString();
        }

        List<String> idList = null;
        if (ids.startsWith("[") && ids.endsWith("]")) {
            idList = JSONArray.parseArray(ids, String.class);
        } else if (!ids.startsWith("[") && !ids.endsWith("]")) {
            String[] split = ids.split(",");
            idList = Arrays.asList(split);
        }
        if (ToolUtil.isEmpty(idList)) {
            return "无";
        }
        List<SysDict> dicts = this.baseMapper.selectBatchIds(idList);
        if (ToolUtil.isEmpty(dicts)) {
            return sb.append("无").toString();
        }
        for (SysDict dict : dicts) {
            sb.append(",").append(dict.getDictName());
        }

        return sb.deleteCharAt(0).toString();
    }
}
