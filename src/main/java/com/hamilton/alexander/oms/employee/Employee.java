package com.hamilton.alexander.oms.employee;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "employees")
public class Employee implements Serializable {

    private static final long serialVersionUID = 7327397258995463099L;

    private static final String SEQ_ID = "seq_employee_id";

    @Id
    @SequenceGenerator(name = SEQ_ID, sequenceName = SEQ_ID, initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_ID)
    private Long id;

    @NotBlank(message = "{validation.required}")
    @Size(max = 30, message = "{validation.size}")
    private String firstName;

    @NotBlank(message = "{validation.required}")
    @Size(max = 50, message = "{validation.size}")
    private String lastName;

    @NotBlank(message = "{validation.required}")
    @Size(max = 150, message = "{validation.size}")
    @Email(message = "{validation.email}")
    private String email;

    @Pattern(regexp = "^(\\d{3}-){2}\\d{4}$", message = "{validation.phoneNumber}")
    private String phone;

    @NotNull(message = "{validation.required}")
    private UUID jwtSub;

    public Long getId() {
        return id;
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

    public UUID getJwtSub() {
        return jwtSub;
    }

    public void setJwtSub(UUID jwtSub) {
        this.jwtSub = jwtSub;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        return (obj instanceof Employee e) ? (id != null && id.equals(e.id)) : false;
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
        builder.append(", jwtSub=");
        builder.append(jwtSub);
        builder.append("]");
        return builder.toString();
    }

}
