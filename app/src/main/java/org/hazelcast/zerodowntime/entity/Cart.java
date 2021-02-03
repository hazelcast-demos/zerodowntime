package org.hazelcast.zerodowntime.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "CART_ID")
    private List<CartLine> cartLines;

    private LocalDateTime lastModified;

    public List<CartLine> getCartLines() {
        return cartLines;
    }

    public void addCartLine(CartLine cartLine) {
        if (cartLines == null) {
            cartLines = new ArrayList<>();
        }
        cartLines.add(cartLine);
    }

    public void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }
}