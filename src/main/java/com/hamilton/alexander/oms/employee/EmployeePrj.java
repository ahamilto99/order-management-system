package com.hamilton.alexander.oms.employee;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface EmployeePrj {

    Long getId();
    
    String getFirstName();
    
    String getLastName();
    
    String getEmail();
    
    String getPhone();

    UUID getJwtSub();
    
}
