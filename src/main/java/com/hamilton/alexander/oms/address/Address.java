package com.hamilton.alexander.oms.address;

import java.io.Serializable;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

import com.hamilton.alexander.oms.customer.Customer;

@Entity
@Table(name = "addresses")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "address_type", length = 1)
public class Address implements Serializable {

    private static final long serialVersionUID = 8544167295614544103L;

    private static final String SEQ_ADDRESSES_ID = "seq_addresses_id";

    @Id
    @SequenceGenerator(name = SEQ_ADDRESSES_ID, sequenceName = SEQ_ADDRESSES_ID, initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = SEQ_ADDRESSES_ID, strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotBlank(message = "The street address field is required")
    @Size(max = 150, message = "The street address cannot be more than 150 characters")
    private String streetAddress;

    @Range(min = 1, max = 99_999, message = "The po box must be between 1 and 99_999")
    private Integer poBox;

    @NotBlank(message = "The city field is required")
    @Size(max = 50, message = "The city cannot be more than 50 characters")
    private String city;

    @NotNull(message = "The province field is required")
    @Enumerated(EnumType.STRING)
    private Province province;

    @NotBlank(message = "The postal code field is required")
    @Size(min = 7, max = 7, message = "The postal code cannot be more than 7 characters (including the whitespace)")
    @Pattern(regexp = "([A-Z]\\d[A-Z])\\s(\\d[A-Z]\\d)", message = "The postal code must be in the following format: A#A #A#")
    private String postalCode;

    @NotNull(message = "The customer field is required")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Customer customer;

    @Version
    public short version;

    public Address() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        return 2021;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        return id != null && id.equals(((Address) obj).id);
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
