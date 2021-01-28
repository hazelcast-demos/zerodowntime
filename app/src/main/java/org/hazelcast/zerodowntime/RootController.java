package org.hazelcast.zerodowntime;

import org.hazelcast.zerodowntime.customer.CustomerRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {

    private final CustomerRepository repository;

    public RootController(CustomerRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public String displayLogin(Model model) {
        var customers = repository.findAll();
        model.addAttribute("customers", customers);
        return "login";
    }
}