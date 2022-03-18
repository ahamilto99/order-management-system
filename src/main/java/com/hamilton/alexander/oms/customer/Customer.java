package com.hamilton.alexander.oms.customer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.hamilton.alexander.oms.address.Address;
import com.hamilton.alexander.oms.employee.Employee;
import com.hamilton.alexander.oms.order.Order;

@Entity
@Table(name = "customers")
public class Customer implements Serializable {

    private static final long serialVersionUID = 1383444899967644967L;

    private static final String SEQ_ID = "seq_customer_id";

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

    @NotBlank(message = "{validation.required}")
    @Pattern(regexp = "^(\\d{3}-){2}\\d{4}$", message = "{validation.phoneNumber}")
    private String phone;

    @NotNull(message = "{validation.required}")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses = new ArrayList<>();

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
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        return (obj instanceof Customer c) ? (id != null && id.equals(c.id)) : false;
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
