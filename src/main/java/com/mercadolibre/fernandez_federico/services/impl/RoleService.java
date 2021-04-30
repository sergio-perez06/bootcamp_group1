package com.mercadolibre.fernandez_federico.services.impl;

import com.mercadolibre.fernandez_federico.exceptions.ApiException;
import com.mercadolibre.fernandez_federico.models.CountryDealer;
import com.mercadolibre.fernandez_federico.models.Role;
import com.mercadolibre.fernandez_federico.repositories.IRoleRepository;
import com.mercadolibre.fernandez_federico.services.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService {
    private final IRoleRepository roleRepository;

    @Override
    public Role findByName(String name) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            throw new ApiException(NOT_FOUND.name(), String.format("No existe role con el nombre '%s'", name), NOT_FOUND.value());
        }
        return role;
    }
}
