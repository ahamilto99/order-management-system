package com.hamilton.alexander.oms.orderline;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import com.hamilton.alexander.oms.order.Order;
import com.hamilton.alexander.oms.product.Product;

/*
 * TODO: WHEN CREATING THE SERVICE LAYER FOR OrderLine, REMEMBER THE DB HAS STORED PROCS FOR ALL WRITES
 */

@Entity
@Table(name = "order_lines")
public class OrderLine implements Serializable {

    private static final long serialVersionUID = -2474675650110035734L;

    @EmbeddedId
    private OrderLineId id;
    
    @NotNull(message = "{validation.required}")
    @MapsId("orderId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @NotNull(message = "{validation.required}")
    @Range(min = 0, max = Integer.MAX_VALUE, message = "{validation.range}")
    private Integer quantity;

    @NotNull(message = "{validation.required}")
    private BigDecimal msrp;

    @NotNull(message = "{validation.required}")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public OrderLineId getId() {
        return id;
    }
    
    public void setId(OrderLineId id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getMsrp() {
        return msrp;
    }

    public void setMsrp(BigDecimal msrp) {
        this.msrp = msrp;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        return (obj instanceof OrderLine ol) ? (id != null && id.equals(ol.id)) : false;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("OrderLine [id=");
        builder.append(id);
        builder.append(", quantity=");
        builder.append(quantity);
        builder.append(", msrp=");
        builder.append(msrp);
        builder.append(", product=");
        builder.append(product);
        builder.append("]");
        return builder.toString();
    }

}
