package org.hazelcast.zerodowntime.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class CartLine {

    @EmbeddedId
    private CartLineId id;

    private int quantity;

    /** Mandated by JPA. */
    protected CartLine() {}

    public CartLine(Cart cart, Long productId) {
        this.id = new CartLineId(cart.getId(), productId);
        this.quantity = 1;
    }

    public void incrementQuantity() {
        quantity++;
    }

    public int getQuantity() {
        return quantity;
    }

    public Product getProduct() {
        return id.getProduct();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartLine cartLine = (CartLine) o;
        return Objects.equals(id, cartLine.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}