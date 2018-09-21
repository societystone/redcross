package com.app.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

/**
 * json-自定义消息转换器配置 . <br>
 * 
 * @author wangtw <br>
 */
@Configuration
public class JsonConfig extends WebMvcConfigurerAdapter {

    /**
     * 修改自定义消息转换器
     * 
     * @param converters
     *            消息转换器列表
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 调用父类的配置
        super.configureMessageConverters(converters);
        // 创建fastjson消息转换器
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        // 创建配置类
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        /*
         * WriteNullStringAsEmpty-字符类型字段如果为null,输出为"",而非null
         * 修改配置返回内容的过滤-序列化分别对应: 输出值为null的字段、 消除对同一对象循环引用的问题、全局修改日期格式yyyy-MM-dd
         * hh:mm:ss
         */
        fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteMapNullValue,
                SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteDateUseDateFormat);
        fastConverter.setFastJsonConfig(fastJsonConfig);
        // 将fastjson添加到视图消息转换器列表内
        converters.add(fastConverter);
    }

}
