package com.hamilton.alexander.oms.product.audit;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record ProductRecord(
        Long id,
        String name,
        String description,
        BigDecimal msrp,
        Integer inventoryCount
) implements Serializable {

    // @formatter:off
    @JsonCreator
    public ProductRecord(
            @JsonProperty("id") Long id,
            @JsonProperty("name") String name,
            @JsonProperty("description") String description,
            @JsonProperty("msrp") String msrp,
            @JsonProperty("inventoryCount") String inventoryCount) {
        this(
                Long.valueOf(id), 
                name,
                description,
                new BigDecimal(msrp), 
                Integer.valueOf(inventoryCount)
        );
    }
    // @formatter:on

}
