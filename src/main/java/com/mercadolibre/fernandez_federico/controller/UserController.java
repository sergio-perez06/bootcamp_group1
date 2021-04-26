package com.mercadolibre.fernandez_federico.controller;


import com.mercadolibre.fernandez_federico.dtos.request.ApplicationUserDTO;
import com.mercadolibre.fernandez_federico.models.ApplicationUser;
import com.mercadolibre.fernandez_federico.models.CountryDealer;
import com.mercadolibre.fernandez_federico.models.Role;
import com.mercadolibre.fernandez_federico.services.IRoleService;
import com.mercadolibre.fernandez_federico.services.impl.UserService;
import com.mercadolibre.fernandez_federico.services.ICountryDealerService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/users")
public class UserController {

    private ModelMapper modelMapper;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserService userService;
    private ICountryDealerService countryDealerService;
    private IRoleService roleService;


    public UserController(BCryptPasswordEncoder bCryptPasswordEncoder,
                          UserService userService,
                          ModelMapper modelMapper,
                          ICountryDealerService countryDealerService,
                          IRoleService roleService) {

        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.countryDealerService = countryDealerService;
        this.roleService = roleService;

    }

    @PostMapping("/signUp")
    public void signUp(@RequestBody ApplicationUserDTO applicationUser) {
        applicationUser.setPassword(bCryptPasswordEncoder.encode(applicationUser.getPassword()));
        //this.userService.saveUser(applicationUser);

        ApplicationUser toSave = modelMapper.map(applicationUser,ApplicationUser.class);

        CountryDealer foundCountryD = countryDealerService.findByCountry(applicationUser.getCountry());
        Role foundRole = roleService.findByName(applicationUser.getRole());

        toSave.setCountryDealer(foundCountryD);
        toSave.setRole(foundRole);

        this.userService.saveUser(toSave);
    }
}
