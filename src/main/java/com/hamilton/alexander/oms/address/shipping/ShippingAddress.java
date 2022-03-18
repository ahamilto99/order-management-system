package com.hamilton.alexander.oms.address.shipping;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.hamilton.alexander.oms.address.Address;

@Entity(name = "ShippingAddress")
@DiscriminatorValue("SHIPPING")
public class ShippingAddress extends Address implements Serializable {

    private static final long serialVersionUID = -3521062814807437636L;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ShippingAddress [getId()=");
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
        builder.append(", hashCode()=");
        builder.append(hashCode());
        builder.append(", toString()=");
        builder.append(super.toString());
        builder.append(", getClass()=");
        builder.append(getClass());
        builder.append("]");
        return builder.toString();
    }


}
