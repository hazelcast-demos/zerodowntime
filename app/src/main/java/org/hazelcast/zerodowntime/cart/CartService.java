package org.hazelcast.zerodowntime.cart;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.hazelcast.zerodowntime.customer.CustomerRepository;
import org.hazelcast.zerodowntime.customer.CustomerView;
import org.hazelcast.zerodowntime.entity.Cart;
import org.hazelcast.zerodowntime.entity.CartLine;
import org.hazelcast.zerodowntime.entity.Customer;
import org.hazelcast.zerodowntime.operation.OperationContext;
import org.springframework.session.FindByIndexNameSessionRepository;

public class CartService {

    private final CustomerRepository customerRepository;
    private final CartRepository cartRepository;
    private final OperationContext context;

    public CartService(CustomerRepository customerRepository,
                       CartRepository cartRepository,
                       OperationContext context) {
        this.customerRepository = customerRepository;
        this.cartRepository = cartRepository;
        this.context = context;
    }

    public CartView view(HttpSession session) {
        var itemCount = getCustomerId(session).stream()
                .map(customerRepository::findByIdWithCartLines)
                .map(Customer::getCart)
                .filter(Objects::nonNull)
                .flatMap(cart -> cart.getCartLines().stream())
                .map(CartLine::getQuantity)
                .mapToInt(quantity -> quantity)
                .sum();
        return new CartView(itemCount, context);
    }

    public CartView add(HttpSession session, Long productId) {
        var customer = getCustomerId(session)
                .stream()
                .map(customerRepository::findByIdWithCartLines)
                .findAny()
                .orElseThrow();
        var cart = customer.getCart();
        if (cart == null) {
            var newCart = new Cart();
            customer.setCart(newCart);
            cartRepository.saveAndFlush(newCart);
            cart = newCart;
            addToCart(cart, productId).run();
        } else {
            Optional.ofNullable(cart.getCartLines())
                    .stream()
                    .flatMap(Collection::stream)
                    .filter(line -> line.getProduct().getId().equals(productId))
                    .findAny()
                    .ifPresentOrElse(
                            CartLine::incrementQuantity,
                            addToCart(cart, productId)
                    );
        }
        cart.setLastModified(LocalDateTime.now());
        customerRepository.saveAndFlush(customer);
        return view(session);
    }

    private Runnable addToCart(Cart cart, Long productId) {
        return () -> {
            var cartLine = new CartLine(cart, productId); // Won't probably work
            cart.addCartLine(cartLine);
        };
    }

    private Optional<Long> getCustomerId(HttpSession session) {
        var customer = (CustomerView) session.getAttribute(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME);
        return Optional.ofNullable(customer).map(CustomerView::getId);
    }
}