package com.hamilton.alexander.oms.product;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

@Entity
@Table(name = "products")
public class Product {

    private static final String SEQ_PRODUCTS_ID = "seq_products_id";

    @Id
    @SequenceGenerator(name = SEQ_PRODUCTS_ID, sequenceName = SEQ_PRODUCTS_ID, initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_PRODUCTS_ID)
    private Long id;

    @NotBlank(message = "The name field is required")
    @Size(max = 100, message = "The name field cannot have more than 100 characters")
    private String name;

    @NotBlank(message = "The description field is required")
    @Size(max = 255, message = "The description field cannot have more than 255 characters")
    private String description;

    @NotNull(message = "The msrp field is required")
    @Positive(message = "The msrp field must be positive")
    private BigDecimal msrp;

    @NotNull(message = "The inventory count field is requried")
    @Range(min = 0, max = Integer.MAX_VALUE, message = "The inventory count field must be between 0 and " + Integer.MAX_VALUE)
    private Integer inventoryCount;

    public Product() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
