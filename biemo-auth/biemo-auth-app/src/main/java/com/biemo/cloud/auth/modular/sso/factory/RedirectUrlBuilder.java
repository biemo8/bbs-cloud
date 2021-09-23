package com.biemo.cloud.auth.modular.sso.factory;

import com.biemo.cloud.auth.modular.sso.util.UrlUtil;
import com.alibaba.csp.sentinel.util.StringUtil;

import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * 重定向URl生成工具类
 *
 *
 * @Date 2019/9/25 22:28
 */
public final class RedirectUrlBuilder {
    private static final String REDIRECT = "redirect:";
    private String clientSsoUrl;

    private Map<String, String> params;

    public RedirectUrlBuilder(String clientSsoUrl) {
        this.clientSsoUrl = clientSsoUrl;
        this.params = new TreeMap<>();
    }

    public static RedirectUrlBuilder create(String clientSsoUrl) {
        assert clientSsoUrl != null;
        return new RedirectUrlBuilder(clientSsoUrl);
    }

    /**
     * 添加参数
     *
     * @param key   键
     * @param value 值
     *
     * @Date 2019/12/4 21:48
     */
    public RedirectUrlBuilder add(String key, String value) {
        this.params.put(key, value);
        return this;
    }

    /**
     * 添加参数集合
     *
     *
     * @Date 2019/12/4 21:48
     */
    public RedirectUrlBuilder addAll(Map<String, String> params) {
        this.params.putAll(params);
        return this;
    }

    /**
     * 生成重定向url
     *
     *
     * @Date 2019/12/4 21:49
     */
    public String build() {
        StringBuffer sb = new StringBuffer(REDIRECT);
        sb.append(clientSsoUrl);
        String symbol = clientSsoUrl.contains("?") ? "&" : "?";
        sb.append(symbol);
        String sortedStr = params
                .entrySet()
                .stream()
                .filter(entry -> !StringUtil.isEmpty(entry.getValue()))
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> entry.getKey() + "=" + UrlUtil.encode(entry.getValue()))
                .collect(Collectors.joining("&"));
        sb.append(sortedStr);
        return sb.toString();
    }
}
