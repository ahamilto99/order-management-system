package com.hamilton.alexander.oms.employee;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hamilton.alexander.oms.config.annotation.ManagerScope;
import com.hamilton.alexander.oms.config.exception.RecordNotFoundException;

@Service
@ManagerScope
@Transactional(readOnly = true)
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Page<EmployeePrj> findPageOfEmployeePrjs(Pageable pageable) {
        return employeeRepository.findAllEmployeePrjBy(pageable);
    }

    public EmployeePrj findEmployeePrjById(Long id) {
        return employeeRepository.findEmployeePrjById(id).orElseThrow(() -> new RecordNotFoundException(Employee.class, "id", id));
    }

    @Transactional
    public Long saveEmployee(Employee employee) {
        return employeeRepository.save(employee).getId();
    }

    public Boolean checkEmployeeEmailTaken(String email) {
        return employeeRepository.checkEmailTaken(email);
    }

    public Boolean checkEmployeePhoneTaken(String phone) {
        return employeeRepository.checkPhoneTaken(phone);
    }

    public Boolean checkEmployeeJwtSubTaken(UUID jwtSub) {
        return employeeRepository.checkJwtSubTaken(jwtSub);
    }

    public Boolean checkEmployeeEmailTakenByAnotherEmployee(String email, Long id) {
        return employeeRepository.checkEmailTakenByAnotherEmployee(email, id);
    }

    public Boolean checkEmployeePhoneTakenByAnotherEmployee(String phone, Long id) {
        return employeeRepository.checkPhoneTakenByAnotherEmployee(phone, id);
    }

    public Boolean checkEmployeeJwtSubTakenByAnotherEmployee(UUID jwtSub, Long id) {
        return employeeRepository.checkJwtSubTakenByAnotherEmployee(jwtSub, id);
    }

}
