package ru.puzikov.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * Created by SBT-Puzikov-AYU on 28.11.2016.
 */
@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "ru.puzikov")
public class Config {

    @Bean(name = "dataSource")
    public DataSource configureDataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        EmbeddedDatabase db = builder
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("db/sql/create-db.sql")
                .addScript("db/sql/create-data-db.sql")
                .setName("telda_db")
                .build();
        return db;
    }


    @Bean(name  = "transactionManager")
    @Autowired
    public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) throws IOException {
        HibernateTransactionManager manager = new HibernateTransactionManager(sessionFactory);
        manager.afterPropertiesSet();
        return manager;
    }

    @Bean(name = "sessionFactory")
    @Autowired
    public SessionFactory getSessionFactory(DataSource dataSource) throws IOException {
        LocalSessionFactoryBean bean = new LocalSessionFactoryBean();
        bean.setAnnotatedClasses(ru.puzikov.common.Vehicle.class);
        bean.setDataSource(dataSource);
        bean.afterPropertiesSet();
        return bean.getObject();
    }
}

