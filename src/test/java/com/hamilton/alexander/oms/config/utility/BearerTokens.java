package com.hamilton.alexander.oms.config.utility;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class BearerTokens {
    
    public static final String MANAGER = //"Bearer ".concat("eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICIzQTRvYlBwLWhtZFpqQXFSTEdDQlZkOVlxalE5T0tWRXY1aWVKYlZoRWpNIn0.eyJleHAiOjE2NDc1ODI3ODMsImlhdCI6MTY0NzU0Njc4MywiYXV0aF90aW1lIjoxNjQ3NTQ2NzgzLCJqdGkiOiJiMzg1MWVlOS0zYTkxLTQwMGYtOTk0MS0zZDE0YWRjZjhiYzgiLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODEvYXV0aC9yZWFsbXMvb21zIiwiYXVkIjoiYWNjb3VudCIsInN1YiI6IjE5ZmNlODUxLWU2NzctNDJlNS04MDdiLTM1NmViNDhhNDMyOSIsInR5cCI6IkJlYXJlciIsImF6cCI6Im9tcy1yZXNvdXJjZS1zZXJ2ZXIiLCJzZXNzaW9uX3N0YXRlIjoiNjE0M2ZiYzAtODdmMS00OGY3LWFhZDAtMjRkYmY2ZGIwNDJkIiwiYWNyIjoiMSIsInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJkZWZhdWx0LXJvbGVzLW9tcyIsIm1hbmFnZXIiLCJvZmZsaW5lX2FjY2VzcyIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJlbWFpbCBtYW5hZ2VyIHByb2ZpbGUiLCJzaWQiOiI2MTQzZmJjMC04N2YxLTQ4ZjctYWFkMC0yNGRiZjZkYjA0MmQiLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwibmFtZSI6IkFsZXhhbmRlciBIYW1pbHRvbiIsInByZWZlcnJlZF91c2VybmFtZSI6ImFsZXhhbmRlcmgiLCJnaXZlbl9uYW1lIjoiQWxleGFuZGVyIiwiZmFtaWx5X25hbWUiOiJIYW1pbHRvbiIsImVtYWlsIjoiYWxleGFuZGVyLmhhbWlsdG9uQG9tcy5jb20ifQ.E6XJTKxxTbBlVlLNXA3SnyqIfIGUbci2cetJBY1CA8RwlnQQo0f241zzruLqAyZgR6Ap5nEzKaL9kzrSZsBnS-TqRxpIWO5RwIHhbwdCpv9yII1NVUG38bflKgdkCP7BbykH2n0Ia5ItPqmDj6-Zo8HmzkUjq7_B9tgG5uf2CqQQU1Q8o_HV-affFcQU-eZhS7ChOq-2SIkXfCk_MkrEiNxzqb6Uwz-azNRZvkvvdu-ZkICBwQe3ySKP_AIwhiDyp2UX1P1k55cozZL521-UB_TS4rRw_6vndO38Gjfd23XxaVr6iFjhtPMr_KU9aO8X9vywnK3WkxoQVqMTGiyoAg");
            "Bearer ".concat(obtainBearerToken("alexanderh", "password"));
    
    public static final String ASSOCIATE = //"Bearer ".concat("eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICIzQTRvYlBwLWhtZFpqQXFSTEdDQlZkOVlxalE5T0tWRXY1aWVKYlZoRWpNIn0.eyJleHAiOjE2NDc1ODI3ODQsImlhdCI6MTY0NzU0Njc4NCwiYXV0aF90aW1lIjoxNjQ3NTQ2Nzg0LCJqdGkiOiI5MDVjMjM5Yy00YzQ5LTQ0MzEtOTVhZC0zOGVkYTNjNzJmYmEiLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODEvYXV0aC9yZWFsbXMvb21zIiwiYXVkIjoiYWNjb3VudCIsInN1YiI6ImQ3M2FmMGU3LWQyZTktNDc3YS05MDM1LTc4MmJmN2UxODYxOSIsInR5cCI6IkJlYXJlciIsImF6cCI6Im9tcy1yZXNvdXJjZS1zZXJ2ZXIiLCJzZXNzaW9uX3N0YXRlIjoiMDU4ZGMxN2EtZTFmMi00OTYyLWEyN2QtNWUxYjVkNzk5NDUyIiwiYWNyIjoiMSIsInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJkZWZhdWx0LXJvbGVzLW9tcyIsIm9mZmxpbmVfYWNjZXNzIiwidW1hX2F1dGhvcml6YXRpb24iLCJhc3NvY2lhdGUiXX0sInJlc291cmNlX2FjY2VzcyI6eyJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6ImVtYWlsIHByb2ZpbGUgYXNzb2NpYXRlIiwic2lkIjoiMDU4ZGMxN2EtZTFmMi00OTYyLWEyN2QtNWUxYjVkNzk5NDUyIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsIm5hbWUiOiJDYXRoeSBDb2FkeSIsInByZWZlcnJlZF91c2VybmFtZSI6ImNhdGh5YyIsImdpdmVuX25hbWUiOiJDYXRoeSIsImZhbWlseV9uYW1lIjoiQ29hZHkiLCJlbWFpbCI6ImNhdGh5LmNvYWR5QG9tcy5jb20ifQ.V2yT1pzr_TayOGc5jLVvyhPRsE0Bkr5jXpphxUBlJJnH_mIBT0WVuiWyf56wg-8nJbaWmSsYRVTFAYS5g3HAIooehr6DtVi50gw5GVjRS2W-X3CeFSi7qxgY91JPYOGN_GrKQCEcShmZXlGvutW9xptJrDZCt_wbaBSLA9xRedcxxtnK3cv-7bdXLEmDRxG4fVc7kQOxDMhoQy7MlI-SjtJSAFpDJrh9f0S-h-hHW8mdEay7wm4unEYWVxYHQjzFpQXIguBuvFZ0ZvdLFCJIw70PHbpIrGNz7vuc8mMq8ePG2YMEqbkqCLlU1jT_hovhmx6pVp4wbr9fB4al8_u6Ug");
            "Bearer ".concat(obtainBearerToken("cathyc", "password"));

    @Value("${ouath2.client-id}")
    private static String clientId;

    @Value("${ouath2.client-secret}")
    private static String clientSecret;

    private static final String AUTH_SERVER_BASE_URL = "http://localhost:8081/auth/realms/oms";

    private static final String REDIRECT_URL = "http://localhost:8080/oms/api/v1/callback&state=kjgr98sdaf4u5reabqf9u7";

    private static final String AUTH_SERVER_AUTHORIZE_URL = AUTH_SERVER_BASE_URL + "/protocol/openid-connect/auth?client_id=" + clientId
            + "&response_type=code&scope=profile&redirect_url" + REDIRECT_URL;

    private static final String TOKEN_URL = AUTH_SERVER_BASE_URL + "/protocol/openid-connect/token";
    
    private static String obtainBearerToken(String username, String password) { // @formatter:off
        Response response = RestAssured.get(AUTH_SERVER_AUTHORIZE_URL);
        
        response = authenticateAgainstAuthServer(response, username, password);
        
        String location = response.getHeader(HttpHeaders.LOCATION);
        String code = location.split("code=")[1].split("&")[0];
        
        Map<String, String> params = new HashMap<>();
        params.put("grant_type", "authorization_code");
        params.put("code", code);
        params.put("client_id", clientId);
        params.put("client_secret", clientSecret);
        params.put("redirect_uri", REDIRECT_URL);
        
        response = RestAssured
                .given()
                .formParams(params)
                .post(TOKEN_URL);
        
        String accessToken = response.jsonPath().getString("access_token");
        
        System.out.println();
        System.out.println(username + ':');
        System.out.println(accessToken);
        System.out.println();
        
        return accessToken;
    }
    
    private static Response authenticateAgainstAuthServer(Response response, String username, String password) { 
        String authSessionId = response.getCookie("AUTH_SESSION_ID");
        String authServerPostAuthenticationUrl = response.asString().split("action=\"")[1].split("\"")[0].replace("&amp;", "&");

        return RestAssured
                .given()
                .cookie("AUTH_SESSION_ID", authSessionId)
                .formParams("username", username, "password", password)
                .post(authServerPostAuthenticationUrl);
    } 
    // @formatter:on

    @Test
    void test() {
    }
    
}
