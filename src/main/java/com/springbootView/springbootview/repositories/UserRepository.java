package com.springbootView.springbootview.repositories;

import com.springbootView.springbootview.model.Cart;
import com.springbootView.springbootview.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u ORDER BY u.name ASC ")
    List<User> findAllUserOrderedByName();


}
