package com.springbootView.springbootview.services;

import com.springbootView.springbootview.model.Topping;
import com.springbootView.springbootview.repositories.ToppingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToppingService {

    private final ToppingRepository toppingRepository;

    @Autowired
    public ToppingService(ToppingRepository toppingRepository) {
        this.toppingRepository = toppingRepository;
    }

    public List<Topping> getAllToppings(){
        return toppingRepository.findAll();
    }

    public boolean saveTopping(String toppingName){
        Topping topping = toppingRepository.save(new Topping(toppingName));
        if (topping == null){
            return false;
        }
        return true;
    }
}
