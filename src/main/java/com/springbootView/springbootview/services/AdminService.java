package com.springbootView.springbootview.services;


import com.springbootView.springbootview.dto.HamburgerEditDto;
import com.springbootView.springbootview.exception.HamburgerNotFoundException;
import com.springbootView.springbootview.model.Hamburger;
import com.springbootView.springbootview.model.Topping;
import com.springbootView.springbootview.repositories.HamburgerRepository;
import com.springbootView.springbootview.repositories.ToppingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    private final HamburgerRepository hamburgerRepository;
    private final ToppingRepository toppingRepository;

    @Autowired
    public AdminService(HamburgerRepository hamburgerRepository, ToppingRepository toppingRepository) {
        this.hamburgerRepository = hamburgerRepository;
        this.toppingRepository = toppingRepository;
    }

    public Hamburger findOneById(Long id) {
        Optional<Hamburger> burger = hamburgerRepository.findById(id);
        return burger.orElseThrow(() -> new HamburgerNotFoundException("Cannot find Hamburger with id: " + id));
    }

    public Hamburger findOneByName(String name) {
        Optional<Hamburger> burger = hamburgerRepository.findByName(name);
        return burger.orElseThrow(() -> new HamburgerNotFoundException("Cannot find Hamburger with name: " + name));
    }

    public List<Hamburger> getAllBurger() {
        return hamburgerRepository.findAll().stream().sorted().toList();
    }

    public Hamburger saveNewBurger(String params) throws UnsupportedEncodingException {
        Hamburger hamburger = extractHamburgerFromParamString(params);
        return hamburgerRepository.save(hamburger);
    }

    public Hamburger updateBurger(String params) {
        HamburgerEditDto hamburgerEditDto = extractHamburgerDtoFromParams(params);
        Hamburger burgerToEdit = hamburgerRepository.findById(hamburgerEditDto.getId()).orElseThrow(() -> new HamburgerNotFoundException("Cannot find Hamburger with id: " + hamburgerEditDto.getId()));
        if (!hamburgerEditDto.getName().equals("")) {
            burgerToEdit.setName(hamburgerEditDto.getName());
        }
        if (hamburgerEditDto.getPrice() != 0) {
            burgerToEdit.setPrice(hamburgerEditDto.getPrice());
        }
        if (!hamburgerEditDto.getToppings().isEmpty()) {
            burgerToEdit.setToppings(hamburgerEditDto.getToppings());
        }
        return hamburgerRepository.save(burgerToEdit);
    }

    public boolean deleteBurger(Long id) {
        if (hamburgerRepository.existsById(id)) {
            hamburgerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private Hamburger extractHamburgerFromParamString(String params) throws UnsupportedEncodingException {
        String[] allparams = params.split("&");
        List<String> result = new ArrayList<>();
        String name = decode(allparams[0].split("=")[1]);
        String priceString = allparams[1].split("=")[1];
        int price = Integer.parseInt(priceString);
        for (int i = 2; i < allparams.length; i++) {
            result.add(allparams[i].split("=")[0]);
        }
        List<Topping> toppings = extractToppingsToList(result);

        return new Hamburger(name, price, toppings);
    }

    private HamburgerEditDto extractHamburgerDtoFromParams(String params) {
        String[] allparams = params.split("&");
        System.out.println(allparams.length);
        List<String> result = new ArrayList<>();
        String idString = allparams[0].split("=")[1];
        long idOld = Long.parseLong(idString);
        String name = (allparams[1].split("=").length > 1)? allparams[1].split("=")[1] : "";
        String priceString = (allparams[2].split("=").length > 1)? allparams[2].split("=")[1] : "";
        int price = (priceString.equals("") ? 0 : Integer.parseInt(priceString));

        for (int i = 3; i < allparams.length; i++) {
            result.add(allparams[i].split("=")[0]);
        }
        List<Topping> toppings = extractToppingsToList(result);
        return new HamburgerEditDto(idOld, name, price, toppings);
    }

    private List<Topping> extractToppingsToList(List<String> toppingNames) {
        List<Topping> tops = new ArrayList<>();
        for (String actual : toppingNames) {
            String actualTopping = actual.trim().toLowerCase();
            Optional<Topping> foundTopping = toppingRepository.findByName(actualTopping);
            if (foundTopping.isPresent()) {
                tops.add(foundTopping.get());
            } else {
                Topping newTopping = toppingRepository.save(new Topping(actualTopping));
                tops.add(newTopping);
            }
        }
        return tops;
    }

    private String decode(String toDecode) throws UnsupportedEncodingException {
         return URLDecoder.decode(toDecode, StandardCharsets.UTF_8.toString());
    }


}
