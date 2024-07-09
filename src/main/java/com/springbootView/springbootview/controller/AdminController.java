package com.springbootView.springbootview.controller;

import com.springbootView.springbootview.model.Cart;
import com.springbootView.springbootview.services.*;
import com.springbootView.springbootview.util.PdfGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/admin", method = {RequestMethod.GET, RequestMethod.POST})
public class AdminController {

    private final AdminService adminService;
    private final ToppingService toppingService;
    private final OrderService orderService;
    private final UserService userService;

    @Autowired
    public AdminController(AdminService adminService, ToppingService toppingService, OrderService orderService, UserService userService) {
        this.adminService = adminService;
        this.toppingService = toppingService;
        this.orderService = orderService;
        this.userService = userService;
    }
    // *** main controller panel path ***

    @GetMapping()
    public String getAdminControllerView() {
        return "admin_controller";
    }

    // *** hamburger controller paths ***

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

    // *** order controller paths ***

    @GetMapping(value = "/orders")
    public String getAllOrders(Model model) {
        model.addAttribute("userNames", userService.getAllUsersName());
        model.addAttribute("carts", orderService.getAllOrdersOrderedBy("dateDesc"));
        return "admin_orders";
    }

    @GetMapping(value = "/orders/orderedby", params = "orderType")
    public String getAllOrdersOrderedBy(Model model, String orderType) {
        model.addAttribute("userNames", userService.getAllUsersName());
        model.addAttribute("carts", orderService.getAllOrdersOrderedBy(orderType));
        return "admin_orders";
    }

    @GetMapping(value = "/orders/user/allorders", params = "userName")
    public String getAllOrderFilteredByName(Model model, String userName) {
        model.addAttribute("userNames", userService.getAllUsersName());
        model.addAttribute("carts", orderService.getAllCartsByName(userName));
        return "admin_orders";
    }

    @GetMapping(value = "/orders/allordersbytime", params = {"timePeriodFrom", "timePeriodTo"})
    public String getAllOrderFilteredByTime(Model model, String timePeriodFrom, String timePeriodTo) throws ParseException {
        List<Cart> carts = new ArrayList<>();
        model.addAttribute("userNames", userService.getAllUsersName());
        try {
            carts = orderService.getAllCartByTime(timePeriodFrom, timePeriodTo);
            model.addAttribute("carts", carts);
        } catch (ParseException | DateTimeParseException e) {
            model.addAttribute("errormessage", "A kezdő időpontot meg kell adni!");
        }
        return "admin_orders";
    }

    @GetMapping(value = "/orders/cart", params = {"cartIdForOpen"})
    public String getOneCart(Long cartIdForOpen, Model model) {
        Cart cart = orderService.getCartById(cartIdForOpen);
        model.addAttribute("cart", cart);
        return "admin_orders_cart";
    }

    // *** PDF path ***

    @GetMapping(value = "/orders/pdf")
    public String createPdfReport(Model model) throws URISyntaxException {
        model.addAttribute("userNames", userService.getAllUsersName());
        List<Cart> carts = orderService.getAllOrdersOrderedBy("dateDesc");
        model.addAttribute("carts", carts);
        PdfGenerator pdfGenerator = new PdfGenerator();
        pdfGenerator.generatePdfReport(carts);
        model.addAttribute("message", "PDF sikeresen generálva");
        return  "admin_orders";
    }

    // *** users controller path ***

    @GetMapping(value = "/users")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsersOrderedByName());
        return "admin_users";
    }

    @GetMapping(value = "/users/user", params = {"userIdForOpen"})
    public String getOneUserById(Long userIdForOpen, Model model) {
        model.addAttribute("user", userService.getOneUserById(userIdForOpen));
        model.addAttribute("roles",userService.getUserRolesPrint(userIdForOpen));
        return "admin_users_user";
    }

    @GetMapping(value = "/users/user/orders", params = {"userIdForOpen"})
    public String getOneUsersAllOrdersById(Long userIdForOpen, Model model) {
        model.addAttribute("carts", orderService.getAllCartsByUserIdOrderedByTimeDesc(userIdForOpen));
        return "admin_users_user_orders";
    }

    @GetMapping(value = "/users/user/orders/cart", params = {"cartIdForOpen"})
    public String getUsersOneOrder(Long cartIdForOpen, Model model) {
        model.addAttribute("cart", orderService.getCartById(cartIdForOpen));
        return "admin_users_user_orders_cart";
    }
    @GetMapping(value = "/users/user/adminrole/add", params = {"userId"})
    public String addAdminRoleToCurrentUser(Long userId, Model model) {
        model.addAttribute("user", userService.getOneUserById(userId));
        model.addAttribute("message",userService.addAdminRole(userId));
        model.addAttribute("roles",userService.getUserRolesPrint(userId));
        return "admin_users_user";
    }

    @GetMapping(value = "/users/user/adminrole/remove", params = {"userId"})
    public String removeAdminRoleToCurrentUser(Long userId, Model model) {
        model.addAttribute("user", userService.getOneUserById(userId));
        model.addAttribute("message",userService.removeAdminRole(userId));
        model.addAttribute("roles",userService.getUserRolesPrint(userId));
        return "admin_users_user";
    }







}
