package com.hamilton.alexander.oms.config.exception;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.mapping.PropertyPath;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.data.util.ClassTypeInformation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;
import org.springframework.validation.BindingResult;
import org.springframework.web.util.UriComponentsBuilder;

import com.github.dockerjava.zerodep.shaded.org.apache.hc.core5.http.HttpHeaders;
import com.hamilton.alexander.oms.config.SecurityConfig;
import com.hamilton.alexander.oms.config.utility.BearerTokens;
import com.hamilton.alexander.oms.config.utility.URIs;
import com.hamilton.alexander.oms.employee.Employee;
import com.hamilton.alexander.oms.employee.EmployeeController;

@WebMvcTest
@Import(SecurityConfig.class)
public class RestResponseExceptionHandlerTest {

    @MockBean
    private EmployeeController employeeControllerMock;

    @Autowired
    private MockMvc mvc;

    private WebTestClient webTestClient;

    private static final Matcher<String> TIMESTAMP_MATCHER = Matchers.greaterThan(ZonedDateTime
            .ofInstant(Instant.now(), ZoneId.of("America/Montreal")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS z")));

    private static final int NUM_JSON_FIELDS = 5;

    // @formatter:off
    
    @BeforeEach
    private void setup() {
        webTestClient = MockMvcWebTestClient
                .bindTo(mvc)
                .build();
    }
    
    /*
     * The test for handling MethodArgumentNotValidException returns Entity-specific error messages; therefore,
     * there are tests for it in every Controller test class.
     */
    
    @Test
    void shouldHandleIllegalArgumentException400() {
        var expectedStatus = HttpStatus.BAD_REQUEST;
        var expectedMessage = "Database supplies the ID";
        
        var invalidEmployeeJson = """
                {
                    "id": "-1",
                    "firstName": "Linda",
                    "lastName": "Smith",
                    "email": "linda.smith@email.com",
                    "phone": "613-555-8916",
                    "jwtSub": "4a6b64b0-bb06-477a-8c62-3d915615e2a5"
                }
                """;
        
        Mockito.when(
                employeeControllerMock.createEmployee(ArgumentMatchers.any(Employee.class), ArgumentMatchers.any(BindingResult.class),
                        ArgumentMatchers.any(HttpServletRequest.class), ArgumentMatchers.any(UriComponentsBuilder.class)))
            .thenThrow(new IllegalArgumentException("Database supplies the ID"));
        
        webTestClient
                .post()
                .uri(URIs.EMPLOYEES)
                .header(HttpHeaders.AUTHORIZATION, BearerTokens.MANAGER)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(invalidEmployeeJson)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                        .jsonPath("$.timestamp").value(TIMESTAMP_MATCHER)
                        .jsonPath("$.status").isEqualTo(expectedStatus.value())
                        .jsonPath("$.error").isEqualTo(expectedStatus.getReasonPhrase())
                        .jsonPath("$.message").isEqualTo(expectedMessage)
                        .jsonPath("$.path").isEqualTo(URIs.EMPLOYEES)
                        .jsonPath("$.size()").isEqualTo(NUM_JSON_FIELDS);
    }
    
    @Test
    void shouldHandlePropertyReferenceException400() {
        var invalidSortByQueryParam = "INVALID";
        var page = 0;
        var size = 5;
        var order = "desc";
        var uri = URIs.EMPLOYEES + "?page=" + page + "&size=" + size + "&sortBy=" + invalidSortByQueryParam + "&order=" + order;
        
        var expectedStatus = HttpStatus.BAD_REQUEST;
        var expectedMessage = "No property '" + invalidSortByQueryParam + "' found for type 'Employee'!";
        
        Mockito.when(employeeControllerMock.getEmployees(page, size, invalidSortByQueryParam, order))
            .thenThrow(new PropertyReferenceException(invalidSortByQueryParam, ClassTypeInformation.from(Employee.class),
                        new ArrayList<PropertyPath>()));
        
        webTestClient
                .get()
                .uri(uri)
                .header(HttpHeaders.AUTHORIZATION, BearerTokens.MANAGER)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                        .jsonPath("$.timestamp").value(TIMESTAMP_MATCHER)
                        .jsonPath("$.status").isEqualTo(expectedStatus.value())
                        .jsonPath("$.error").isEqualTo(expectedStatus.getReasonPhrase())
                        .jsonPath("$.message").isEqualTo(expectedMessage)
                        .jsonPath("$.path").isEqualTo(URIs.EMPLOYEES)
                        .jsonPath("$.size()").isEqualTo(5);
    }
    
    @Test
    void shouldHandleAccessDeniedException403() {
        var expectedStatus = HttpStatus.FORBIDDEN;
        var expectedMessage = "Insufficient privileges";
        
        var employeeJson = """
                {
                    "firstName": "Johnny",
                    "lastName": "Boy",
                    "email": "jb@email.com",
                    "phone": "613-555-0110",
                    "jwtSub": "4a6b64b0-bb06-477a-8c62-3d9156151111"
                }
                """;
        
        Mockito.when(
                employeeControllerMock.createEmployee(ArgumentMatchers.any(Employee.class), ArgumentMatchers.any(BindingResult.class),
                        ArgumentMatchers.any(HttpServletRequest.class), ArgumentMatchers.any(UriComponentsBuilder.class)))
            .thenThrow(AccessDeniedException.class);
        
        webTestClient
                .post()
                .uri(URIs.EMPLOYEES)
                .header(HttpHeaders.AUTHORIZATION, BearerTokens.MANAGER)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(employeeJson)
                .exchange()
                .expectStatus().isForbidden()
                .expectBody()
                        .jsonPath("$.timestamp").value(TIMESTAMP_MATCHER)
                        .jsonPath("$.status").isEqualTo(expectedStatus.value())
                        .jsonPath("$.error").isEqualTo(expectedStatus.getReasonPhrase())
                        .jsonPath("$.message").isEqualTo(expectedMessage)
                        .jsonPath("$.path").isEqualTo(URIs.EMPLOYEES)
                        .jsonPath("$.size()").isEqualTo(5);
    }
    
    // @formatter:on

}
