package com.simpleform.controller;

import com.simpleform.model.Users;
import com.simpleform.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Objects;

@Slf4j
@Controller
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/register")
    public String getRegisterPage(Model model){
        model.addAttribute("registerRequest", new Users());
        return "register_page";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model){
        model.addAttribute("loginRequest", new Users());
        return "login_page";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute Users users, Model model){
        log.info("user register request: {}", users);
        int count = userService.validateDuplicateEmail(users.getEmail());

        if(count > 0) {
            log.info("Duplicate email,count:{}", count);
            model.addAttribute("email_count", count);
            return "error_page";
        }
        Users registeredUser = userService.registerUser(users.getLogin(), users.getPassword(), users.getEmail());
        return Objects.nonNull(registeredUser) ? "redirect:/login" : "error_page";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute Users users, Model model){
        log.info("user login request: {}", users);
        Users authenticatedUser = userService.authenticateUser(users.getLogin(), users.getPassword());
        if(Objects.nonNull(authenticatedUser)){
            model.addAttribute("userLogin", authenticatedUser.getLogin());
            return "user_page";
        }
        return Objects.nonNull(authenticatedUser) ? "user_page" : "error_page";
    }

}
