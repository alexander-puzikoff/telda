package ru.puzikov.config;

import net.sf.ehcache.hibernate.EhCacheProvider;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.hibernate.SessionFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by SBT-Puzikov-AYU on 28.11.2016.
 */
@Configuration
@EnableCaching
@EnableTransactionManagement
@ComponentScan(basePackages = "ru.puzikov")
public class Config {

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

    @Bean(name = "dataSource")
    public DataSource configureDataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        EmbeddedDatabase db = builder
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("db/sql/create-db.sql")
                .setName("telda_db")
                .build();
        return db;
    }


    @Bean(name = "transactionManager")
    public HibernateTransactionManager getTransactionManager() throws IOException {
        HibernateTransactionManager manager = new HibernateTransactionManager(getSessionFactory());
        manager.afterPropertiesSet();
        return manager;
    }

    @Bean(name = "sessionFactory")
    public SessionFactory getSessionFactory() throws IOException {
        LocalSessionFactoryBean bean = new LocalSessionFactoryBean();
        bean.setAnnotatedClasses(ru.puzikov.common.Vehicle.class);
        bean.setHibernateProperties(getHibernateProps());
        bean.setDataSource(configureDataSource());
        bean.afterPropertiesSet();
        return bean.getObject();
    }

    @Bean
    ViewResolver getViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setSuffix(".html");
        return resolver;
    }

    private Properties getHibernateProps() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.c3p0.max_statements", "50");
        return hibernateProperties;
    }
}

