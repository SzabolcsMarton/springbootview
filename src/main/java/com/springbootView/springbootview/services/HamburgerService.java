package com.springbootView.springbootview.services;

import com.springbootView.springbootview.model.Hamburger;
import com.springbootView.springbootview.repositories.HamburgerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HamburgerService {

    private HamburgerRepository hamburgerRepository;

    @Autowired
    public HamburgerService(HamburgerRepository hamburgerRepository) {
        this.hamburgerRepository = hamburgerRepository;
    }

    public List<Hamburger> getAllBurger(){
        return hamburgerRepository.findAll();
    }
}
