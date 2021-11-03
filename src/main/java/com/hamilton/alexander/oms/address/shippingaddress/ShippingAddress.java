package com.hamilton.alexander.oms.address.shippingaddress;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.hamilton.alexander.oms.address.Address;

@Entity
@DiscriminatorValue("S")
public class ShippingAddress extends Address implements Serializable {

    private static final long serialVersionUID = -2882302314293652839L;

}
