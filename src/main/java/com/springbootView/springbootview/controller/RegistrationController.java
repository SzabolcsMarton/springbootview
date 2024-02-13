package com.springbootView.springbootview.controller;

import com.springbootView.springbootview.dto.AddressRegisterDto;
import com.springbootView.springbootview.dto.UserRegisterDto;
import com.springbootView.springbootview.exception.UserAlreadyRegisteredException;
import com.springbootView.springbootview.model.User;
import com.springbootView.springbootview.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/registration")
public class RegistrationController {

    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/register/user")
    public String getRegisterPageUser(Model model){
        model.addAttribute("userDto", new UserRegisterDto());
        return "register_user";
    }

    @PostMapping(value = "/register/address")
    public String getRegisterPageAddAddress(@ModelAttribute("userDto") UserRegisterDto userRegisterDto,Model model){
        User user = new User();
        try {
           user = userService.saveUserFromDto(userRegisterDto);

        }catch (UserAlreadyRegisteredException exception){
            model.addAttribute("userDto", userRegisterDto);
            model.addAttribute("message","Ez az email cim már regisztrálva van");
            return "register_user";
        }
        AddressRegisterDto dto =new AddressRegisterDto();
        dto.setUserId(user.getUserId());
        model.addAttribute("message", "Sikeres regisztráció, adja meg a címet");
        model.addAttribute("addressDto",dto );
        return "register_user_add_address";
    }

    @PostMapping("/register")
    public String getLoginWithSuccessRegister(@ModelAttribute("addressDto") AddressRegisterDto addressRegisterDto, Model model){
        boolean isSuccess = userService.registerUser(addressRegisterDto);
        String message = isSuccess? "Sikeres regisztráció" : "Ez az email cim már regisztrálva van";
        model.addAttribute("message", message);
        return "auth/login";
    }


}
