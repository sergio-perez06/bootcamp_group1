package com.mercadolibre.fernandez_federico.services.impl;

import com.mercadolibre.fernandez_federico.models.Role;
import com.mercadolibre.fernandez_federico.repositories.IRoleRepository;
import com.mercadolibre.fernandez_federico.services.IRoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleService implements IRoleService {
    private IRoleRepository roleRepository;

    public RoleService(IRoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }
}
