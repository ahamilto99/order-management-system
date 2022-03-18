package com.hamilton.alexander.oms.employee;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.hamilton.alexander.oms.config.annotation.MockAssociate;
import com.hamilton.alexander.oms.config.annotation.MockManager;
import com.hamilton.alexander.oms.config.exception.RecordNotFoundException;

@Import(EmployeeService.class)
@ExtendWith(SpringExtension.class)
@EnableMethodSecurity(prePostEnabled = true)
public class EmployeeServiceTest {

    @Autowired
    private EmployeeService underTest;

    @MockBean
    private EmployeeRepository employeeRepositoryMock;

    private static Random rand = new Random();

    private static PageRequest pageable = PageRequest.of(0, 5, Sort.Direction.ASC, "lastName", "firstName");

    @Test
    @MockManager
    void shouldFindPageOfEmployeePrjsForManagers() {
        Mockito.when(employeeRepositoryMock.findAllEmployeePrjBy(pageable)).thenReturn(MockEmployeePrjs.ALL);

        List<EmployeePrj> employees = underTest.findPageOfEmployeePrjs(pageable).getContent();

        Assertions.assertThat(employees).hasSize(3);
        Assertions.assertThat(employees.get(0)).isEqualTo(MockEmployeePrjs.WAYNE);
        Assertions.assertThat(employees.get(1)).isEqualTo(MockEmployeePrjs.AL);
        Assertions.assertThat(employees.get(2)).isEqualTo(MockEmployeePrjs.DON);

        Mockito.verify(employeeRepositoryMock, Mockito.only()).findAllEmployeePrjBy(pageable);
    }

    @Test
    @MockAssociate
    void shouldThrowAccessDeniedExceptionWhenAssociatesFindPageOfEmployeePrjs() {
        Assertions.assertThatExceptionOfType(AccessDeniedException.class).isThrownBy(() -> underTest.findPageOfEmployeePrjs(pageable));

        Mockito.verifyNoInteractions(employeeRepositoryMock);
    }

    @Test
    @MockManager
    void shouldFindEmployeePrjByIdForManagers() {
        Mockito.when(employeeRepositoryMock.findEmployeePrjById(MockEmployeePrjs.DON_ID)).thenReturn(Optional.of(MockEmployeePrjs.DON));

        Assertions.assertThat(underTest.findEmployeePrjById(MockEmployeePrjs.DON_ID))
                .extracting("id", "firstName", "lastName", "phone", "email", "jwtSub")
                .containsExactly(MockEmployeePrjs.DON_ID, "Don", "Hamilton", "613-555-1234", "dham@oms.com", MockEmployeePrjs.DON_SUB);
    }

    @Test
    @MockAssociate
    void shouldThrowAssociateDeniedExceptionWhenAssociatesFindEmployeePrjById() {
        Assertions.assertThatExceptionOfType(AccessDeniedException.class).isThrownBy(() -> underTest.findEmployeePrjById(1L));

        Mockito.verifyNoInteractions(employeeRepositoryMock);
    }

    @Test
    @MockManager
    void shouldThrowRecordNotFoundExceptionWhenIdDoesNotExist() {
        var id = rand.nextLong(1L, Long.MAX_VALUE);

        Mockito.when(employeeRepositoryMock.findEmployeePrjById(id)).thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(RecordNotFoundException.class).isThrownBy(() -> underTest.findEmployeePrjById(id))
                .withMessage("Employee id=" + id + " does not exist");
    }

    @Test
    @MockManager
    void shouldSaveEmployeeForManagers() {
        var id = rand.nextLong(1L, Long.MAX_VALUE);
        Employee newEmployee = Mockito.mock(Employee.class);

        Mockito.when(newEmployee.getId()).thenReturn(id);
        Mockito.when(employeeRepositoryMock.save(ArgumentMatchers.any(Employee.class))).thenReturn(newEmployee);

        var newEmployeeId = underTest.saveEmployee(new Employee());

        Assertions.assertThat(newEmployeeId).isEqualTo(id);

        Mockito.verify(employeeRepositoryMock, Mockito.times(1)).save(ArgumentMatchers.any(Employee.class));
    }

    @Test
    @MockAssociate
    void shouldThrowAccessDeniedExceptionWhenAssociateSavesEmployee() {
        Assertions.assertThatExceptionOfType(AccessDeniedException.class).isThrownBy(() -> underTest.saveEmployee(new Employee()));

        Mockito.verifyNoInteractions(employeeRepositoryMock);
    }

}
