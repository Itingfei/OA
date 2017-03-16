package com.dongao.oa.config;

/**
 * Created by fengjifei on 2016/8/5.
 */


import com.github.pagehelper.PageHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * {@link EnableAutoConfiguration Auto-Configuration} for Mybatis. Contributes a * {@link SqlSessionFactory} and a {@link SqlSessionTemplate}. * * If {@link org.mybatis.spring.annotation.MapperScan} is used, or a configuration file is * specified as a property, those will be considered, otherwise this auto-configuration * will attempt to register mappers based on the interface definitions in or under the * root auto-configuration package. * * @author Eddú Meléndez * @author Josh Long
 */
@Configuration
@EnableTransactionManagement
@EnableConfigurationProperties(MybatisProperties.class)
//@AutoConfigureAfter(DataSourceAutoConfiguration.class)
public class MybatisAutoConfiguration implements TransactionManagementConfigurer {

    private static Log log = LogFactory.getLog(MybatisAutoConfiguration.class);

    @Autowired
    private MybatisProperties properties;
    @Autowired
    DataSource dataSource;

    @Autowired
    private ResourceLoader resourceLoader = new DefaultResourceLoader();

    @PostConstruct
    public void checkConfigFileExists() {
        if (this.properties.isCheckConfigLocation()) {
            Resource resource = this.resourceLoader
                    .getResource(this.properties.getConfig());
            Assert.state(resource.exists(),
                    "Cannot find config location: " + resource
                            + " (please add config file or check your Mybatis "
                            + "configuration)");
        }
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        factory.setTypeAliasesPackage(this.properties.getTypeAliasesPackage());
        factory.setPlugins(new Interceptor[]{pageHelper()});
//        factory.setMapperLocations(this.properties.getMapperLocations());
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            factory.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
            return factory.getObject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    @Bean
    @ConditionalOnMissingBean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory,
                this.properties.getExecutorType());
    }

    /**
     * 分页插件 * * @param dataSource * @return * @author SHANHY * @create 2016年2月18日
     */
    @Bean
    public PageHelper pageHelper() {
        log.info("注册MyBatis分页插件PageHelper");
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("reasonable", "true");
        properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("returnPageInfo", "check");
        properties.setProperty("params", "count=countSql");
        pageHelper.setProperties(properties);
        return pageHelper;
    }
    @Bean
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager(){
        return new DataSourceTransactionManager(dataSource);
    }
}
