package com.mercadolibre.fernandez_federico.controller;


import com.mercadolibre.fernandez_federico.models.ApplicationUser;
import com.mercadolibre.fernandez_federico.repositories.IUserRepository;
import com.mercadolibre.fernandez_federico.services.ApplicationUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private IUserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private ApplicationUserDetailsService applicationUserDetailsService;



    public UserController(IUserRepository userRepository,
                          BCryptPasswordEncoder bCryptPasswordEncoder,
                          ApplicationUserDetailsService applicationUserDetailsService) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.applicationUserDetailsService = applicationUserDetailsService;

    }

    @PostMapping("/signUp")
    public void signUp(@RequestBody ApplicationUser applicationUser) {
        applicationUser.setPassword(bCryptPasswordEncoder.encode(applicationUser.getPassword()));
        //this.applicationUserDetailsService.saveUser(applicationUser);
        this.userRepository.save(applicationUser);
    }
}
