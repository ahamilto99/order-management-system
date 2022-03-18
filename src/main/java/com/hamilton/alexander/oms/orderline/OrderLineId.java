package com.hamilton.alexander.oms.orderline;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class OrderLineId implements Serializable {
    
    private static final long serialVersionUID = -8466155043886149672L;

    @Column(name = "order_id")
    private Long orderId;
    
    // db uses STORED PROCs to perform all writes to this field
    @Column(name = "line_number")
    private Short lineNumber;
    
    public OrderLineId() {
    }

    public OrderLineId(Long orderId, Short lineNumber) {
        this.orderId = orderId;
        this.lineNumber = lineNumber;
    }

    public Long getOrder() {
        return orderId;
    }

    public Short getLineNumber() {
        return lineNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lineNumber, orderId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        
        // TODO: REWRITE THIS USING JAVA 16's TYPE PATTERN MATCHING
        if (!(obj instanceof OrderLineId))
            return false;
        
        final OrderLineId other = (OrderLineId) obj;

        return (Objects.equals(orderId, other.orderId) && Objects.equals(lineNumber, other.lineNumber));
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("OrderLineId [order=");
        builder.append(orderId);
        builder.append(", lineNumber=");
        builder.append(lineNumber);
        builder.append("]");
        return builder.toString();
    }

}
