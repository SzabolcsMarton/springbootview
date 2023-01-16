package com.springbootView.springbootview.repositories;

import com.springbootView.springbootview.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
