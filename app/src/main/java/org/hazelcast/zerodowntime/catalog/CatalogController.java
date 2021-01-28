package org.hazelcast.zerodowntime.catalog;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CatalogController {

    private final Catalog catalog;

    public CatalogController(Catalog catalog) {
        this.catalog = catalog;
    }

    @GetMapping("/catalog")
    public String display(Model model) {
        var products = catalog.findAll();
        model.addAttribute("products", products);
        return "catalog";
    }
}