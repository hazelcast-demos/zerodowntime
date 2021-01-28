package org.hazelcast.zerodowntime.checkout;

import org.hazelcast.zerodowntime.entity.CartLine;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CheckoutView {

    private final List<CartLineView> cartLines;
    private final BigDecimal price;

    public CheckoutView(List<CartLine> cartLines) {
        this.cartLines = cartLines.stream()
                .map(CartLineView::new)
                .collect(Collectors.toList());
        // We are iterating through the lines twice
        // We are valuing readability over performance here
        this.price = this.cartLines.stream()
                .map(CartLineView::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<CartLineView> getCartLines() {
        return Collections.unmodifiableList(cartLines);
    }

    public BigDecimal getPrice() {
        return price;
    }

    public static class CartLineView {

        private final String name;
        private final Long id;
        private final int quantity;
        private final BigDecimal price;

        public CartLineView(CartLine cartLine) {
            var product = cartLine.getId().getProduct();
            this.name = product.getName();
            this.id = product.getId();
            this.quantity = cartLine.getQuantity();
            this.price = product.getPrice().multiply(new BigDecimal(this.quantity));
        }

        public String getName() {
            return name;
        }

        public Long getId() {
            return id;
        }

        public int getQuantity() {
            return quantity;
        }

        public BigDecimal getPrice() {
            return price;
        }
    }
}