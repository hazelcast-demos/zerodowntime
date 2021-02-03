package org.hazelcast.zerodowntime.checkout;

import org.hazelcast.zerodowntime.cart.CartRepository;
import org.hazelcast.zerodowntime.customer.CustomerView;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

@Controller
public class CheckoutController {

    private final CartRepository repository;

    public CheckoutController(CartRepository repository) {
        this.repository = repository;
    }

    @GetMapping("checkout")
    public String displayCheckout(HttpSession session, Model model) {
        var customer = (CustomerView) session.getAttribute(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME);
        var cart = repository.findWithLinesByCustomerId(customer.getId());
        model.addAttribute("checkout", new CheckoutView(cart));
        return "checkout";
    }

    @DeleteMapping("/rest/checkout/delete/{productId}")
    @ResponseBody
    public BigDecimal deleteCartLine(@PathVariable Long productId, HttpSession session) {
        var customer = (CustomerView) session.getAttribute(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME);
        var customerId = customer.getId();
        repository.deleteByCustomerIdAndProductId(customerId, productId);
        var cart = repository.findWithLinesByCustomerId(customerId);
        return new CheckoutView(cart).getPrice();
    }
}