package com.hamilton.alexander.oms.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.hamilton.alexander.oms.customer.Customer;
import com.hamilton.alexander.oms.orderline.OrderLine;

/*
 * TODO: WHEN CREATING SERVICE LAYER FOR Order, REMEMBER: THE DB HAS A STORED PROC FOR ALL DELETES
 * TODO: ONLY CREATE ORDER OPTION USING JPQL
 * TODO: BI-DIRECTIONAL WITH OrderLine HAS TO BE RE-THOUGHT B/C OF STORED PROCs
 */

@Entity
@Table(name = "orders")
public class Order implements Serializable {
    
    private static final long serialVersionUID = 8891027231064748161L;

    private static final String SEQ_ID = "seq_order_id";

    @Id
    @SequenceGenerator(name = SEQ_ID, sequenceName = SEQ_ID, initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_ID)
    private Long id;

    @NotNull(message = "{validation.required}")
    private LocalDateTime orderTimestamp;

    @Size(max = 255)
    private String notes;

    @NotNull(message = "{validation.required}")
    @Positive(message = "{validation.positiveNumber}")
    private BigDecimal total;

    @NotNull(message = "{validation.required}")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @NotEmpty(message = "{validation.orderLineRequired}")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "order")
    private List<OrderLine> orderLines = new ArrayList<>();

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

    public Long getId() {
        return id;
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
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        return (obj instanceof Order o) ? (id != null && id.equals(o.id)) : false;
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
        builder.append(", total=");
        builder.append(total);
        builder.append("]");
        return builder.toString();
    }

}
