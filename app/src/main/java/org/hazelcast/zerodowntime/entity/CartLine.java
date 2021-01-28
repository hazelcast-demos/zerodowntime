package org.hazelcast.zerodowntime.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import java.io.Serializable;

@Entity
@NamedQuery(
    name = "CartLine.findAllByCustomer",
    query = "SELECT line FROM CartLine line WHERE line.id.customer.id = ?1"
)
public class CartLine implements Serializable {

    @EmbeddedId
    private CartLineId id;
    private int quantity;

    /** Mandated by JPA. */
    protected CartLine() {}

    public CartLine(Long customerId, Long productId) {
        id = new CartLineId(customerId, productId);
        this.quantity = 0;
    }

    public void incrementQuantity() {
        quantity++;
    }

    public CartLineId getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }
}