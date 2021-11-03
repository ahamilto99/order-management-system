package com.hamilton.alexander.oms.address.billingaddress;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.hamilton.alexander.oms.address.Address;

@Entity
@DiscriminatorValue("B")
public class BillingAddress extends Address implements Serializable{

    private static final long serialVersionUID = 8153650124241696933L;

}
