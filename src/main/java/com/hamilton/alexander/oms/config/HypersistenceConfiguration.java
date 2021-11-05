package com.hamilton.alexander.oms.config;

import javax.persistence.EntityManagerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.hypersistence.optimizer.HypersistenceOptimizer;
import io.hypersistence.optimizer.core.config.JpaConfig;

// TODO: REMOVE THIS CLASS WHEN V1 IS COMPLETE
// TODO: REMOVE HYPERSISTENCE DEPENDENCY WHEN V1 IS COMPLETE
@Configuration
public class HypersistenceConfiguration {

    @Bean
    public HypersistenceOptimizer hypersistenceOptimizer(EntityManagerFactory emf) {
        return new HypersistenceOptimizer(new JpaConfig(emf));
    }
    
}
