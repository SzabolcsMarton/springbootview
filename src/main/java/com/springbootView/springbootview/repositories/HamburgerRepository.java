package com.springbootView.springbootview.repositories;

import com.springbootView.springbootview.model.Hamburger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HamburgerRepository extends JpaRepository<Hamburger, Long> {
}
