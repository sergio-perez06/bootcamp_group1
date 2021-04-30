package com.mercadolibre.fernandez_federico.unit.services.impl;

import com.mercadolibre.fernandez_federico.exceptions.ApiException;
import com.mercadolibre.fernandez_federico.models.Role;
import com.mercadolibre.fernandez_federico.repositories.IRoleRepository;
import com.mercadolibre.fernandez_federico.services.IRoleService;
import com.mercadolibre.fernandez_federico.services.impl.RoleService;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {

    @Mock
    private IRoleRepository roleRepository;

    private IRoleService roleService;

    public RoleServiceTest() {
        openMocks(this);
        roleService = new RoleService(roleRepository);
    }

    @Test
    @DisplayName("Test invalido, no existe countryDealer con nombre dado")
    public void findByNameInvalidNotExistentCountry() throws Exception {
        when(roleRepository.findByName(anyString())).thenReturn(null);

        assertThatThrownBy(() -> {
            Role cd = roleService.findByName("Hola");
        }).isInstanceOf(ApiException.class).hasMessageContaining("No existe role con el nombre 'Hola'");
    }

    @Test
    @DisplayName("Test invalido, no existe countryDealer con nombre dado")
    public void findByCountryValidExistentCountry() throws Exception {
        Role roleExpected = new Role();
        roleExpected.setName("Admin");
        when(roleRepository.findByName(anyString())).thenReturn(roleExpected);

        Role role = roleService.findByName("Admin");

        assertThat(role.getName()).isEqualTo(roleExpected.getName());
    }


}