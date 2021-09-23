package com.biemo.cloud.libs.sentinel;

import com.alibaba.csp.sentinel.adapter.servlet.callback.UrlCleaner;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.math.NumberUtils;

import java.util.Arrays;

/**
 * 针对restful风格的url进行限流过滤
 * <p>
 * 例如：原接口为/user/1  /user/2    /user/3
 * <p>
 * 此类接口不应设置设置三个限流规则，应该设置一个规则即可
 *
 *
 * @Date 2019/9/1 16:52
 */
@Slf4j
public class RestfulUrlCleaner implements UrlCleaner {
    @Override
    public String clean(String originUrl) {
        // 让 /user/1 与 /user/2 的返回值相同
        // 返回/user/{number}

        String[] split = originUrl.split("/");
        return Arrays.stream(split)
                .map(string -> {
                    if (NumberUtils.isNumber(string)) {
                        return "{number}";
                    }
                    return string;
                })
                .reduce((a, b) -> a + "/" + b)
                .orElse("");
    }
}
