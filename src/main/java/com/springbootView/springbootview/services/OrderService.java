package com.springbootView.springbootview.services;

import com.springbootView.springbootview.exception.HamburgerNotFoundException;
import com.springbootView.springbootview.model.Address;
import com.springbootView.springbootview.model.Cart;
import com.springbootView.springbootview.model.Hamburger;
import com.springbootView.springbootview.model.OrderItem;
import com.springbootView.springbootview.repositories.AddressRepository;
import com.springbootView.springbootview.repositories.CartRepository;
import com.springbootView.springbootview.repositories.HamburgerRepository;
import com.springbootView.springbootview.repositories.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Service
public class OrderService {

    private static final int FIRST_ORDER_PARAM_INDEX_INDEX = 6;

    private final HamburgerRepository hamburgerRepository;
    private final AddressRepository addressRepository;
    private final CartRepository cartRepository;
    private final OrderItemRepository orderItemRepository;


    @Autowired
    public OrderService(HamburgerRepository hamburgerRepository, AddressRepository addressRepository, CartRepository cartRepository, OrderItemRepository orderItemRepository) {
        this.hamburgerRepository = hamburgerRepository;
        this.addressRepository = addressRepository;
        this.cartRepository = cartRepository;
        this.orderItemRepository = orderItemRepository;
    }

    public Cart parseOrders(String ordersString) throws UnsupportedEncodingException {
        String encodedParams = URLDecoder.decode(ordersString, StandardCharsets.UTF_8.toString());
        String[] params = encodedParams.split("&");
        String[] deliveryParams = Arrays.copyOfRange(params, 0, FIRST_ORDER_PARAM_INDEX_INDEX);
        String[] orderParams = Arrays.copyOfRange(params, FIRST_ORDER_PARAM_INDEX_INDEX, params.length );

        Address address = addressRepository.save(parseAddress(deliveryParams));
        Cart newCart = new Cart();
        newCart.setAddress(address);
        Cart savedCart = cartRepository.save(newCart);
        for (String actual : orderParams) {
            String[] orderItems = actual.split("=");
            if (orderItems[0].equals("szallit")) {
                savedCart.setDelivery(true);
            } else {
                Hamburger hamburger = getHamburgerByName(orderItems[0]);
                OrderItem itemToSave =new OrderItem(hamburger.getName(), Integer.parseInt(orderItems[1]), hamburger.getPrice(),savedCart);
                OrderItem item = orderItemRepository.save(itemToSave);
                savedCart.addItem(item);

            }
        }

        return cartRepository.save(savedCart);
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
