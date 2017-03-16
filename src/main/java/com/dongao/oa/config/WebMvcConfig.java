/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.dongao.oa.config;

import com.dongao.oa.exception.GlobalExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.util.Properties;

/**
 * @author fengjifei
 * @since 2016-8-1 16:16
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/templates/app/**").addResourceLocations("classpath:/templates/app/");
    }
    @Bean
    public FreeMarkerConfigurer freeMarkerConfigurer() {
        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
        configurer.setTemplateLoaderPaths("classpath:/templates/","classpath:/templates/common/");
        configurer.setDefaultEncoding("UTF-8");
        Properties properties = new Properties();
        properties.put("classic_compatible","true");
        properties.put("number_format","#.##");
        properties.put("tag_syntax","auto_detect");
        properties.put("template_update_delay","0");
        configurer.setFreemarkerSettings(properties);
        return configurer;
    }
    @Bean
    public GlobalExceptionHandler globalExceptionHandler() {
        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
        Properties properties = new Properties();
        properties.setProperty("java.lang.Exception","/error/500");
        globalExceptionHandler.setExceptionMappings(properties);
        return globalExceptionHandler;
    }
}
//<prop key="template_update_delay">0</prop><!--模板刷新的时间，如果经常调试就用0 了 -->
//<prop key="tag_syntax">auto_detect</prop><!-- 设置标签类型 两种：[] 和 <> 。[] 这种标记解析要快些 -->
//<prop key="default_encoding">UTF-8</prop>
//<prop key="output_encoding">UTF-8</prop>
//<prop key="url_escaping_charset">UTF-8</prop>
//<prop key="whitespace_stripping">true</prop>
//<prop key="locale">zh_CN</prop>
//<prop key="boolean_format">true,false</prop>
//<prop key="date_format">yyyy-MM-dd</prop>
//<prop key="time_format">HH:mm:ss</prop>
//<prop key="datetime_format">yyyy-MM-dd HH:mm:ss</prop>
//<prop key="number_format">#.##</prop><!-- 设置数字格式 以免出现 000.00 -->
//<prop key="classic_compatible">true</prop><!-- 可以满足一般需要。默认情况变量为null则替换为空字符串，如果需要自定义，写上${empty!"EmptyValue of fbysss"}的形式即可 -->
//<prop key="template_exception_handler">html_debug</prop><!-- ignore,debug,html_debug,rethrow -->