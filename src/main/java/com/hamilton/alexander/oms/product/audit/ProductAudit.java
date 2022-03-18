package com.hamilton.alexander.oms.product.audit;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.TypeDef;
import org.springframework.data.annotation.Immutable;

import com.vladmihalcea.hibernate.type.json.JsonType;

@Entity
@Immutable // no Serializable b/c all queries return DTO Projections
@Table(name = "products_audit")
@TypeDef(typeClass = JsonType.class, defaultForType = ProductRecord.class)
public class ProductAudit {

    private static final String SEQ_PRODUCTS_AUDIT_ID = "seq_product_audit_id";

    @Id
    @SequenceGenerator(name = SEQ_PRODUCTS_AUDIT_ID, sequenceName = SEQ_PRODUCTS_AUDIT_ID, initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_PRODUCTS_AUDIT_ID)
    private Long id;

    private UUID productId;

    @Column(name = "old_row", columnDefinition = "JSON")
    private ProductRecord oldProductRow;

    @Column(name = "new_row", columnDefinition = "JSON")
    private ProductRecord newProductRow;

    private Integer inventoryChange;

    private String revisionType;

    private LocalDateTime revisionTimestamp;

    public ProductAudit() {
    }

    public Long getId() {
        return id;
    }

    public UUID getProductId() {
        return productId;
    }

    public ProductRecord getOldProductRow() {
        return oldProductRow;
    }

    public ProductRecord getNewProductRow() {
        return newProductRow;
    }

    public Integer getInventoryChange() {
        return inventoryChange;
    }

    public String getRevisionType() {
        return revisionType;
    }

    public LocalDateTime getRevisionTimestamp() {
        return revisionTimestamp;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ProductAudit [id=");
        builder.append(id);
        builder.append(", productId=");
        builder.append(productId);
        builder.append(", oldProductRow=");
        builder.append(oldProductRow);
        builder.append(", newProductRow=");
        builder.append(newProductRow);
        builder.append(", inventoryChange=");
        builder.append(inventoryChange);
        builder.append(", revisionType=");
        builder.append(revisionType);
        builder.append(", revisionTimestamp=");
        builder.append(revisionTimestamp);
        builder.append("]");
        return builder.toString();
    }

}
