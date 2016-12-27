package ru.puzikov.config;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by APuzikov on 27.12.2016.
 */
@Configuration
@EnableTransactionManagement
public class HibernateConfig {
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

    private Properties getHibernateProps() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.c3p0.max_statements", "50");
        return hibernateProperties;
    }
}
