package com.dongao.oa;

import com.dongao.oa.config.SystemSettings;
import com.github.pagehelper.PageInfo;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
/*指定mybatis扫描的包路径 不指定会把所有interface当做mapper注入数据库连接
而且使用通用mapper不能被扫描到 否则会出错*/
@MapperScan(basePackages = {"com.dongao.oa.dao"})
@EnableConfigurationProperties(SystemSettings.class)
@EnableCaching//启用缓存，这个注解很重要；
public class Application{
	   public static final Logger logger = LoggerFactory.getLogger(Application.class);

	    public static void main(String[] args) {
	        SpringApplication.run(Application.class, args);
	        logger.info("Springboot Application [springboot-demo-controller] started!");
	    }
}
