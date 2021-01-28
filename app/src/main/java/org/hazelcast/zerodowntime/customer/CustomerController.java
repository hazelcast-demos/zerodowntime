package org.hazelcast.zerodowntime.customer;

import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CustomerController {

    private final CustomerRepository repository;

    public CustomerController(CustomerRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/rest/customer")
    @ResponseBody
    public List<CustomerView> list() {
        return repository.findAll()
                .stream()
                .map(CustomerView::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/customer/{id}")
    public String choose(HttpSession session, @PathVariable Long id, @RequestParam String to) {
        var customer = repository.findById(id).orElseThrow();
        var view = new CustomerView(customer);
        session.setAttribute(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME, view);
        return "redirect:/" + to;
    }
}