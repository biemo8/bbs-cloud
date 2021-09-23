package com.biemo.cloud.auth.modular.sso.util;

import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;

/**
 * RSA非对称加密
 *
 *
 * @Date 2019/9/25 22:23
 */
public class RSAUtil {

    /**
     * 私钥
     */
    private static final String PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAO3fQBw7wJIB/NJrkvyKRWt96EfpoXD0rIvzk3M4B245+c9xuIAYuMtYQo6Q6GRiuVIB9Tmz6lrc5BOyCl8KRVWbnp0RCHyxqMhHi7IZuG/T8J289NlLmjew/piL1N6ZZnjjf5A2NqM3oxIJOZNjzpmhH2OZNwm1XiQ1NBYOi/qpAgMBAAECgYAt/RgZfUnlUKIPEoI39gmm1xBV+urd+kfzxNNQltgE7QuJmjxn85+inL09b/GmGtomaz98ePbJu2QTFLxhlIxDueUNuZWp+daDfftV/gaCKJU0sZ/t22OtwegpT3NwcAFx8c74A0k3Sera34R+adUL/ALnQXff6gbMPjw0LU/LCQJBAPpzGlimfeuM2x/F/QwAceGkqMtKyR7kUqN6iux4Bdq8e2wrZwlLYmDsQwDnQ8vujlrFunFXB9hFPmF/nbf1RyUCQQDzJMpNOx7mGl0txsdSOMVsO6qdim78zh3qmBoJLaGWfWuQoF2KR2dyWITaxfuJTGoDGiSOioZYkn4Yy4Zrn0A1AkAS+kHW0dbc0HGSOMhx7l6ywG1h6irPQnHYnWKFhJq89mdLzQYjuCieO4jgOqXygAMdcbos+HFJvAV8EdV7qQRZAkEAl4ChHaZtBmv/C3sEz9LYESzMjwWnSpcURQ0pi/aG7UKRN7j1ECiV6MYHYlpi9ZZLqEst5DOkqqP9Nm37ACxm0QJACfN90x3r+wFR+MJ9BS5yXUNcfRRpcPBOD7D4QrzOMkWUh3QkuaQZSESpud7UUt0PAfFfTFLx4XQyeY9CqdrYTg==";

    /**
     * 公钥
     */
    private static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDt30AcO8CSAfzSa5L8ikVrfehH6aFw9KyL85NzOAduOfnPcbiAGLjLWEKOkOhkYrlSAfU5s+pa3OQTsgpfCkVVm56dEQh8sajIR4uyGbhv0/CdvPTZS5o3sP6Yi9TemWZ443+QNjajN6MSCTmTY86ZoR9jmTcJtV4kNTQWDov6qQIDAQAB";

    /**
     * 加密明文通过公钥
     *
     *
     * @Date 2019/9/25 22:22
     */
    public static String encrypt(String data) {
        RSA rsa = new RSA(PRIVATE_KEY, PUBLIC_KEY);
        return rsa.encryptBase64(data, KeyType.PublicKey);
    }

    /**
     * 解密密文通过私钥
     *
     *
     * @Date 2019/9/25 22:22
     */
    public static String decrypt(String data) {
        RSA rsa = new RSA(PRIVATE_KEY, PUBLIC_KEY);
        return rsa.decryptStr(data, KeyType.PrivateKey);
    }

    public static void main(String[] args){
        String encrypt = RSAUtil.encrypt("111111");
        System.out.println(encrypt);
    }

}
