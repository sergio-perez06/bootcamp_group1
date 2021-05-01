package com.mercadolibre.fernandez_federico.unit.services.impl;

import com.mercadolibre.fernandez_federico.dtos.request.ApplicationUserDTO;
import com.mercadolibre.fernandez_federico.dtos.responses.BillDTO;
import com.mercadolibre.fernandez_federico.exceptions.ApiException;
import com.mercadolibre.fernandez_federico.models.ApplicationUser;
import com.mercadolibre.fernandez_federico.models.CountryDealer;
import com.mercadolibre.fernandez_federico.models.Role;
import com.mercadolibre.fernandez_federico.repositories.IUserRepository;
import com.mercadolibre.fernandez_federico.services.ICountryDealerService;
import com.mercadolibre.fernandez_federico.services.IRoleService;
import com.mercadolibre.fernandez_federico.services.IUserService;
import com.mercadolibre.fernandez_federico.services.impl.UserService;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock private IUserRepository applicationUserRepository;
    @Mock private ModelMapper modelMapper;
    @Mock private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Mock private ICountryDealerService countryDealerService;
    @Mock private IRoleService roleService;

    private IUserService userService;

    public UserServiceTest() {
        openMocks(this);
        userService = new UserService(applicationUserRepository, modelMapper, bCryptPasswordEncoder, countryDealerService, roleService);
    }

    @Test
    public void signUpOK() throws Exception {
        ApplicationUserDTO request = new ApplicationUserDTO("sergio", "passs", "Argentina", "ADMIN");
        CountryDealer countryDealer = new CountryDealer(1L, "0001", "MELI", "Argentina");
        Role role = new Role(1L, "ADMIN");
        ApplicationUser saveUser = new ApplicationUser(1L, "sergio", "$2a$10$QjoUS0H032kw7fv1h0tWNua752vWbdcTAP.0eluk1c022RDuYWtWW", role, countryDealer);
        String encodedPw = "$2a$10$QjoUS0H032kw7fv1h0tWNua752vWbdcTAP.0eluk1c022RDuYWtWW";

        when(bCryptPasswordEncoder.encode(anyString())).thenReturn(encodedPw);
        when(modelMapper.map(any(), any())).thenReturn(saveUser);
        when(countryDealerService.findByCountry(anyString())).thenReturn(countryDealer);
        when(roleService.findByName(anyString())).thenReturn(role);

        userService.signUp(request);
        verify(bCryptPasswordEncoder, atMostOnce()).encode(anyString());
        verify(modelMapper, atMostOnce()).map(any(), any());
        verify(countryDealerService, atMostOnce()).findByCountry(anyString());
        verify(roleService, atMostOnce()).findByName(anyString());
        verify(applicationUserRepository, atMostOnce()).save(saveUser);
    }

    @Test
    public void signUpBadRequest() throws Exception {
        ApplicationUserDTO request = new ApplicationUserDTO("sergio", "passs", "Argentina", "ADMIN");
        assertThatThrownBy(() -> {
            userService.signUp(request);
        }).isInstanceOf(ApiException.class).hasMessageContaining("El usuario no pudo ser registrado.");
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
        //userService.saveUser(new ApplicationUser());
        //verify(applicationUserRepository, atMostOnce()).save(any(ApplicationUser.class));
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