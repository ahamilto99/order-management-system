package com.hamilton.alexander.oms;

import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.hamilton.alexander.oms.config.SecurityConfig;

import dasniko.testcontainers.keycloak.KeycloakContainer;

@Import(SecurityConfig.class)
@Testcontainers(disabledWithoutDocker = true)
public class KeycloakTestcontainer {

    @Container
    static final KeycloakContainer KEYCLOAK = new KeycloakContainer().withRealmImportFile("oms-realm.json").withReuse(true);

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.security.oauth2.resourceserver.jwt.issuer-uri", () -> KEYCLOAK.getAuthServerUrl() + "/realms/oms");
    }

    
//    @Test
//    void test() {
//        System.out.println();
//        System.out.println(KEYCLOAK.getAdminUsername());
//        System.out.println(KEYCLOAK.getAdminPassword());
//        System.out.println();
//        System.out.println(KEYCLOAK.getAuthServerUrl());
//        System.out.println(KEYCLOAK.getContainerIpAddress());
//        System.out.println(KEYCLOAK.getHttpPort());
//        System.out.println();
//    }
    
}
