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
        // JPAVendorAdapter => JPA 구현체(vendor)에 대한 설정을 제공하는 인터페이스.
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        // Hibernate를 사용할 때 사용되는 JpaVendorAdapter의 구현 클래스.
        hibernateJpaVendorAdapter.setDatabase(Database.H2);

        return hibernateJpaVendorAdapter;
    }

    // 프로퍼티 설정. 스프링 부트는 resources 파일에서 설정해도 된다. 이 프로퍼티의 역할은 로그 출력을 위해서 설정한 것.
    private Properties jpaProperties() {
        Properties jpaProperties = new Properties();
        // 실행하는 SQL 쿼리를 로그에 출력할지 여부.
        jpaProperties.setProperty("hibernate.show_sql", "false");
        // 출력된 SQL 쿼리를 포맷팅해서 보여줄지 여부. (굉장히 유용함)
        jpaProperties.setProperty("hibernate.format_sql", "true");
        // 출력된 SQL 쿼리에 주석을 포함할지 여부.
        jpaProperties.setProperty("hibernate.use_sql_comments", "true");
        // 테이블 이름, 컬럼 이름 등에 따옴표를 사용할지 여부.
        jpaProperties.setProperty("hibernate.globally_quoted_identifiers", "true");
        // JDBC 메타데이터를 사용해서 테이블 정보를 가져올지 여부.
        jpaProperties.setProperty("hibernate.temp.use_jdbc_metadata_defaults", "false");
        return jpaProperties;
    }

    /*
        스프링에서 제공하는 트랜잭션 추상화 계층.
        코드에서 명시적으로 트랜잭션을 다루지 않아도 트랜잭션 가능.
        PlatformTransactionManager는 스프링이 지원하는 다양한 트랜잭션 매니저들의 공통 인터페이스.
        이 코드를 Bean으로 등록하거나 트랜잭션을 실행할 코드에 @Transactional 어노테이션 사용.
        어노테이션에 비해 세밀한 조정이 가능하나 코드 양이 늘어나고 정확하게 사용해야 함.
     */
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);

        return transactionManager;
    }
}
