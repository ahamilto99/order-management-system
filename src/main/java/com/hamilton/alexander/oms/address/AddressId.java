package com.hamilton.alexander.oms.address;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.hamilton.alexander.oms.customer.Customer;

@Embeddable
public class AddressId implements Serializable {

    private static final long serialVersionUID = 2861741520871313121L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "address_type")
    private Character addressType;

    public AddressId() {
    }

    public AddressId(Customer customer, Character addressType) {
        this.customer = customer;
        this.addressType = addressType;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Character getAddressType() {
        return addressType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(addressType, customer);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        AddressId other = (AddressId) obj;
        if (!Objects.equals(customer, other.customer) || Objects.equals(addressType, other.addressType))
            return false;

        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("AddressId [customer=");
        builder.append(customer.getId());
        builder.append(", addressType=");
        builder.append(addressType);
        builder.append("]");
        return builder.toString();
    }

}
