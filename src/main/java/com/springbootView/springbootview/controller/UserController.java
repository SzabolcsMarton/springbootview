package com.springbootView.springbootview.controller;

import com.springbootView.springbootview.model.Cart;
import com.springbootView.springbootview.services.AdminService;
import com.springbootView.springbootview.services.OrderService;
import com.springbootView.springbootview.services.ToppingService;
import com.springbootView.springbootview.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;


@Controller
@RequestMapping(value = "/users", method = {RequestMethod.GET, RequestMethod.POST})
public class UserController {

    private final AdminService adminService;
    private final ToppingService toppingService;
    private final OrderService orderService;
    private final UserService userService;

    @Autowired
    public UserController(AdminService adminService, ToppingService toppingService, OrderService orderService, UserService userService) {
        this.adminService = adminService;
        this.toppingService = toppingService;
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping(value = "/user")
    public String getUserProfileById(Principal principal, Model model) {
        model.addAttribute("user", userService.getOneUserByEmail(principal.getName()));
        return "users_user";
    }

    @GetMapping(value = "/user/orders", params = {"userIdForOpen"})
    public String getUsersAllOrdersById(Long userIdForOpen, Model model) {
        model.addAttribute("carts", orderService.getAllCartsByUserIdOrderedByTimeDesc(userIdForOpen));
        return "users_user_orders";
    }

    @GetMapping(value = "/user/orders/cart", params = {"cartIdForOpen"})
    public String getUsersOneOrder(Long cartIdForOpen, Model model) {
        model.addAttribute("cart", orderService.getCartById(cartIdForOpen));
        return "users_user_orders_cart";
    }

}
