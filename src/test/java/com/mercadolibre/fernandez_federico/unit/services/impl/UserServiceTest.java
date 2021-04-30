package com.mercadolibre.fernandez_federico.unit.services.impl;

import com.mercadolibre.fernandez_federico.models.ApplicationUser;
import com.mercadolibre.fernandez_federico.repositories.IUserRepository;
import com.mercadolibre.fernandez_federico.services.IUserService;
import com.mercadolibre.fernandez_federico.services.impl.UserService;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private IUserRepository applicationUserRepository;

    private IUserService userService;

    public UserServiceTest() {
        openMocks(this);
        userService = new UserService(applicationUserRepository);
    }

    @Test
    @DisplayName("Test invalido, no existe applicationUser con username dado")
    public void loadUserByUsernameInvalidNotExistentUser() {
        when(applicationUserRepository.findByUsername(anyString())).thenReturn(null);

        assertThatThrownBy(() -> {
            UserDetails user = userService.loadUserByUsername("Hola");
        }).isInstanceOf(UsernameNotFoundException.class);
    }

    @Test
    @DisplayName("Test valido, existe applicationUser con username dado")
    public void loadUserByUsernameValidExistentUser() {
        ApplicationUser appUser = generateApplicationUserData();
        when(applicationUserRepository.findByUsername(anyString())).thenReturn(appUser);

        UserDetails user = userService.loadUserByUsername("meliuser");

        assertThat(user.getUsername()).isEqualTo(appUser.getUsername());
        assertThat(user.getPassword()).isEqualTo(appUser.getPassword());
    }

    @Test
    @DisplayName("Test valido, unico almacenamiento de usuario")
    public void saveUserValid() {
        userService.saveUser(new ApplicationUser());
        verify(applicationUserRepository, atMostOnce()).save(any(ApplicationUser.class));
    }

    @Test
    @DisplayName("Test invalido, no existe applicationUser con username dado")
    public void findByUsernameInvalidNotExistentUser() {
        when(applicationUserRepository.findByUsername(anyString())).thenReturn(null);

        assertThatThrownBy(() -> {
            UserDetails user = userService.loadUserByUsername("Hola");
        }).isInstanceOf(UsernameNotFoundException.class);
    }

    @Test
    @DisplayName("Test valido, existe applicationUser con username dado")
    public void findByUsernameValidExistentUser() {
        ApplicationUser appUser = generateApplicationUserData();
        when(applicationUserRepository.findByUsername(anyString())).thenReturn(appUser);

        ApplicationUser user = userService.findByUsername("meliuser");

        assertThat(user.getUsername()).isEqualTo(appUser.getUsername());
        assertThat(user.getPassword()).isEqualTo(appUser.getPassword());
    }

    private ApplicationUser generateApplicationUserData() {
        ApplicationUser appUser = new ApplicationUser();
        appUser.setUsername("meliuser");
        appUser.setPassword("meliuserpass");
        return appUser;
    }

}