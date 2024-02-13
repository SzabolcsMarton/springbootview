package com.springbootView.springbootview.services;


import com.springbootView.springbootview.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Configuration
@EnableScheduling
public class ChedulerService {

    private static final int WEEKS_VIP = 2;
    private static final int MIN_ORDER_FOR_VIP = 3;

    private final UserService userService;
    private final OrderService orderService;

   @Autowired
    public ChedulerService(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    //@Scheduled(cron = "0 10 8 * * *")
    @Scheduled(cron = "0 0 0 * * *")
    public void setVipScheduler(){
        List<User> users = userService.getAllUsers();
        System.out.println("Scheduler");
        for (User current : users){
            int numberOfOrdersInLastTwoWeeks = orderService.countOrdersOfUserInGivenWeeksPrior(current.getUserId(), WEEKS_VIP);
            if(numberOfOrdersInLastTwoWeeks >= MIN_ORDER_FOR_VIP){
                current.setVip(true);
                userService.saveUser(current);
            }
        }
    }
}
