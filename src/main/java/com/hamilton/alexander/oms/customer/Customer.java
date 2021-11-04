package com.hamilton.alexander.oms.customer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.hamilton.alexander.oms.address.Address;
import com.hamilton.alexander.oms.employee.Employee;
import com.hamilton.alexander.oms.order.Order;

@Entity
@Table(name = "customers")
public class Customer implements Serializable {

    private static final long serialVersionUID = 8119949260334311924L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "pg-uuid")
    @GenericGenerator( // @formatter:off
            name = "pg-uuid", 
            strategy = "uuid2",
            parameters = @Parameter(
                    name = "uuid_gen_strategy_class", 
                    value = "com.vladmihalcea.book.hpjp.hibernate.identifier.uuid.PostgreSQLUUIDGenerationStrategy")) // @formatter:on
    private UUID id;

    @NotBlank(message = "The first name field is required")
    @Size(max = 30, message = "The first name cannot be more than 30 characters")
    private String firstName;

    @NotBlank(message = "The last name field is required")
    @Size(max = 50, message = "The last name cannot be more than 50 characters")
    private String lastName;

    @NotBlank(message = "The email field is required")
    @Size(max = 150, message = "The email cannot be more than 150 characters")
    @Email
    private String email;

    @NotBlank(message = "The phone number field is required")
    @Pattern(regexp = "(\\d{3}-){2}\\d{4}", message = "The phone number filed must be in the following format: ###-###-####")
    private String phone;

    @NotNull(message = "The employee field is required")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses = new ArrayList<>();

    @Version
    private short version;

    public Customer() {
    }

    public void addOrder(Order order) {
        order.setCustomer(this);
        orders.add(order);
    }

    public void removeOrder(Order order) {
        order.setCustomer(null);
        orders.remove(order);
    }

    public void removeAllOrders() {
        Iterator<Order> iter = orders.iterator();
        while (iter.hasNext()) {
            Order order = iter.next();
            order.setCustomer(null);
            iter.remove();
        }
    }

    public void addAddress(Address address) {
        address.setCustomer(this);
        addresses.remove(address);
    }

    public void removeAddress(Address address) {
        address.setCustomer(null);
        addresses.remove(address);
    }

    public void removeAllAddresses() {
        Iterator<Address> iter = addresses.iterator();
        while (iter.hasNext()) {
            Address address = iter.next();
            address.setCustomer(null);
            iter.remove();
        }
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

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    @Override
    public int hashCode() {
        return 2021;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        return id != null && id.equals(((Customer) obj).id);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Customer [id=");
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
