package com.hamilton.alexander.oms.employee;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Version;

import com.hamilton.alexander.oms.customer.Customer;

@Entity
@Table(name = "employees")
public class Employee implements Serializable {

    private static final long serialVersionUID = -867940333075357163L;

    private static final String SEQ_EMPLOYEES_ID = "seq_employees_id";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_EMPLOYEES_ID)
    @SequenceGenerator(name = SEQ_EMPLOYEES_ID, sequenceName = SEQ_EMPLOYEES_ID, initialValue = 1, allocationSize = 1)
    private Long id;

    @NotBlank(message = "Username is required")
    @Size(max = 10, message = "Username cannot be more than 10 characters")
    private String username;

    @NotBlank(message = "First name is required")
    @Size(max = 30, message = "First name cannot be more than 30 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 50, message = "Last name cannot be more than 50 characters")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Size(max = 150, message = "Email cannot be more than 150 characters")
    @Email
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "(\\d{3}-){2}\\d{4}", message = "Phone number must be in the following format: ###-###-####")
    private String phone;
    
    @OneToMany(mappedBy = "employee")
    private List<Customer> customers = new ArrayList<>();

    @Version
    private short version;

    public Employee() {
    }
    
    public void addCustomer(Customer customer) {
        customer.setEmployee(this);
        customers.add(customer);
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Employee [id=");
        builder.append(id);
        builder.append(", username=");
        builder.append(username);
        builder.append(", firstName=");
        builder.append(firstName);
        builder.append(", lastName=");
        builder.append(lastName);
        builder.append(", email=");
        builder.append(email);
        builder.append(", phone=");
        builder.append(phone);
        builder.append("]");
        return builder.toString();
    }

}
