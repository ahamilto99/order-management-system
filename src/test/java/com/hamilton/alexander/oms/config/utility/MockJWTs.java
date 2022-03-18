package com.hamilton.alexander.oms.config.utility;

import java.util.Map;

import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor;

public class MockJWTs {

    public static final JwtRequestPostProcessor MANAGER = createMockJWT("19fce851-e677-42e5-807b-356eb48a4329", "alexanderh", "manager");

    public static final JwtRequestPostProcessor ASSOCIATE = createMockJWT("d73af0e7-d2e9-477a-9035-782bf7e18619", "cathyc", "associate");

    private static JwtRequestPostProcessor createMockJWT(String id, String username, String scope) {
        return SecurityMockMvcRequestPostProcessors.jwt().jwt(
                jwtBuilder -> jwtBuilder.claims(claims -> claims.putAll(Map.of("sub", id, "preferred_username", username, "scope", scope))));
    }

}
