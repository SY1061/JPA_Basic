package com.sunny.JPA.config;

import com.sunny.JPA.Repostiory.RepositoryBase;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackageClasses = RepositoryBase.class)
public class JavaConfig {
}
