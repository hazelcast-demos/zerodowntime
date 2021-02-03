package org.hazelcast.zerodowntime.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CartLineId implements Serializable {

    @Column(name = "CART_ID")
    private Long cartId;

    @ManyToOne
    private Product product;

    /** Mandated by JPA. */
    protected CartLineId() {}

    public CartLineId(Long cartId, Long productId) {
        this.cartId = cartId;
        this.product = new Product(productId);
    }

    public Product getProduct() {
        return product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartLineId that = (CartLineId) o;
        return Objects.equals(cartId, that.cartId) && Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartId, product);
    }
}