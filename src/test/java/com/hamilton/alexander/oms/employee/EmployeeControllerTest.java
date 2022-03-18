package com.hamilton.alexander.oms.employee;

import java.time.Instant;
import java.util.Random;
import java.util.UUID;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.util.UriComponentsBuilder;

import com.hamilton.alexander.oms.config.SecurityConfig;
import com.hamilton.alexander.oms.config.utility.BearerTokens;
import com.hamilton.alexander.oms.config.utility.URIs;

@WebMvcTest
@Import(SecurityConfig.class)
public class EmployeeControllerTest {

    @MockBean
    private EmployeeService employeeServiceMock;

    @Mock
    private UriComponentsBuilder uriBuilderMock;

    @Autowired
    private WebTestClient webTestClient;

    private Random rand = new Random();

    private Pageable pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "lastName", "firstName");

    private static final String EMPLOYEES_ROOT = "$.content";

    private static final String NEW_EMPLOYEE_JSON = """
            {
                "firstName": "Will",
                "lastName": "Jensen",
                "email": "wjensen@email.com",
                "phone": "613-555-1990",
                "jwtSub": "4a6b64b0-bb06-477a-8c62-3d915615e2a5"
            }
            """;

    private static final String UPDATED_EMPLOYEE_JSON = """
            {
                "id": "1",
                "firstName": "Alex",
                "lastName": "Hamilton",
                "email": "ahamilton@email.com",
                "phone": "613-555-7979",
                "jwtSub": "dcfe110d-ab8f-4386-8a77-4f96c9c43d08"
            }
            """;

    private static final String UNIQUE_VAL = "Value must be unique";

    private static final String REQUIRED_FIELD = "Field is required";
    
    private static final String TAKEN_VAL = "Value is taken";

    // @formatter:off
    
    @Test
    void managerGetsEmployees200() {
        Mockito.when(employeeServiceMock.findPageOfEmployeePrjs(pageable)).thenReturn(MockEmployeePrjs.ALL);
        
        webTestClient
                .get()
                .uri(URIs.EMPLOYEES)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, BearerTokens.MANAGER)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                        .jsonPath(EMPLOYEES_ROOT.concat(".size()")).isEqualTo(3)
                        .jsonPath(EMPLOYEES_ROOT.concat("[2].id")).isEqualTo(MockEmployeePrjs.DON_ID.toString())
                        .jsonPath(EMPLOYEES_ROOT.concat("[2].firstName")).isEqualTo("Don")
                        .jsonPath(EMPLOYEES_ROOT.concat("[2].lastName")).isEqualTo("Hamilton")
                        .jsonPath(EMPLOYEES_ROOT.concat("[2].email")).isEqualTo("dham@oms.com")
                        .jsonPath(EMPLOYEES_ROOT.concat("[2].phone")).isEqualTo("613-555-1234");
        
        Mockito.verify(employeeServiceMock, Mockito.only()).findPageOfEmployeePrjs(ArgumentMatchers.any(Pageable.class));
        Mockito.verify(employeeServiceMock, Mockito.times(1)).findPageOfEmployeePrjs(ArgumentMatchers.any(Pageable.class));
    }
    
    @Test
    void associateCannotGetAllEmployees403() {
        webTestClient
                .get()
                .uri(URIs.EMPLOYEES)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, BearerTokens.ASSOCIATE)
                .exchange()
                .expectStatus().isForbidden();
        
         Mockito.verifyNoInteractions(employeeServiceMock);
    }
    
    @Test
    void managerGetsOneEmployee200() {
        var wayneUri = URIs.EMPLOYEES_SLASH.concat(MockEmployeePrjs.WAYNE_ID.toString());
        
        Mockito.when(employeeServiceMock.findEmployeePrjById(MockEmployeePrjs.WAYNE_ID)).thenReturn(MockEmployeePrjs.WAYNE);
        
        webTestClient
                .get()
                .uri(wayneUri)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, BearerTokens.MANAGER)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                        .jsonPath("$.size()").isEqualTo(6)
                        .jsonPath("$.id").isEqualTo(MockEmployeePrjs.WAYNE_ID.toString())
                        .jsonPath("$.firstName").isEqualTo("Wayne")
                        .jsonPath("$.lastName").isEqualTo("Coady")
                        .jsonPath("$.email").isEqualTo("wcoady@oms.com")
                        .jsonPath("$.phone").isEqualTo("613-555-9999")
                        .jsonPath("$.jwtSub").isEqualTo(MockEmployeePrjs.WAYNE_SUB.toString());
        
        Mockito.verify(employeeServiceMock, Mockito.only()).findEmployeePrjById(MockEmployeePrjs.WAYNE_ID);
        Mockito.verify(employeeServiceMock, Mockito.times(1)).findEmployeePrjById(MockEmployeePrjs.WAYNE_ID);
    }
    
    @Test
    void associateCannotGetOneEmployee403() {
        var employeeDonUri = URIs.EMPLOYEES_SLASH.concat(MockEmployeePrjs.DON_ID.toString());

        webTestClient
                .get()
                .uri(employeeDonUri)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, BearerTokens.ASSOCIATE)
                .exchange()
                .expectStatus().isForbidden();
        
        Mockito.verifyNoInteractions(employeeServiceMock);
    }
    
    @Test
    void managerPostsAnEmployee201() {
        var newEmployeeId = rand.nextLong(1L, Long.MAX_VALUE);
        
        Mockito.when(employeeServiceMock.saveEmployee(ArgumentMatchers.any(Employee.class))).thenReturn(newEmployeeId);
        
        webTestClient
                .post()
                .uri(URIs.EMPLOYEES)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, BearerTokens.MANAGER)
                .bodyValue(NEW_EMPLOYEE_JSON)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().location(URIs.EMPLOYEES_SLASH_FULL + newEmployeeId)
                .expectBody().isEmpty();
        
        Mockito.verify(employeeServiceMock, Mockito.times(1)).saveEmployee(ArgumentMatchers.any(Employee.class));
    }
    
    @Test
    void associateCannotPostAnEmployee403() {
        webTestClient
                .post()
                .uri(URIs.EMPLOYEES)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, BearerTokens.ASSOCIATE)
                .bodyValue(NEW_EMPLOYEE_JSON)
                .exchange()
                .expectHeader().doesNotExist(HttpHeaders.LOCATION)
                .expectStatus().isForbidden();
                
        Mockito.verifyNoInteractions(employeeServiceMock);
    }

    @Test
    void managerPostsAnInvalidEmployee400() {
        var expectedStatus = HttpStatus.BAD_REQUEST;
        
        var invalidEmployeeJson = """
                {
                    "firstName": "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX",
                    "lastName": "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX",
                    "email": "taken.email@oms.com",
                    "phone": "613-555-1234",
                    "jwtSub": "19fce851-e677-42e5-807b-356eb48a4329"
                }
                """;
        
        Mockito.when(employeeServiceMock.checkEmployeeEmailTaken(ArgumentMatchers.anyString())).thenReturn(true);
        Mockito.when(employeeServiceMock.checkEmployeePhoneTaken(ArgumentMatchers.anyString())).thenReturn(true);
        Mockito.when(employeeServiceMock.checkEmployeeJwtSubTaken(ArgumentMatchers.any(UUID.class))).thenReturn(true);
        
        webTestClient
                .post()
                .uri(URIs.EMPLOYEES)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, BearerTokens.MANAGER)
                .bodyValue(invalidEmployeeJson)
                .exchange()
                .expectStatus().isBadRequest()
                .expectHeader().doesNotExist(HttpHeaders.LOCATION)
                .expectBody()
                        .jsonPath("$.size()").isEqualTo(5)
                        .jsonPath("$.timestamp").value(Matchers.lessThan(Instant.now().toString()))
                        .jsonPath("$.status").isEqualTo(expectedStatus.value())
                        .jsonPath("$.errors.size()").isEqualTo(5)
                        .jsonPath("$.errors.firstName").isEqualTo("Field cannot be more than 30 characters")
                        .jsonPath("$.errors.lastName").isEqualTo("Field cannot be more than 50 characters")
                        .jsonPath("$.errors.email").isEqualTo(UNIQUE_VAL)
                        .jsonPath("$.errors.phone").isEqualTo(UNIQUE_VAL)
                        .jsonPath("$.errors.jwtSub").isEqualTo(UNIQUE_VAL)
                        .jsonPath("$.message").isEqualTo(expectedStatus.getReasonPhrase())
                        .jsonPath("$.path").isEqualTo(URIs.EMPLOYEES);
        
        Mockito.verify(employeeServiceMock, Mockito.never()).saveEmployee(ArgumentMatchers.any(Employee.class));
    }
    
    @Test
    void managerPutsAnEmployee200() {
        var id = 1L;
        
        Mockito.when(employeeServiceMock.checkEmployeeEmailTakenByAnotherEmployee(ArgumentMatchers.anyString(), ArgumentMatchers.eq(id)))
                .thenReturn(false);
        Mockito.when(employeeServiceMock.checkEmployeePhoneTakenByAnotherEmployee(ArgumentMatchers.anyString(), ArgumentMatchers.eq(id)))
                .thenReturn(false);
        Mockito.when(employeeServiceMock.checkEmployeeJwtSubTakenByAnotherEmployee(ArgumentMatchers.any(UUID.class), ArgumentMatchers.eq(id)))
                .thenReturn(false);
        Mockito.when(employeeServiceMock.saveEmployee(ArgumentMatchers.any(Employee.class))).thenReturn(id);
        
        webTestClient
                .put()
                .uri(URIs.EMPLOYEES_ONE, id)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, BearerTokens.MANAGER)
                .bodyValue(UPDATED_EMPLOYEE_JSON)
                .exchange()
                .expectStatus().isNoContent()
                .expectHeader().location(URIs.EMPLOYEES_SLASH_FULL + id)
                .expectBody().isEmpty();
        
        Mockito.verify(employeeServiceMock, Mockito.times(1)).saveEmployee(ArgumentMatchers.any(Employee.class));
    }
    
    @Test
    void managerPutsInvalidEmployee400() {
        var id = 1L;
        var status = HttpStatus.BAD_REQUEST;
        
        var invalidEmployeeJson = """
                {
                    "id": "%d",
                    "firstName": "",
                    "lastName": "",
                    "email": "taken.email.by.another.employee@oms.com",
                    "phone": "613-555-1234",
                    "jwtSub": "d73af0e7-d2e9-477a-9035-782bf7e18619"
                }
                """.formatted(id);
        
        Mockito.when(employeeServiceMock.checkEmployeeEmailTakenByAnotherEmployee(ArgumentMatchers.anyString(), ArgumentMatchers.eq(id)))
                .thenReturn(true);
        Mockito.when(employeeServiceMock.checkEmployeePhoneTakenByAnotherEmployee(ArgumentMatchers.anyString(), ArgumentMatchers.eq(id)))
                .thenReturn(true);
        Mockito.when(employeeServiceMock.checkEmployeeJwtSubTakenByAnotherEmployee(ArgumentMatchers.any(UUID.class), ArgumentMatchers.eq(id)))
                .thenReturn(true);
        
        webTestClient
                .put()
                .uri(URIs.EMPLOYEES_ONE, id)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, BearerTokens.MANAGER)
                .bodyValue(invalidEmployeeJson)
                .exchange()
                .expectStatus().isBadRequest()
                .expectHeader().doesNotExist(HttpHeaders.LOCATION)
                .expectBody()
                        .jsonPath("$.timestamp").value(Matchers.lessThan(Instant.now().toString()))
                        .jsonPath("$.status").isEqualTo(status.value())
                        .jsonPath("$.errors.size()").isEqualTo(5)
                        .jsonPath("$.errors.firstName").isEqualTo(REQUIRED_FIELD)
                        .jsonPath("$.errors.lastName").isEqualTo(REQUIRED_FIELD)
                        .jsonPath("$.errors.email").isEqualTo(TAKEN_VAL)
                        .jsonPath("$.errors.phone").isEqualTo(TAKEN_VAL)
                        .jsonPath("$.errors.jwtSub").isEqualTo(TAKEN_VAL)
                        .jsonPath("$.message").isEqualTo(status.getReasonPhrase())
                        .jsonPath("$.path").isEqualTo(URIs.EMPLOYEES_SLASH + id);
        
        Mockito.verify(employeeServiceMock, Mockito.never()).saveEmployee(ArgumentMatchers.any(Employee.class));
    }
    
    @Test
    void associateCannotPatchAnEmployee403() {
        webTestClient
                .patch()
                .uri(URIs.EMPLOYEES_ONE, 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, BearerTokens.ASSOCIATE)
                .bodyValue(UPDATED_EMPLOYEE_JSON)
                .exchange()
                .expectHeader().doesNotExist(HttpHeaders.LOCATION)
                .expectStatus().isForbidden();
        
        Mockito.verifyNoInteractions(employeeServiceMock);
    }
   
    // @formatter:on

}
