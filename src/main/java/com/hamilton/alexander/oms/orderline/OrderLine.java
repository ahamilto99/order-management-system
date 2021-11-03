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

@Entity
@Table(name = "order_lines")
public class OrderLine implements Serializable {

    private static final long serialVersionUID = 5099802592880092434L;

    @EmbeddedId
    private OrderLineId id;
    
    @NotNull(message = "Order ID is required")
    @MapsId("orderId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @NotNull(message = "Quantity is required")
    @Range(min = 0, max = Integer.MAX_VALUE, message = "Quantity must be between 0 and " + Integer.MAX_VALUE)
    private Integer quantity;

    @NotNull(message = "MSRP is required")
    private BigDecimal msrp;

    @NotNull(message = "Product is required")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public OrderLine() {
    }

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
        return 2021;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        return id != null && id.equals(((OrderLine) obj).id);
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
