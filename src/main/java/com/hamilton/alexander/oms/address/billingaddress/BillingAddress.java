package com.hamilton.alexander.oms.address.billingaddress;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import com.hamilton.alexander.oms.address.Address;

@Entity
@DiscriminatorValue("B")
public class BillingAddress extends Address implements Serializable {

    private static final long serialVersionUID = 8153650124241696933L;

    @NotNull(message = "The is also shipping field is required")
    private Boolean isAlsoShipping;

    public Boolean getIsAlsoShipping() {
        return isAlsoShipping;
    }

    public void setIsAlsoShipping(Boolean isAlsoShipping) {
        this.isAlsoShipping = isAlsoShipping;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Address [id=");
        builder.append(getId());
        builder.append(", isAlsoShipping=");
        builder.append(isAlsoShipping);
        builder.append(", streetAddress=");
        builder.append(getStreetAddress());
        builder.append(", poBox=");
        builder.append(getPoBox());
        builder.append(", city=");
        builder.append(getCity());
        builder.append(", province=");
        builder.append(getProvince());
        builder.append(", postalCode=");
        builder.append(getPostalCode());
        builder.append("]");
        return builder.toString();
    }

}
