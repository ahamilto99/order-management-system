package com.hamilton.alexander.oms.employee;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "employees")
public class Employee implements Serializable {

    private static final long serialVersionUID = -867940333075357163L;

    @Id
    private UUID id;

    @NotBlank(message = "The first name field is required")
    @Size(max = 30, message = "The first name field cannot be more than 30 characters")
    private String firstName;

    @NotBlank(message = "The last name field is required")
    @Size(max = 50, message = "The last name field cannot be more than 50 characters")
    private String lastName;

    @NotBlank(message = "The email field is required")
    @Size(max = 150, message = "The email field cannot be more than 150 characters")
    @Email
    private String email;

    @NotBlank(message = "The phone number field is required")
    @Pattern(regexp = "(\\d{3}-){2}\\d{4}", message = "The phone number field must be in the following format: ###-###-####")
    private String phone;

    public Employee() {
    }
    
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Employee [id=");
        builder.append(id);
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
