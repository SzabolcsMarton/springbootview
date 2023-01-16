package com.springbootView.springbootview.controller;

import com.springbootView.springbootview.services.HamburgerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/")
public class HamburgerController {

    private final HamburgerService hamburgerService;

    @Autowired
    public HamburgerController(HamburgerService hamburgerService) {
        this.hamburgerService = hamburgerService;
    }

    @GetMapping("/")
    public String getHomeView() {
        return "index";
    }

    @GetMapping("/about")
    public String getAboutView() {
        return "about";
    }

    @GetMapping("/products")
    public String getProductsView(Model model) {
        model.addAttribute("allhamburger", hamburgerService.getAllBurger());
        return "products";
    }

}
