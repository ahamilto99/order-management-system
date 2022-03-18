package com.hamilton.alexander.oms.product;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

@Entity
@Table(name = "products")
public class Product implements Serializable {

    private static final long serialVersionUID = 6344494816766069587L;

    @Id
    // db generates the ID
    @Column(columnDefinition = "UUID", nullable = false)
    private UUID id;

    @NotBlank(message = "{validation.required}")
    @Size(max = 100, message = "{validation.size}")
    private String name;

    @NotBlank(message = "{validation.required}")
    @Size(max = 255, message = "{validation.size}")
    private String description;

    @NotNull(message = "{validation.required}")
    @Positive(message = "{validation.positiveNumber}")
    @DecimalMax(value = "999,999,999.99", message = "{validation.decimalMax}")
    private BigDecimal msrp;

    @NotNull(message = "{validation.required}")
    @Range(min = 0, max = Integer.MAX_VALUE, message = "{validation.range}")
    private Integer inventoryCount;

    public Product() {
    }

    public UUID getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getMsrp() {
        return msrp;
    }

    public void setMsrp(BigDecimal msrp) {
        this.msrp = msrp;
    }

    public Integer getInventoryCount() {
        return inventoryCount;
    }

    public void setInventoryCount(Integer inventoryCount) {
        this.inventoryCount = inventoryCount;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Product [id=");
        builder.append(id);
        builder.append(", name=");
        builder.append(name);
        builder.append(", description=");
        builder.append(description);
        builder.append(", msrp=");
        builder.append(msrp);
        builder.append(", inventoryCount=");
        builder.append(inventoryCount);
        builder.append("]");
        return builder.toString();
    }

}
