package com.springbootView.springbootview.services;

import com.springbootView.springbootview.exception.CartNotFoundException;
import com.springbootView.springbootview.exception.HamburgerNotFoundException;
import com.springbootView.springbootview.model.*;
import com.springbootView.springbootview.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@Service
public class OrderService {

    private static final int FIRST_ORDER_PARAM_PART_INDEX = 7;

    private final HamburgerRepository hamburgerRepository;
    private final AddressRepository addressRepository;
    private final CartRepository cartRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserService userService;


    @Autowired
    public OrderService(HamburgerRepository hamburgerRepository, AddressRepository addressRepository, CartRepository cartRepository, OrderItemRepository orderItemRepository, UserService userService) {
        this.hamburgerRepository = hamburgerRepository;
        this.addressRepository = addressRepository;
        this.cartRepository = cartRepository;
        this.orderItemRepository = orderItemRepository;
        this.userService = userService;
    }

    public Cart saveOrder(String ordersString) throws UnsupportedEncodingException {
        return cartRepository.save(parseOrders(ordersString));
    }

    public Cart getCartById(Long cartId){
        return cartRepository.findById(cartId).orElseThrow(()-> new CartNotFoundException("Nem található Cart az adott id-vel: " + cartId));
    }

    public List<Cart> getAllOrders(){
        return cartRepository.findAll();
    }

    private Cart parseOrders(String ordersString) throws UnsupportedEncodingException {
        String encodedParams = URLDecoder.decode(ordersString, StandardCharsets.UTF_8.toString());
        String[] params = encodedParams.split("&");
        String addressType = params[0].split("=")[1];
        String[] deliveryParams = Arrays.copyOfRange(params, 1, FIRST_ORDER_PARAM_PART_INDEX);
        String[] orderParams = Arrays.copyOfRange(params, FIRST_ORDER_PARAM_PART_INDEX, params.length);
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getOneUserByEmail(email);
        Cart newCart = new Cart();
        String addressToSave = setAddress(deliveryParams,user,addressType);
        newCart.setAddress(addressToSave);
        Cart savedCart = cartRepository.save(newCart);
        orderItemParser(savedCart, orderParams);
        return savedCart;
    }

    private String setAddress(String[] deliveryParams, User user, String addressType) {
        if (addressType.equals("user")) {
            return user.getAddress().toString();
        } else {
            return parseAddress(deliveryParams).toString();
        }
    }

    private OrderItem parseOrderItem(String[] itemParam, Cart savedCart) {
        Hamburger hamburger = getHamburgerByName(itemParam[0]);
        OrderItem itemToSave = new OrderItem(hamburger.getName(), Integer.parseInt(itemParam[1]), hamburger.getPrice(), savedCart);
        return orderItemRepository.save(itemToSave);
    }

    private void orderItemParser(Cart cart, String[] orderParams) {
        for (String actual : orderParams) {
            String[] orderItems = actual.split("=");
            if (orderItems[0].equals("szallit")) {
                cart.setDelivery(true);
            } else {
                OrderItem item = parseOrderItem(orderItems, cart);
                cart.addItem(item);
            }
        }
    }

    private Hamburger getHamburgerByName(String name) {
        return hamburgerRepository.findByName(name).orElseThrow(() -> new HamburgerNotFoundException("Cannot find Hamburger with name: " + name));
    }

    private Address parseAddress(String[] deliveryParams) {
        return new Address(
                deliveryParams[0].split("=")[1],
                deliveryParams[1].split("=")[1],
                deliveryParams[2].split("=")[1],
                deliveryParams[3].split("=")[1],
                deliveryParams[4].split("=")[1],
                deliveryParams[5].split("=")[1]
        );
    }



}
