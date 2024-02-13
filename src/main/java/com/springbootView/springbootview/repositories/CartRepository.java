package com.springbootView.springbootview.repositories;

import com.springbootView.springbootview.model.Cart;
import org.hibernate.annotations.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    //order
    @Query("SELECT c FROM Cart c ORDER BY c.name ASC ")
    List<Cart> findAllOrdersOrderedByNameAsc();

    @Query("SELECT c FROM Cart c ORDER BY c.name DESC ")
    List<Cart> findAllOrdersOrderedByNameDesc();

    @Query(value = "SELECT c FROM Cart c ORDER BY c.sumOfAllItemPrices ASC ")
    List<Cart> findAllOrdersOrderedByPriceAsc();

    @Query("SELECT c FROM Cart c ORDER BY c.sumOfAllItemPrices DESC")
    List<Cart> findAllOrdersOrderedByPriceDesc();

    @Query("SELECT c FROM Cart c ORDER BY c.timeOfOrder ASC ")
    List<Cart> findAllOrdersOrderedByDateAsc();

    @Query("SELECT c FROM Cart c ORDER BY c.timeOfOrder DESC ")
    List<Cart> findAllOrdersOrderedByDateDesc();

    //find by name
    List<Cart> findCartsByName(String name);

    //list of carts on a day
    List<Cart> findAllByTimeOfOrder(LocalDate date);

    //list of carts in a period of time
    List<Cart> findAllByTimeOfOrderBetween(LocalDateTime from, LocalDateTime until);

    @Query("SELECT c FROM Cart c where c.user.userId = :userId ")
    List<Cart> findAllOrdersOfUserById(@Param("userId") Long userId);

    @Query("SELECT c FROM Cart c where c.user.userId = :userId ORDER BY c.timeOfOrder DESC ")
    List<Cart> findAllOrdersOfUserByIdOrderedByTimeDesc(@Param("userId") Long userId);

    @Query("select count(c) from Cart c where c.user.userId =:userId And c.timeOfOrder between :from And :until")
    int countUsersAllOrdersInPeriod(@Param("userId") Long userId, @Param("from") LocalDateTime from,@Param("until") LocalDateTime until);



}
