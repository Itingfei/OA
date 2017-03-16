
package com.dongao.oa.config;
import com.dongao.oa.utils.RetryLimitHashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by fengjifei on 2016/8/2.
 */
@Configuration
public class ShiroConfiguration {

    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean(name = "hashedCredentialsMatcher")
    public RetryLimitHashedCredentialsMatcher hashedCredentialsMatcher() {
        RetryLimitHashedCredentialsMatcher credentialsMatcher = new RetryLimitHashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("MD5");
        credentialsMatcher.setHashIterations(2);
        credentialsMatcher.setStoredCredentialsHexEncoded(true);
        return credentialsMatcher;
    }

    @Bean(name = "shiroRealm")
    @DependsOn("lifecycleBeanPostProcessor")
    public ShiroRealm shiroRealm() {
        ShiroRealm realm = new ShiroRealm();
        realm.setCredentialsMatcher(hashedCredentialsMatcher());
        return realm;
    }

//    @Bean(name = "ehCacheManager")
//    @DependsOn("lifecycleBeanPostProcessor")
//    public EhCacheManager ehCacheManager(){
//        EhCacheManager ehCacheManager = new EhCacheManager();
//        return ehCacheManager;
//    }

    @Bean(name = "securityManager")
//    @DependsOn("lifecycleBeanPostProcessor")
    public SecurityManager securityManager(){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroRealm());
        securityManager.setCacheManager(null);
        securityManager.setSessionManager(DefaultWebSessionManager());
        return securityManager;
    }
    @Bean(name = "sessionManager")
    public SessionManager DefaultWebSessionManager(){
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setDeleteInvalidSessions(true);
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setGlobalSessionTimeout(1800000);//1800000 10000
        sessionManager.setSessionIdCookieEnabled(true);
        sessionManager.setSessionIdCookie(SimpleCookie());
        sessionManager.setSessionValidationInterval(600000);//600000
//        sessionManager.setSessionListeners(new SimpleShiroSessionListener());
        return sessionManager;
    }
    @Bean(name = "sessionIdCookie")
    public SimpleCookie SimpleCookie(){
        SimpleCookie sessionManager = new SimpleCookie("sid");
        sessionManager.setHttpOnly(true);
        sessionManager.setMaxAge(-1);//maxAge=-1表示浏览器关闭时失效此Cookie；
        return sessionManager;
    }
//<property name="globalSessionTimeout" value="${session.globalSessionTimeout}" /><!-- 默认是30分钟 -->
//		<property name="deleteInvalidSessions" value="true" />
//		<property name="sessionValidationSchedulerEnabled" value="true" /><!-- sessionValidationSchedulerEnabled(扫描session线程,负责清理超时会话) -->
//		<property name="sessionValidationScheduler" ref="sessionValidationScheduler" />
//		<property name="sessionDAO" ref="redisSessionDAO" />
//		<property name="sessionIdCookieEnabled" value="true" />
//		<property name="sessionIdCookie" ref="sessionIdCookie" />
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager());

        Map<String, Filter> filters = new LinkedHashMap<String, Filter>();
        LogoutFilter logoutFilter = new LogoutFilter();
        logoutFilter.setRedirectUrl("/login");
        filters.put("logout", logoutFilter);
//        filters.put("authc",getFormAuthenticationFilter());
        shiroFilterFactoryBean.setFilters(filters);

        Map<String, String> filterChainDefinitionManager = new LinkedHashMap<String, String>();
        //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
        filterChainDefinitionManager.put("/logout", "logout");
        filterChainDefinitionManager.put("/static/**", "anon");
        filterChainDefinitionManager.put("/templates/app/**", "anon");
        filterChainDefinitionManager.put("/appLogin", "anon");
        filterChainDefinitionManager.put("/app/modifyPass", "anon");
        filterChainDefinitionManager.put("/app/saveCategory", "anon");
        filterChainDefinitionManager.put("/app/classifyList", "anon");
        filterChainDefinitionManager.put("/app/index", "anon");
        filterChainDefinitionManager.put("/app/categoryDetail", "anon");
        filterChainDefinitionManager.put("/app/submitCateGoryTask", "anon");
        filterChainDefinitionManager.put("/app/searchCategory", "anon");
        filterChainDefinitionManager.put("/app/approveTask", "anon");
        filterChainDefinitionManager.put("/app/bookSortLetters", "anon");
        filterChainDefinitionManager.put("/app/bookDepartment", "anon");
        filterChainDefinitionManager.put("/app/userDetail", "anon");
        filterChainDefinitionManager.put("/app/searchDynamicState", "anon");
        filterChainDefinitionManager.put("/app/sendMessage", "anon");
        filterChainDefinitionManager.put("/druid2/login.html", "anon");
        //配置记住我或认证通过可以访问的地址
//        filterChainDefinitionManager.put("/index", "user");
//        filterChainDefinitionManager.put("/", "user");
        //<!-- 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
        //<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
        filterChainDefinitionManager.put("/**", "authc");

        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        // 登录成功后要跳转的链接
//        shiroFilterFactoryBean.setSuccessUrl("/index");
        //未授权界面;
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionManager);
        return shiroFilterFactoryBean;
    }

    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
        daap.setProxyTargetClass(true);
        return daap;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
        aasa.setSecurityManager(securityManager());
        return aasa;
    }
}
