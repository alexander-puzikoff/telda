package ru.puzikov.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * Created by SBT-Puzikov-AYU on 28.11.2016.
 */
@Configuration
@EnableCaching
@ComponentScan(basePackages = "ru.puzikov")
@Import(value = {WebSocketConfig.class, HibernateConfig.class})
public class MainConfig {

    @Bean(name = "cacheManager")
    public CacheManager getCacheManager() {
        EhCacheCacheManager bean = new EhCacheCacheManager();
        bean.setCacheManager(getEhCacheManagerFactoryBean().getObject());
        bean.afterPropertiesSet();
        return bean;
    }

    @Bean
    public EhCacheManagerFactoryBean getEhCacheManagerFactoryBean() {
        EhCacheManagerFactoryBean managerFactoryBean = new EhCacheManagerFactoryBean();
        managerFactoryBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
        managerFactoryBean.setShared(true);
        managerFactoryBean.afterPropertiesSet();
        return managerFactoryBean;
    }

    @Bean
    ViewResolver getViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setSuffix(".html");
        return resolver;
    }
}

