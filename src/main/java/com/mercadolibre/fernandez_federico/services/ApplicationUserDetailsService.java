package com.mercadolibre.fernandez_federico.services;

import com.mercadolibre.fernandez_federico.models.ApplicationUser;
import com.mercadolibre.fernandez_federico.repositories.IUserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;

@Service
public class ApplicationUserDetailsService implements UserDetailsService {
    private IUserRepository applicationUserRepository;

    private List<ApplicationUser> users;

    public ApplicationUserDetailsService(IUserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
        this.users = new ArrayList<>();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser applicationUser = applicationUserRepository.findByUsername(username);
        if (applicationUser == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(applicationUser.getUsername(), applicationUser.getPassword(), emptyList());
    }

    public void saveUser(ApplicationUser applicationUser) {
        this.users.add(applicationUser);
        System.out.println(this.users.toString());
    }
}