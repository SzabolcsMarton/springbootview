package com.springbootView.springbootview.repositories;

import com.springbootView.springbootview.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {

}
