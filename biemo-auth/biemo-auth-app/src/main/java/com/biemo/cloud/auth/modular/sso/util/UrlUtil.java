package com.biemo.cloud.auth.modular.sso.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Url工具类
 *
 *
 * @Date 2018/9/27 上午10:47
 */
public final class UrlUtil {

    private static final Logger log = LoggerFactory.getLogger(UrlUtil.class);
    private static final String charset = "utf-8";

    public static String encode(String url) {
        try {
            return URLEncoder.encode(url, charset);
        } catch (UnsupportedEncodingException e) {
            log.error("Url编码错误", e);
            throw new RuntimeException("Url编码错误", e);

        }
    }

    public static String decode(String url) {
        try {
            return URLEncoder.encode(url, charset);
        } catch (UnsupportedEncodingException e) {
            log.error("Url解码错误", e);
            throw new RuntimeException("Url解码错误", e);

        }
    }
}
