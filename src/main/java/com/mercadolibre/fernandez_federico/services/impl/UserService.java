package com.mercadolibre.fernandez_federico.services.impl;

import com.mercadolibre.fernandez_federico.dtos.request.ApplicationUserDTO;
import com.mercadolibre.fernandez_federico.exceptions.ApiException;
import com.mercadolibre.fernandez_federico.models.ApplicationUser;
import com.mercadolibre.fernandez_federico.models.CountryDealer;
import com.mercadolibre.fernandez_federico.models.Role;
import com.mercadolibre.fernandez_federico.repositories.IUserRepository;
import com.mercadolibre.fernandez_federico.services.ICountryDealerService;
import com.mercadolibre.fernandez_federico.services.IRoleService;
import com.mercadolibre.fernandez_federico.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static java.util.Collections.emptyList;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final IUserRepository applicationUserRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ICountryDealerService countryDealerService;
    private final IRoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser applicationUser = this.findByUsername(username);
        return new User(applicationUser.getUsername(), applicationUser.getPassword(), emptyList());
    }

    public ApplicationUser findByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser applicationUser = applicationUserRepository.findByUsername(username);
        if (applicationUser == null) {
            throw new UsernameNotFoundException(username);
        }
        return applicationUser;
    }

    @Override
    public void signUp(ApplicationUserDTO request) throws Exception {
        try {
            request.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
            ApplicationUser toSave = modelMapper.map(request, ApplicationUser.class);

            CountryDealer foundCountryD = countryDealerService.findByCountry(request.getCountry());
            Role foundRole = roleService.findByName(request.getRole());

            toSave.setCountryDealer(foundCountryD);
            toSave.setRole(foundRole);
            applicationUserRepository.save(toSave);
        }
        catch(Exception e) {
            // how to find out the reason for the rollback exception?
            throw new ApiException(HttpStatus.BAD_REQUEST.name(), "El usuario no pudo ser registrado.", HttpStatus.BAD_REQUEST.value());
        }
    }
}