package com.springbootView.springbootview.services;

import com.springbootView.springbootview.model.Hamburger;
import com.springbootView.springbootview.repositories.HamburgerRepository;
import org.springframework.stereotype.Service;
import java.util.*;

import java.util.*;

@Service
public class HamburgerService {

    private final HamburgerRepository hamburgerRepository;

    public HamburgerService(HamburgerRepository hamburgerRepository) {
        this.hamburgerRepository = hamburgerRepository;
    }

    public List<Hamburger> getAllBurger() {
        List<Hamburger> burgers = hamburgerRepository.findAll();
        Collections.sort(burgers);
        return burgers;

    }



}
