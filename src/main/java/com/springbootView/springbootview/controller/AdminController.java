package com.springbootView.springbootview.controller;

import com.springbootView.springbootview.model.Cart;
import com.springbootView.springbootview.services.AdminService;
import com.springbootView.springbootview.services.OrderService;
import com.springbootView.springbootview.services.ToppingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Controller
@RequestMapping(value = "/admin", method = {RequestMethod.GET, RequestMethod.POST})
public class AdminController {

    private final AdminService adminService;
    private final ToppingService toppingService;
    private final OrderService orderService;

    @Autowired
    public AdminController(AdminService adminService, ToppingService toppingService, OrderService orderService) {
        this.adminService = adminService;
        this.toppingService = toppingService;
        this.orderService = orderService;
    }

    @GetMapping()
    public String getAdminControllerView() {
        return "admin_controller";
    }

    @GetMapping(value = "/hamburger")
    public String getAdminHamburgerView(Model model) {
        model.addAttribute("toppings", toppingService.getAllToppings());
        return "admin_hamburger";
    }


    @GetMapping(value = "/hamburger/burgerbyname", params = {"hamburgerToFindByName"})
    public String findBurgerByName(String hamburgerToFindByName, Model model) {
        model.addAttribute("burgerByName", adminService.findOneByName(hamburgerToFindByName));
        return "admin_burger_edit";
    }

    @GetMapping(value = "/hamburger/burgerbyid", params = {"hamburgerToFindById"})
    public String findBurgerById(long hamburgerToFindById, Model model) {
        model.addAttribute("burgerById", adminService.findOneById(hamburgerToFindById));
        return "admin_burger_edit";
    }

    @GetMapping(value = "/hamburger/addnewburger")
    public String createNewBurger(HttpServletRequest request, Model model) throws UnsupportedEncodingException {
        String params = request.getQueryString();
        String encodedParams = URLDecoder.decode(params, StandardCharsets.UTF_8.toString());
        model.addAttribute("newburger", adminService.saveNewBurger(encodedParams));
        return "admin_new_burger";
    }

    @GetMapping(value = "/hamburger/allburger")
    public String findAllBurgers(Model model) {
        model.addAttribute("allburgers", adminService.getAllBurger());
        return "admin_allburgers";
    }

    @GetMapping(value = "/hamburger/delete", params = {"deleteId"})
    public String deleteBurgerById(long deleteId, Model model) {
        String isSuccess = adminService.deleteBurger(deleteId) ? "Törlés sikeres!" : "Törlés sikertelen!";
        model.addAttribute("message", isSuccess);
        model.addAttribute("allburgers", adminService.getAllBurger());
        return "admin_allburgers";
    }

    @GetMapping(value = "/hamburger/edit", params = {"editId"})
    public String getEditBurgerPanel(long editId, Model model) {
        model.addAttribute("burgerById", adminService.findOneById(editId));
        model.addAttribute("toppings", toppingService.getAllToppings());
        return "admin_burger_edit_editpanel";
    }

    @GetMapping(value = "/hamburger/edit/editburger")
    public String editBurger(HttpServletRequest request, Model model) throws UnsupportedEncodingException {
        String params = request.getQueryString();
        String encodedParams = URLDecoder.decode(params, StandardCharsets.UTF_8.toString());
        model.addAttribute("newburger", adminService.updateBurger(encodedParams));
        return "admin_new_burger";
    }

    @GetMapping(value = "/hamburger/toppings/addtopping", params = "newTopping")
    public String addNewTopping(String newTopping, Model model) {
        model.addAttribute("message", toppingService.saveTopping(newTopping) ? "Feltét sikeresen mentve" : "Mentés sikertelen");
        model.addAttribute("toppings", toppingService.getAllToppings());
        return "admin_hamburger";
    }
    @GetMapping(value = "/orders")
    public String getAllOrders(Model model) {
        model.addAttribute("carts", orderService.getAllOrders());
        return "admin_orders";
    }

    @GetMapping(value = "/orders/orderedby", params = "orderType")
    public String getAllOrdersOrderedBy(Model model, String orderType ) {
        model.addAttribute("carts", orderService.getAllOrdersOrderedBy(orderType));
        return "admin_orders";
    }

    @GetMapping(value = "/orders/cart", params = {"cartIdForOpen"})
    public String getOneCart(Long cartIdForOpen, Model model) {
        Cart cart = orderService.getCartById(cartIdForOpen);
        model.addAttribute("cart",cart);
        return "admin_orders_cart";
    }






}
