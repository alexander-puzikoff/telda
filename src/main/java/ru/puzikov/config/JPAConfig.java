package ru.puzikov.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;
import ru.puzikov.Executor;

import javax.sql.DataSource;

/**
 * Created by SBT-Puzikov-AYU on 28.11.2016.
 */
@Configuration
@ComponentScan(basePackages = "ru.puzikov")
@EnableTransactionManagement
//@EnableJpaRepositories(basePackageClasses = Executor.class)
public class JPAConfig {

    @Bean
    public DataSource configureDataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        EmbeddedDatabase db = builder
                .setType(EmbeddedDatabaseType.HSQL) //.H2 or .DERBY
                .addScript("db/sql/create-db.sql")
                .addScript("db/sql/create-data-db.sql")
                .setName("telda_db")
                .build();
        return db;
    }

    @Bean
    @Autowired
    public LocalSessionFactoryBean getSessionFactory(DataSource dataSource){
        LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
        localSessionFactoryBean.setDataSource(dataSource);
        return localSessionFactoryBean;
    }

//?    public PlatformTransactionManager annotationDrivenTransactionManager() {
//        return new JpaTransactionManager();
//    }
}

