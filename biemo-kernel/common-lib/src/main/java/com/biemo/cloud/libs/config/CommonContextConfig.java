package com.biemo.cloud.libs.config;

import cn.hutool.core.date.DateUtil;
import com.biemo.cloud.core.config.FastjsonAutoConfiguration;
import com.biemo.cloud.libs.error.SystemErrorAttributes;
import com.biemo.cloud.libs.mock.VirtualLoginUserAop;
import com.biemo.cloud.core.aop.RequestDataAop;
import com.biemo.cloud.core.converter.CustomFastJsonHttpMessageConverter;
import com.biemo.cloud.core.converter.RequestDataMessageConvert;
import com.biemo.cloud.core.page.PageFieldNamesContainer;
import com.biemo.cloud.core.util.MvcAdapter;
import com.biemo.cloud.core.util.ToolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.lang.Nullable;
import org.springframework.validation.Validator;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.Date;
import java.util.HashSet;

/**
 * 默认web配置
 *
 *
 * @Date 2018/7/24 下午3:27
 */
@Configuration
@Import(value = {ChainConfig.class, SentinelConfig.class, VirtualLoginUserAop.class, MetadataFillConfig.class, LoginContextConfig.class, FastjsonAutoConfiguration.class})
public class CommonContextConfig extends WebMvcConfigurationSupport {

    @Autowired
    private RequestMappingHandlerAdapter handlerAdapter;


    /**
     * 静态资源映射
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/assets/");

        //工作流的静态资源映射
        registry.addResourceHandler("/activiti-editor/**").addResourceLocations("classpath:/activiti-editor/");

        //swagger的静态资源映射
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");

        //swagger增强的静态资源映射
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");

        String uploadPath = System.getProperty("os.name").toLowerCase().startsWith("win") ?"file:D:/data/upload_file_root/cms/":"file:/data/upload_file_root/cms/";
        registry.addResourceHandler("/upload/**").addResourceLocations(uploadPath);
        registry.addResourceHandler("/Uploads/**").addResourceLocations(uploadPath+"Uploads/");
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        String staticPath = "/data/upload_file_root/cms/html/";
        registry.addResourceHandler("/static/html/**").addResourceLocations("file:"+staticPath);

    }

    /**
     * 错误信息提示重写
     *
     *
     * @Date 2019/5/31 21:27
     */
    @Bean
    public SystemErrorAttributes systemErrorAttributes() {
        return new SystemErrorAttributes();
    }

    /**
     * 控制器层临时缓存RequestData的aop
     */
    @Bean
    public RequestDataAop requestDataAop() {
        return new RequestDataAop();
    }

    /**
     * RequestData解析器
     */
    @Bean
    public RequestDataMessageConvert requestDataMessageConvert() {
        return new RequestDataMessageConvert();
    }

    /**
     * RequestData解析器，fastjson的converter
     */
    @Bean
    public RequestMappingHandlerAdapter requestMappingHandlerAdapter(CustomFastJsonHttpMessageConverter customFastJsonHttpMessageConverter,
                                                                     RequestDataMessageConvert requestDataMessageConvert, @Qualifier("mvcContentNegotiationManager") ContentNegotiationManager contentNegotiationManager, @Qualifier("mvcConversionService") FormattingConversionService conversionService, @Qualifier("mvcValidator") Validator validator) {

        return MvcAdapter.requestMappingHandlerAdapter(
                super.requestMappingHandlerAdapter(contentNegotiationManager,conversionService,validator), customFastJsonHttpMessageConverter, requestDataMessageConvert);
    }

//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        //1、定义一个convert转换消息的对象
//        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
//        //2、添加fastjson的配置信息
//        FastJsonConfig fastJsonConfig = new FastJsonConfig();
//        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
//        fastJsonConfig.setCharset(Charset.forName("utf-8"));
//        fastJsonConfig.setSerializerFeatures(
//                SerializerFeature.PrettyFormat,
//                SerializerFeature.WriteMapNullValue,
//                SerializerFeature.DisableCircularReferenceDetect,
//                SerializerFeature.BrowserCompatible
//        );
//        initOtherValueFilters(fastJsonConfig);
//
//        //3、在convert中添加配置信息
//        fastConverter.setFastJsonConfig(fastJsonConfig);
//        fastConverter.setSupportedMediaTypes(getSupportedMediaType());
//        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
//
//        //4、将convert添加到converters中
//        converters.add(fastConverter);
//        //5、追加默认转换器
//        super.addDefaultHttpMessageConverters(converters);
//    }


    /**
     * 适配时间转化器
     */
    @PostConstruct
    public void addConversionConfig() {
        ConfigurableWebBindingInitializer initializer = (ConfigurableWebBindingInitializer) handlerAdapter.getWebBindingInitializer();
        if ((initializer != null ? initializer.getConversionService() : null) != null) {
            GenericConversionService genericConversionService = (GenericConversionService) initializer.getConversionService();
            genericConversionService.addConverter(new StringToDateConverter());
        }
    }

    /**
     * String类型日期转化
     *
     *
     * @Date 2019/12/8 19:04
     */
    public static class StringToDateConverter implements Converter<String, Date> {
        @Override
        public Date convert(@Nullable String dateString) {
            if (ToolUtil.isEmpty(dateString)) {
                return null;
            } else {
                return DateUtil.parse(dateString);
            }
        }
    }

    /**
     * 项目的分页参数适配
     *
     *
     * @Date 2019/12/8 19:04
     */
    @Bean
    public Object object() {

        HashSet<String> pageNoFields = new HashSet<>();
        pageNoFields.add("current");
        pageNoFields.add("page");

        HashSet<String> pageSizeFields = new HashSet<>();
        pageSizeFields.add("size");

        PageFieldNamesContainer.getInstance().initPageNoFieldNames(pageNoFields);
        PageFieldNamesContainer.getInstance().initPageSizeFieldNames(pageSizeFields);

        return new Object();
    }

}
