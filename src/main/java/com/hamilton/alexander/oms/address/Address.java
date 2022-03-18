package com.hamilton.alexander.oms.address;

import java.io.Serializable;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

import com.hamilton.alexander.oms.customer.Customer;

@Entity
@Table(name = "addresses")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "address_type", length = 8, discriminatorType = DiscriminatorType.STRING)
public class Address implements Serializable {

    private static final long serialVersionUID = -8862432326801491219L;

    private static final String ID_SEQ = "seq_address_id";

    @Id
    @SequenceGenerator(name = ID_SEQ, sequenceName = ID_SEQ, initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = ID_SEQ)
    private Long id;

    @NotBlank(message = "{validation.required}")
    @Size(max = 150, message = "{validation.size}")
    private String streetAddress;

    @Range(min = 1, max = 99_999, message = "{validation.range}")
    private Integer poBox;

    @NotBlank(message = "{validation.required}")
    @Size(max = 50, message = "{validation.size}")
    private String city;

    @NotBlank(message = "{validation.required}")
    @Enumerated(EnumType.STRING)
    private Province province;

    @NotBlank(message = "{validation.required}")
    @Pattern(regexp = "([A-Z]\\d[A-Z])\\s(\\d[A-Z]\\d)", message = "{validation.postalCode}")
    private String postalCode;

    @NotNull(message = "{validation.required}")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public Address() {
    }

    public Long getId() {
        return id;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public Integer getPoBox() {
        return poBox;
    }

    public void setPoBox(Integer poBox) {
        this.poBox = poBox;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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

        return (obj instanceof Address a) ? (id != null && id.equals(a.id)) : false;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Address [id=");
        builder.append(id);
        builder.append(", streetAddress=");
        builder.append(streetAddress);
        builder.append(", poBox=");
        builder.append(poBox);
        builder.append(", city=");
        builder.append(city);
        builder.append(", province=");
        builder.append(province);
        builder.append(", postalCode=");
        builder.append(postalCode);
        builder.append("]");
        return builder.toString();
    }

}
