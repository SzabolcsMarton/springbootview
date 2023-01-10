package com.springbootView.springbootview.repositories;

import com.springbootView.springbootview.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
