package com.biemo.cloud.auth.modular.ssomgr.controller;

import cn.hutool.core.util.NumberUtil;
import com.biemo.cloud.core.util.ToolUtil;
import com.biemo.cloud.kernel.model.response.ResponseData;
import com.biemo.cloud.kernel.scanner.modular.annotation.GetResource;
import com.biemo.cloud.kernel.scanner.modular.stereotype.ApiResource;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.biemo.cloud.auth.modular.ssomgr.constants.GodKeyConst.*;


/**
 * 万能密码控制器
 *
 *
 * @Date 2019/12/7 18:12
 */
@RestController
@Api(tags = "万能密码")
@ApiResource(name = "万能密码", path = "/godKeyManager")
public class GodKeyManagerController {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 获取万能密码的值和状态
     *
     *
     * @Date 2019/12/7 18:13
     */
    @ApiOperation(value = "获取万能密码的值和状态")
    @GetResource(name = "获取万能密码的值和状态", path = "/getGodKey")
    public ResponseData getGodKey() {
        Map<Object, Object> godKeyMap = redisTemplate.opsForHash().entries(GOD_KEY_TICKET);
        Long expireTime = redisTemplate.getExpire(GOD_KEY_TICKET, TimeUnit.SECONDS);
        if (ToolUtil.isNotEmpty(expireTime)) {
            Long diff = expireTime - System.currentTimeMillis();
            BigDecimal secondDiff = NumberUtil.div(new BigDecimal(diff), new BigDecimal(1000), 2);
            godKeyMap.put(EXPIRE_TIME, secondDiff);
        }
        return ResponseData.success(godKeyMap);
    }

    /**
     * 启用禁用万能密码
     *
     *
     * @Date 2019/12/7 18:13
     */
    @ApiOperation(value = "启用禁用万能密码")
    @GetResource(name = "启用禁用万能密码", path = "/checkedStatus")
    public ResponseData checkedStatus() {
        Map<Object, Object> godKeyMap = redisTemplate.opsForHash().entries(GOD_KEY_TICKET);
        String status = (String) godKeyMap.get(GOD_KEY_STATUS);
        godKeyMap.put(GOD_KEY_STATUS, ENABLE_GOD_KEY.equals(status) ? DISABLED_GOD_KEY : ENABLE_GOD_KEY);
        redisTemplate.opsForHash().putAll(GOD_KEY_TICKET, godKeyMap);
        return ResponseData.success();
    }
}
