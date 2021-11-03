package com.hamilton.alexander.oms.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import com.hamilton.alexander.oms.customer.Customer;
import com.hamilton.alexander.oms.orderline.OrderLine;

@Entity
@Table(name = "orders")
public class Order implements Serializable {

    private static final long serialVersionUID = -8176070068460078261L;

    @Id
    private UUID id;

    @NotNull(message = "Order timestamp is required")
    private LocalDateTime orderTimestamp;

    @Size(max = 255)
    private String notes;

    @NotNull(message = "Subtotal is required")
    @Positive(message = "Subtotal must be positive")
    private BigDecimal subtotal;

    @PositiveOrZero(message = "Discount cannot be negative")
    @DecimalMax(value = "1.00", message = "Discount cannot be greater than 1")
    private BigDecimal discount;

    @NotNull(message = "Tax is required")
    @PositiveOrZero(message = "Tax cannot be negative")
    private BigDecimal tax;

    @NotNull(message = "Total is required")
    @Positive(message = "Total must be positive")
    private BigDecimal total;

    @NotNull(message = "Customer is required")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @NotEmpty(message = "Order must have at least one order line")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "order")
    private List<OrderLine> orderLines = new ArrayList<>();

    public Order() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void addOrderLine(OrderLine orderLine) {
        orderLine.setOrder(this);
        orderLines.add(orderLine);
    }

    public void removeOrderLine(OrderLine orderLine) {
        orderLine.setOrder(null);
        orderLines.remove(orderLine);
    }

    public void removeAllOrderLines() {
        Iterator<OrderLine> iter = orderLines.iterator();
        while (iter.hasNext()) {
            OrderLine orderLine = iter.next();
            orderLine.setOrder(null);
            iter.remove();
        }
    }

    public LocalDateTime getOrderTimestamp() {
        return orderTimestamp;
    }

    public void setOrderTimestamp(LocalDateTime orderTimestamp) {
        this.orderTimestamp = orderTimestamp;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLine> orderLines) {
        this.orderLines = orderLines;
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

        return id != null && id.equals(((Order) obj).id);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Order [id=");
        builder.append(id);
        builder.append(", orderTimestamp=");
        builder.append(orderTimestamp);
        builder.append(", notes=");
        builder.append(notes);
        builder.append(", subtotal=");
        builder.append(subtotal);
        builder.append(", discount=");
        builder.append(discount);
        builder.append(", tax=");
        builder.append(tax);
        builder.append(", total=");
        builder.append(total);
        builder.append("]");
        return builder.toString();
    }

}
