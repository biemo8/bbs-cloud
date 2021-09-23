package com.biemo.cloud.auth.modular.ssomgr.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.biemo.cloud.auth.modular.ssomgr.consumer.CustomServiceConsumer;
import com.biemo.cloud.auth.modular.ssomgr.service.ResCacheService;
import com.biemo.cloud.auth.modular.ssomgr.util.PageUtil;
import com.biemo.cloud.system.api.keys.SysCacheKey;
import com.biemo.cloud.core.util.ToolUtil;
import com.biemo.cloud.kernel.model.exception.RequestEmptyException;
import com.biemo.cloud.kernel.model.resource.ResourceDefinition;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 资源缓存服务类
 *
 *
 * @Date 2019/12/4 21:14
 */
@Service
public class ResCacheServiceImpl implements ResCacheService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired(required = false)
    private CustomServiceConsumer resServiceConsumer;

    /**
     * 获取资源缓存分页列表
     *
     *
     * @Date 2019/12/4 21:14
     */
    @Override
    public Page<Object> getResCacheList(String menuFlag) {
        boolean menu = menuFlag.equals("N");
        List<Object> resList = redisTemplate.opsForHash().values(SysCacheKey.RES_CACHE_MAP);
        //资源过滤
        List<Object> collect = resList.stream().filter(res -> {
            ResourceDefinition resourceDefinition = (ResourceDefinition) res;
            return resourceDefinition.getMenuFlag().equals(menu);
        }).collect(Collectors.toList());
        return PageUtil.createPageList(collect);
    }

    /**
     * 新增资源缓存
     *
     *
     * @Date 2019/12/4 21:14
     */
    @Override
    public void addResCache(ResourceDefinition resourceDefinition) {
        String cacheKey = resourceDefinition.getUrl();
        if (ToolUtil.isEmpty(cacheKey)) {
            throw new RequestEmptyException("资源的url为空");
        }
        redisTemplate.boundHashOps(SysCacheKey.RES_CACHE_MAP).put(cacheKey, resourceDefinition);

        //同步MySQL数据库
        try {
            resServiceConsumer.saveResource(resourceDefinition);
        } catch (Exception e) {
            e.printStackTrace();
            redisTemplate.boundHashOps(SysCacheKey.RES_CACHE_MAP).delete(cacheKey);
            throw new RequestEmptyException("资源保存数据库时出现异常");
        }
    }

    /**
     * 获取资源详情
     *
     *
     * @Date 2019/12/4 21:14
     */
    @Override
    public Object getDetail(String cacheKey) {
        return redisTemplate.opsForHash().get(SysCacheKey.RES_CACHE_MAP, cacheKey);
    }

    /**
     * 修改资源缓存
     *
     *
     * @Date 2019/12/4 21:14
     */
    @Override
    public void editResCache(ResourceDefinition resourceDefinition) {
        String cacheKey = resourceDefinition.getUrl();
        //缓存中的旧数据备份
        Object oldRes = redisTemplate.opsForHash().get(SysCacheKey.RES_CACHE_MAP, cacheKey);
        redisTemplate.boundHashOps(SysCacheKey.RES_CACHE_MAP)
                .put(cacheKey, resourceDefinition);
        //同步修改到MySQL数据库
        try {
            resServiceConsumer.editResource(resourceDefinition);
        } catch (Exception e) {
            //缓存数据回滚
            assert oldRes != null;
            redisTemplate.boundHashOps(SysCacheKey.RES_CACHE_MAP).put(cacheKey, oldRes);
            throw new RequestEmptyException("数据库资源更新时发生异常");
        }
    }

    /**
     * 删除资源缓存
     *
     *
     * @Date 2019/12/4 21:14
     */
    @Override
    public void removeResCache(String cacheKey) {
        Object oldRes = redisTemplate.opsForHash().get(SysCacheKey.RES_CACHE_MAP, cacheKey);
        redisTemplate.boundHashOps(SysCacheKey.RES_CACHE_MAP).delete(cacheKey);
        //同步删除MySQL数据库
        try {
            this.resServiceConsumer.removeResource(cacheKey);
        } catch (Exception e) {
            //缓存数据回滚
            assert oldRes != null;
            redisTemplate.boundHashOps(SysCacheKey.RES_CACHE_MAP).put(cacheKey, oldRes);
            throw new RequestEmptyException("删除数据库中的缓存时发生异常");
        }
    }

    /**
     * 获取应用下拉列表
     *
     *
     * @Date 2019/12/4 21:14
     */
    @Override
    public List<Map<String, Object>> getAppSelect() {
        return this.resServiceConsumer.getAppSelect();
    }

    /**
     * 获取资源所属模块下拉列表
     *
     *
     * @Date 2019/12/4 21:14
     */
    @Override
    public List<Map<String, Object>> getResModuleSelect() {
        return this.resServiceConsumer.getResModuleSelect();
    }

    @Override
    public List<String> getHttpRequestSelect() {
        return CollUtil
                .toList("GET", "HEAD", "POST", "PUT",
                        "DELETE", "CONNECT", "OPTIONS", "TRACE");
    }
}
