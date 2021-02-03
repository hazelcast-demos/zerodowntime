package org.hazelcast.zerodowntime.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "CART_ID")
    private List<CartLine> cartLines;

    private LocalDateTime lastModified;

    public Cart() {}

    public Cart(Long id) {
        this.id = id;
    }

    Long getId() {
        return id;
    }

    public List<CartLine> getCartLines() {
        return cartLines;
    }

    public void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }

    public void addCartLine(CartLine cartLine) {
        if (cartLines == null) {
            cartLines = new ArrayList<>();
        }
        cartLines.add(cartLine);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return Objects.equals(id, cart.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}