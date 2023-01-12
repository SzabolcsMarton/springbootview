package com.springbootView.springbootview.controller;

import com.springbootView.springbootview.model.Cart;
import com.springbootView.springbootview.services.HamburgerService;
import com.springbootView.springbootview.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;


@Controller
@RequestMapping(value = "/")
public class HamburgerController {

    private final HamburgerService hamburgerService;
    private final OrderService orderService;

    @Autowired
    public HamburgerController(HamburgerService hamburgerService, OrderService orderService) {
        this.hamburgerService = hamburgerService;
        this.orderService = orderService;
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

    @GetMapping("/order")
    public String getOrdersView(Model model) {
        model.addAttribute("allhamburger", hamburgerService.getAllBurger());
        return "order";
    }

    @GetMapping(value = "/order/hamburger")
    public String getOrder(HttpServletRequest request, Model model) throws UnsupportedEncodingException {
        String params = request.getQueryString();
        Cart cart = orderService.saveOrder(params);
        model.addAttribute("cart", cart);
        model.addAttribute("address", cart.getAddress());
        return "ordered_products";
    }

    @GetMapping("/login")
    public String getLoginView() {
        return "login";
    }
/*
    @PostMapping ("/login")
    public String getLoginViewDetails(@RequestBody String email, @RequestBody String password){
        System.out.println(email);
        return "login";
    }

    @GetMapping ("/register")
    public String getRegisterView(){
        return "register";
    }

    @PostMapping("/register")
    public void getRegisterPostView(@RequestBody String user){
        System.out.println(user);
       // return "register";
    }
    */


}
