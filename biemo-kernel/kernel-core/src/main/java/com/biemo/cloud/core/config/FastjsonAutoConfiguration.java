/**
 * Copyright  2018-2020 &   (admin@makesoft.cn)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.biemo.cloud.core.config;

import com.biemo.cloud.core.converter.CustomFastJsonHttpMessageConverter;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * fastjson配置类
 *
 *
 * @date 2017-05-23 22:56
 */
@Configuration("defaultFastjsonConfig")
@ConditionalOnClass(com.alibaba.fastjson.JSON.class)
@ConditionalOnMissingBean(FastJsonHttpMessageConverter.class)
@ConditionalOnWebApplication
public class FastjsonAutoConfiguration {

    @Bean
    public CustomFastJsonHttpMessageConverter customFastJsonHttpMessageConverter() {
        CustomFastJsonHttpMessageConverter converter = new CustomFastJsonHttpMessageConverter();
        converter.setFastJsonConfig(fastjsonConfig());
        converter.setSupportedMediaTypes(getSupportedMediaType());
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        return converter;
    }

    /**
     * fastjson的配置
     */
    public FastJsonConfig fastjsonConfig() {
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.PrettyFormat,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.BrowserCompatible
        );
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
        fastJsonConfig.setCharset(Charset.forName("utf-8"));
        //initOtherValueFilters(fastJsonConfig);
        return fastJsonConfig;
    }

    /**
     * 支持的mediaType类型
     */
    public List<MediaType> getSupportedMediaType() {
        ArrayList<MediaType> mediaTypes = new ArrayList<>();

        mediaTypes.add(MediaType.APPLICATION_JSON);
        mediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
//        mediaTypes.add(MediaType.APPLICATION_ATOM_XML);
//        mediaTypes.add(MediaType.APPLICATION_FORM_URLENCODED);
//        mediaTypes.add(MediaType.APPLICATION_OCTET_STREAM);
//        mediaTypes.add(MediaType.APPLICATION_PDF);
//        mediaTypes.add(MediaType.APPLICATION_RSS_XML);
//        mediaTypes.add(MediaType.APPLICATION_XHTML_XML);
//        mediaTypes.add(MediaType.APPLICATION_XML);
//        mediaTypes.add(MediaType.IMAGE_GIF);
//        mediaTypes.add(MediaType.IMAGE_JPEG);
//        mediaTypes.add(MediaType.IMAGE_PNG);
//        mediaTypes.add(MediaType.TEXT_EVENT_STREAM);
//        mediaTypes.add(MediaType.TEXT_HTML);
//        mediaTypes.add(MediaType.TEXT_MARKDOWN);
//        mediaTypes.add(MediaType.TEXT_PLAIN);
//        mediaTypes.add(MediaType.TEXT_XML);

        //增加解析spring boot actuator结果的解析
        mediaTypes.add(MediaType.valueOf("application/vnd.spring-boot.actuator.v2+json"));

        return mediaTypes;
    }

    /**
     * 初始化value过滤器
     * <p>
     * 默认的valueFilter是把空的字段转化为空串
     */
    protected void initOtherValueFilters(FastJsonConfig fastJsonConfig) {

        //为空的值转化为空串
        ValueFilter nullValueFilter = (object, name, value) -> {
            if (null == value) {
                return "";
            } else {
                return value;
            }
        };

        fastJsonConfig.setSerializeFilters(nullValueFilter);
    }

}
