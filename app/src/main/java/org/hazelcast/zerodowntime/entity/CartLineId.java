package org.hazelcast.zerodowntime.entity;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CartLineId implements Serializable {

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Product product;

    /** Mandated by JPA. */
    protected CartLineId() {}

    public CartLineId(Long customerId, Long productId) {
        this.customer = new Customer(customerId);
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
        return Objects.equals(customer, that.customer) && Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, product);
    }
}