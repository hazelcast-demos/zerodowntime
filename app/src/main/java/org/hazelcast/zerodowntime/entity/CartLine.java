package org.hazelcast.zerodowntime.entity;

import javax.persistence.*;

@Entity
public class CartLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;

    @ManyToOne
    private Product product;

    /** Mandated by JPA. */
    protected CartLine() {}

    public void incrementQuantity() {
        quantity++;
    }

    public CartLine(Product product) {
        this.product = product;
        this.quantity = 1;
    }

    public int getQuantity() {
        return quantity;
    }

    public Product getProduct() {
        return product;
    }
}