package com.hamilton.alexander.oms.address;

import java.io.Serializable;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

@Entity
@MappedSuperclass
@Table(name = "addresses")
@DiscriminatorColumn(name = "address_type", columnDefinition = "CHAR(1)" , discriminatorType = DiscriminatorType.CHAR)
public class Address implements Serializable {

    private static final long serialVersionUID = 8544167295614544103L;

    @EmbeddedId
    private AddressId id;

    private Boolean shippingBillingSame = false;

    @NotBlank(message = "Street address is required")
    @Size(max = 150, message = "Street address cannot be more than 150 characters")
    private String streetAddress;

    @Range(min = 1, max = 99_999, message = "PO box must be between 1 and 99_999")
    private Integer poBox;

    @NotBlank(message = "City is required")
    @Size(max = 50, message = "City cannot be more than 50 characters")
    private String city;

    @NotNull(message = "Province is required")
    @Enumerated(EnumType.STRING)
    private Province province;

    @NotBlank(message = "Postal code is required")
    @Size(min = 7, max = 7, message = "Postal code cannot be more than 7 characters (including the whitespace)")
    @Pattern(regexp = "([A-Z]\\d[A-Z])\\s(\\d[A-Z]\\d)", message = "Postal code must be in the following format: A#A #A#")
    private String postalCode;

    @Version
    public short version;

    public Address() {
    }

    public AddressId getId() {
        return id;
    }

    public void setId(AddressId id) {
        this.id = id;
    }

    public Boolean getShippingBillingSame() {
        return shippingBillingSame;
    }

    public void setShippingBillingSame(Boolean shippingBillingSame) {
        this.shippingBillingSame = shippingBillingSame;
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
        builder.append(", shippingBillingSame=");
        builder.append(shippingBillingSame);
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
