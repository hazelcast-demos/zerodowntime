package org.hazelcast.zerodowntime.checkout;

import org.hazelcast.zerodowntime.cart.CartLinesRepository;
import org.hazelcast.zerodowntime.customer.CustomerView;
import org.hazelcast.zerodowntime.entity.CartLine;
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

    private final CartLinesRepository repository;

    public CheckoutController(CartLinesRepository repository) {
        this.repository = repository;
    }

    @GetMapping("checkout")
    public String displayCheckout(HttpSession session, Model model) {
        var customer = (CustomerView) session.getAttribute(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME);
        var cartLines = repository.findAllByCustomer(customer.getId());
        model.addAttribute("checkout", new CheckoutView(cartLines));
        return "checkout";
    }

    @DeleteMapping("/rest/checkout/delete/{productId}")
    @ResponseBody
    public BigDecimal deleteCartLine(@PathVariable Long productId, HttpSession session) {
        var customer = (CustomerView) session.getAttribute(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME);
        var customerId = customer.getId();
        repository.delete(new CartLine(customerId, productId));
        var cartLines = repository.findAllByCustomer(customerId);
        return new CheckoutView(cartLines).getPrice();
    }
}