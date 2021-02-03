package org.hazelcast.zerodowntime.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private Cart cart;
    private String name;

    /**
     * Mandated by JPA.
     */
    protected Customer() {}

    Customer(Long id) {
        this.id = id;
    }

    public Cart getCart() {
        return cart;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}