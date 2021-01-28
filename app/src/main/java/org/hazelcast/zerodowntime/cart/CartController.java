package org.hazelcast.zerodowntime.cart;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class CartController {

    private final CartService cart;

    public CartController(CartService cart) {
        this.cart = cart;
    }

    @PostMapping("/rest/cart/add/{productId}")
    @ResponseBody
    public CartView addToCart(HttpSession session, @PathVariable Long productId) {
        return cart.add(session, productId);
    }

    @GetMapping("/rest/cart")
    @ResponseBody
    public CartView getCart(HttpSession session) {
        return cart.view(session);
    }
}