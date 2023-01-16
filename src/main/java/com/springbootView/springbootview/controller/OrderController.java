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
@RequestMapping(value = "/order")
public class OrderController {

    private final HamburgerService hamburgerService;
    private final OrderService orderService;

    @Autowired
    public OrderController(HamburgerService hamburgerService, OrderService orderService) {
        this.hamburgerService = hamburgerService;
        this.orderService = orderService;
    }

    @GetMapping
    public String getOrdersView(Model model) {
        model.addAttribute("allhamburger", hamburgerService.getAllBurger());
        return "order";
    }

    @GetMapping(value = "order/hamburger")
    public String getOrder(HttpServletRequest request, Model model) throws UnsupportedEncodingException {
        String params = request.getQueryString();
        Cart cart = orderService.saveOrder(params);
        model.addAttribute("cart", cart);
        model.addAttribute("address", cart.getAddress());
        return "ordered_products";
    }

}
