package com.hamilton.alexander.oms.customer;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

    @GetMapping("/employees/{employeeId}/customers")
    public Page<?> getCustomersForEmployee(@PathVariable Long employeeId) {
        return null;
    }
    
}
