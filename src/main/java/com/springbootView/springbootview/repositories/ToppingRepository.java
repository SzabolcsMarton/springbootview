package com.springbootView.springbootview.repositories;

import com.springbootView.springbootview.model.Topping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ToppingRepository extends JpaRepository<Topping, Long> {

    Optional<Topping> findByName(String name);
}
