package com.sunny.JPA.config;

import com.sunny.JPA.Repostiory.RepositoryBase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(basePackageClasses = RepositoryBase.class)
public class JavaConfig {
    // 순수 JPA 환경에서는 Persistence.EntityManagerFactory 사용해서 EntityManagerFactory 생성 가능.
    // 스프링 환경에서는 LocalContainerEntityManagerFactoryBean을 사용해서 JPA 관리.
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource); // 데이터베이스와의 연결을 설정하기 위한 javax.sql.DataSource을 설정.
        emf.setPackagesToScan("com.sunny.JPA.entity"); // JPA Entity Class가 위치한 패키지를 지정.
        emf.setJpaVendorAdapter(jpaVendorAdapters()); // JPA 구현체(vendor)에 대한 설정을 지정.
        // vendor란 JPA는 표준 인터페이스이므로 여러가지 데이터베이스를 지원함. 그에 따라 데이터베이스마다 차이가 존재함.
        // 이 차이점을 추상화하기 위해서 vendor라는 것이 존재.
        emf.setJpaProperties(jpaProperties()); // JPA 구현체에 대한 추가 설정을 지정.

        return emf;
    }

    private JpaVendorAdapter jpaVendorAdapters() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setDatabase(Database.H2);

        return hibernateJpaVendorAdapter;
    }

    private Properties jpaProperties() {
        Properties jpaProperties = new Properties();
        jpaProperties.setProperty("hibernate.show_sql", "false");
        jpaProperties.setProperty("hibernate.format_sql", "true");
        jpaProperties.setProperty("hibernate.use_sql_comments", "true");
        jpaProperties.setProperty("hibernate.globally_quoted_identifiers", "true");
        jpaProperties.setProperty("hibernate.temp.use_jdbc_metadata_defaults", "false");

        return jpaProperties;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);

        return transactionManager;
    }
}
