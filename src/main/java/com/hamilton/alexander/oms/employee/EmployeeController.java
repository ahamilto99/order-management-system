package com.hamilton.alexander.oms.employee;

import java.time.Instant;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private static final String UNIQUE_VAL = "Value must be unique";

    private static final String TAKEN_VAL = "Value is taken";

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public Page<EmployeePrj> getEmployees(@RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int size,
            @RequestParam(defaultValue = "lastName", required = false) String sortBy,
            @RequestParam(defaultValue = "asc", required = false) String order) {
        var pageable = sortBy.equals("lastName") ? PageRequest.of(page, size, Sort.Direction.fromString(order), sortBy, "firstName")
                : PageRequest.of(page, size, Sort.Direction.fromString(order), sortBy);

        return employeeService.findPageOfEmployeePrjs(pageable);
    }

    @GetMapping("/{id}")
    public EmployeePrj getOneEmployee(@PathVariable Long id) {
        return employeeService.findEmployeePrjById(id);
    }

    @PostMapping
    public ResponseEntity<?> createEmployee(@RequestBody @Valid Employee employee, BindingResult bindingResult, HttpServletRequest request,
            UriComponentsBuilder uriBuilder) {
        Assert.isNull(employee.getId(), "Database supplies the ID");

        if (employeeService.checkEmployeeEmailTaken(employee.getEmail())) {
            bindingResult.addError(new FieldError("employee", "email", UNIQUE_VAL));
        }
        if (employeeService.checkEmployeePhoneTaken(employee.getPhone())) {
            bindingResult.addError(new FieldError("employee", "phone", UNIQUE_VAL));
        }
        if (employeeService.checkEmployeeJwtSubTaken(employee.getJwtSub())) {
            bindingResult.addError(new FieldError("employee", "jwtSub", UNIQUE_VAL));
        }

        if (bindingResult.hasFieldErrors()) {
            return buildInvalidRequestBodyResponse(bindingResult, request);
        }

        var newEmployeeId = employeeService.saveEmployee(employee);
        var locationUri = uriBuilder.path("/employees/{id}").buildAndExpand(newEmployeeId).toUri();

        return ResponseEntity.created(locationUri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable Long id, @RequestBody @Valid Employee employee, BindingResult bindingResult,
            HttpServletRequest request, UriComponentsBuilder uriBuilder) {
        Assert.isTrue(id.equals(employee.getId()), "Path variable and ID field must match");

        if (employeeService.checkEmployeeEmailTakenByAnotherEmployee(employee.getEmail(), id)) {
            bindingResult.addError(new FieldError("employee", "email", TAKEN_VAL));
        }
        if (employeeService.checkEmployeePhoneTakenByAnotherEmployee(employee.getPhone(), id)) {
            bindingResult.addError(new FieldError("employee", "phone", TAKEN_VAL));
        }
        if (employeeService.checkEmployeeJwtSubTakenByAnotherEmployee(employee.getJwtSub(), id)) {
            bindingResult.addError(new FieldError("employee", "jwtSub", TAKEN_VAL));
        }

        if (bindingResult.hasFieldErrors()) {
            return buildInvalidRequestBodyResponse(bindingResult, request);
        }

        var updatedEmployeeId = employeeService.saveEmployee(employee);
        var locationUri = uriBuilder.path("/employees/{id}").buildAndExpand(updatedEmployeeId).toUri();

        return ResponseEntity.noContent().location(locationUri).build();
    }

    private ResponseEntity<Map<String, Object>> buildInvalidRequestBodyResponse(BindingResult bindingResult, HttpServletRequest request) {
        var status = HttpStatus.BAD_REQUEST;
        var errorMap = new LinkedHashMap<String, Object>();

        errorMap.put("timestamp", Instant.now());
        errorMap.put("status", status.value());

        var fieldErrorMap = new HashMap<String, String>();
        bindingResult.getFieldErrors().forEach(fe -> fieldErrorMap.put(fe.getField(), fe.getDefaultMessage()));
        errorMap.put("errors", fieldErrorMap);

        errorMap.put("message", status.getReasonPhrase());
        errorMap.put("path", request.getRequestURI());

        return new ResponseEntity<Map<String, Object>>(errorMap, status);
    }

}
