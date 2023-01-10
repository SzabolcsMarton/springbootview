package com.springbootView.springbootview.repositories;

import com.springbootView.springbootview.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
