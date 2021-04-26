package com.mercadolibre.fernandez_federico.services;

import com.mercadolibre.fernandez_federico.models.Role;

public interface IRoleService {

    Role findByName(String name);
}
