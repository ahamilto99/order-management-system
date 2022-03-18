package com.hamilton.alexander.oms.employee;

import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.hamilton.alexander.oms.config.DataSourceProxyLoggerBeanPostProcessor;

@ActiveProfiles("db-container")
@DataJpaTest
@Testcontainers(disabledWithoutDocker = true)
@Import(DataSourceProxyLoggerBeanPostProcessor.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EmployeeRepositoryTest {

    private static final UUID ALEXANDER_JWT_SUB = UUID.fromString("19fce851-e677-42e5-807b-356eb48a4329");

    private static final UUID CATHY_JWT_SUB = UUID.fromString("d73af0e7-d2e9-477a-9035-782bf7e18619");

    private static final UUID KEITH_JWT_SUB = UUID.fromString("473f2595-c009-4529-91e7-735180174f14");

    @Autowired
    private EmployeeRepository underTest;

    @Test
    void findAllProjectionsWithPageable() {
        Page<EmployeePrj> employees = underTest.findAllEmployeePrjBy(PageRequest.of(0, 10, Sort.by("lastName", "firstName")));

        Assertions.assertThat(employees).hasSize(3);
        Assertions.assertThat(employees).extracting("id", "jwtSub", "lastName", "firstName", "email", "phone").containsExactly(
                Tuple.tuple(3L, CATHY_JWT_SUB, "Coady", "Cathy", "cathy.coady@oms.com", "613-555-7278"),
                Tuple.tuple(2L, KEITH_JWT_SUB, "Coady", "Keith", "keith.coady@oms.com", "613-555-1016"),
                Tuple.tuple(1l, ALEXANDER_JWT_SUB, "Hamilton", "Alexander", "alexander.hamilton@oms.com", "613-555-4106"));
    }

    @Test
    void shouldFindOneEmployeeByItsId() {
        EmployeePrj employee = underTest.findEmployeePrjById(1L).get();

        Assertions.assertThat(employee).extracting("id", "jwtSub", "lastName", "firstName", "email", "phone").containsExactly(1L,
                ALEXANDER_JWT_SUB, "Hamilton", "Alexander", "alexander.hamilton@oms.com", "613-555-4106");
    }

    @Test
    void shouldReturnFalseWhenEmailIsNotTaken() {
        var email = RandomStringUtils.randomAlphabetic(10).concat("@email.com");

        Assertions.assertThat(underTest.checkEmailTaken(email)).isFalse();
    }

    @Test
    void shouldReturnTrueWhenEmailIsTaken() {
        var email = "cathy.coady@oms.com";

        Assertions.assertThat(underTest.checkEmailTaken(email)).isTrue();
    }

    @Test
    void shouldReturnTrueWhenPhoneIsTaken() {
        var phone = "613-555-1016";

        Assertions.assertThat(underTest.checkPhoneTaken(phone)).isTrue();
    }

    @Test
    void shouldReturnFalseWhenPhoneIsNotTaken() {
        var phone = "514-555-5555";

        Assertions.assertThat(underTest.checkPhoneTaken(phone)).isFalse();
    }

    @Test
    void shouldReturnTrueWhenJwtSubIsNotTaken() {
        Assertions.assertThat(underTest.checkJwtSubTaken(ALEXANDER_JWT_SUB)).isTrue();
    }

    @Test
    void shouldReturnFalseWhenJwtSubIsNotTaken() {
        var jwtSub = UUID.randomUUID();

        Assertions.assertThat(underTest.checkJwtSubTaken(jwtSub)).isFalse();
    }

    @Test
    void shouldReturnTrueWhenEmailIsTakenByAnotherEmployee() {
        var email = "keith.coady@oms.com";
        var id = 1L;

        Assertions.assertThat(underTest.checkEmailTakenByAnotherEmployee(email, id)).isTrue();
    }

    @Test
    void shouldReturnFalseWhenEmailIsNotTakenByAnotherEmployee() {
        var email = "keith.coady@oms.com";
        var id = 2L;

        Assertions.assertThat(underTest.checkEmailTakenByAnotherEmployee(email, id)).isFalse();
    }

    @Test
    void shouldReturnTrueWhenPhoneIsTakenByAnotherEmployee() {
        var phone = "613-555-1016";
        var id = 1L;

        Assertions.assertThat(underTest.checkPhoneTakenByAnotherEmployee(phone, id)).isTrue();
    }

    @Test
    void shouldReturnFalseWhenPhoneIsNotTakenByAnotherEmployee() {
        var phone = "613-555-1016";
        var id = 2L;

        Assertions.assertThat(underTest.checkPhoneTakenByAnotherEmployee(phone, id)).isFalse();
    }

    @Test
    void shouldReturnTrueWhenJwtSubIsTakenByAnotherEmployee() {
        var id = 1L;

        Assertions.assertThat(underTest.checkJwtSubTakenByAnotherEmployee(KEITH_JWT_SUB, id)).isTrue();
    }

    @Test
    void shouldReturnFalseWhenJwtSubIsNotTakenByAnotherEmployee() {
        var id = 2L;

        Assertions.assertThat(underTest.checkJwtSubTakenByAnotherEmployee(KEITH_JWT_SUB, id)).isFalse();
    }

}
