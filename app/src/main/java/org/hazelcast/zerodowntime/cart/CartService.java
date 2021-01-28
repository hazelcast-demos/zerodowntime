package org.hazelcast.zerodowntime.cart;

import org.hazelcast.zerodowntime.customer.CustomerView;
import org.hazelcast.zerodowntime.entity.CartLine;
import org.hazelcast.zerodowntime.entity.CartLineId;
import org.hazelcast.zerodowntime.operation.OperationContext;
import org.springframework.session.FindByIndexNameSessionRepository;

import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.Optional;

public class CartService {

    private final CartLinesRepository cartRepository;
    private final OperationContext context;

    public CartService(CartLinesRepository cartRepository, OperationContext context) {
        this.cartRepository = cartRepository;
        this.context = context;
    }

    public CartView view(HttpSession session) {
        var itemCount = getCustomerId(session).stream()
                .map(cartRepository::findAllByCustomer)
                .flatMap(Collection::stream)
                .map(CartLine::getQuantity)
                .mapToInt(quantity -> quantity)
                .sum();
        return new CartView(itemCount, context);
    }

    public CartView add(HttpSession session, Long productId) {
        var customerId = getCustomerId(session).orElseThrow();
        var cartLineId = new CartLineId(customerId, productId);
        var cartLine = cartRepository.findById(cartLineId)
                .orElse(new CartLine(customerId, productId));
        cartLine.incrementQuantity();
        cartRepository.saveAndFlush(cartLine);
        return view(session);
    }

    private Optional<Long> getCustomerId(HttpSession session) {
        var customer = (CustomerView) session.getAttribute(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME);
        return Optional.ofNullable(customer).map(CustomerView::getId);
    }
}