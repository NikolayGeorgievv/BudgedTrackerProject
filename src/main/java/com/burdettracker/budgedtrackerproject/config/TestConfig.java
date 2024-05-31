package com.burdettracker.budgedtrackerproject.config;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class TestConfig {

    @Bean
    @Profile("test")
    public BeanPostProcessor excludeRolesInitializationBeans() {
        return new BeanPostProcessor() {
            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName) {
                if (beanName.equals("RolesRepositoryInit")) {
                    return null; // Exclude these beans during testing
                }
                return bean;
            }
        };
    }

    @Bean
    @Profile("test")
    public BeanPostProcessor excludeCategoriesInitializationBeans() {
        return new BeanPostProcessor() {
            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName) {
                if ( beanName.equals("CategoriesRepositoryInit")) {
                    return null; // Exclude these beans during testing
                }
                return bean;
            }
        };
    }
}