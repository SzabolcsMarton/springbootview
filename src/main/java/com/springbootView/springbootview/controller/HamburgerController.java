package com.springbootView.springbootview.controller;

import com.springbootView.springbootview.services.HamburgerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HamburgerController {

    private HamburgerService hamburgerService;

    @Autowired
    public HamburgerController(HamburgerService hamburgerService) {
        this.hamburgerService = hamburgerService;
    }

    @RequestMapping ("/")
    public String getHomeView(){
        return "index";
    }

    @RequestMapping ("/about")
    public String getAboutView(){
        return "about";
    }

    @RequestMapping ("/products")
    public String getProductsView(Model model){
        model.addAttribute("allhamburger", hamburgerService.getAllBurger());
        return "products";
    }

    @RequestMapping ("/order")
    public String getOrdersView(){
        return "order";
    }

    @RequestMapping ("/admin")
    public String getAdminView(){
        return "admin";
    }

    @RequestMapping ("/login")
    public String getLoginView(){
        return "login";
    }

    @RequestMapping ("/register")
    public String getRegisterView(){
        return "register";
    }
}
