package com.springbootView.springbootview.repositories;

import com.springbootView.springbootview.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
