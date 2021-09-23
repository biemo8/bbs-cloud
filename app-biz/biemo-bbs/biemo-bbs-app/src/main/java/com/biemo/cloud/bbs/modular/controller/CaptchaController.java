package com.biemo.cloud.bbs.modular.controller;


import cn.hutool.core.codec.Base64;
import com.biemo.cloud.bbs.api.BCaptchaApi;
import com.biemo.cloud.core.constant.Constants;
import com.biemo.cloud.core.util.redis.RedisCache;
import com.biemo.cloud.core.util.uuid.IdUtils;
import com.biemo.cloud.kernel.model.response.ResponseData;
import com.google.code.kaptcha.Producer;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 验证码操作处理
 *
 */
@RestController
@RequestMapping("/captcha")
public class CaptchaController implements BCaptchaApi
{
    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    @Resource
    private RedisCache redisCache;

    // 验证码类型 math char
    private String captchaType = "char";

    /**
     * 生成验证码
     */
    @Override
    public ResponseData getCode(HttpServletResponse response) throws IOException
    {
        // 保存验证码信息
        String uuid = IdUtils.simpleUUID();
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;

        String capStr = null, code = null;
        BufferedImage image = null;

        // 生成验证码
        if ("math".equals(captchaType))
        {
            String capText = captchaProducerMath.createText();
            capStr = capText.substring(0, capText.lastIndexOf("@"));
            code = capText.substring(capText.lastIndexOf("@") + 1);
            image = captchaProducerMath.createImage(capStr);
        }
        else if ("char".equals(captchaType))
        {
            capStr = code = captchaProducer.createText();
            image = captchaProducer.createImage(capStr);
        }

        redisCache.setCacheObject(verifyKey,code,Constants.CAPTCHA_EXPIRATION,TimeUnit.MINUTES);

        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try
        {
            ImageIO.write(image, "jpg", os);
        }
        catch (IOException e)
        {
            return ResponseData.error(e.getMessage());
        }
        Map<String,Object> result = new HashMap<>();
        result.put("uuid", uuid);
        result.put("img", Base64.encode(os.toByteArray()));
        return ResponseData.success(result);
    }
}
