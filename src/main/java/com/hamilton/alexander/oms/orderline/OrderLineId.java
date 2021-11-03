package com.hamilton.alexander.oms.orderline;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class OrderLineId implements Serializable {
    
    private static final long serialVersionUID = -8466155043886149672L;

    @Column(name = "order_id")
    private UUID orderId;
    
    @Column(name = "line_number")
    private Short lineNumber;
    
    public OrderLineId() {
    }

    public OrderLineId(UUID orderId, Short lineNumber) {
        this.orderId = orderId;
        this.lineNumber = lineNumber;
    }

    public UUID getOrder() {
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
        
        if (obj == null || getClass() != obj.getClass())
            return false;
        
        OrderLineId other = (OrderLineId) obj;
        if  (Objects.equals(orderId, other.orderId) || !Objects.equals(lineNumber, other.lineNumber))
            return false;
        
        return true;
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
