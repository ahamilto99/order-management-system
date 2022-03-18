package com.hamilton.alexander.oms.address.billing;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import com.hamilton.alexander.oms.address.Address;

@Entity(name = "BillingAddress")
@DiscriminatorValue("BILLING")
public class BillingAddress extends Address implements Serializable {

    private static final long serialVersionUID = 1385443410799025106L;

    @NotNull(message = "{validation.required}")
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
        builder.append("BillingAddress [isAlsoShipping=");
        builder.append(isAlsoShipping);
        builder.append(", getId()=");
        builder.append(getId());
        builder.append(", getStreetAddress()=");
        builder.append(getStreetAddress());
        builder.append(", getPoBox()=");
        builder.append(getPoBox());
        builder.append(", getCity()=");
        builder.append(getCity());
        builder.append(", getProvince()=");
        builder.append(getProvince());
        builder.append(", getPostalCode()=");
        builder.append(getPostalCode());
        builder.append(", getCustomer()=");
        builder.append(getCustomer());
        builder.append(", getClass()=");
        builder.append(getClass());
        builder.append("]");
        return builder.toString();
    }

}
