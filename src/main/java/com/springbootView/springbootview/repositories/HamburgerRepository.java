package com.springbootView.springbootview.repositories;

import com.springbootView.springbootview.model.Hamburger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HamburgerRepository extends JpaRepository<Hamburger, Long> {

    Optional<Hamburger> findByName(String name);


}
