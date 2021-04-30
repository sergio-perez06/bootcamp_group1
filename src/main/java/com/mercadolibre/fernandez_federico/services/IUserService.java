package com.mercadolibre.fernandez_federico.services;

import com.mercadolibre.fernandez_federico.dtos.request.BillRequestDTO;
import com.mercadolibre.fernandez_federico.dtos.request.CountryDealerStockDTO;
import com.mercadolibre.fernandez_federico.dtos.responses.BillDTO;
import com.mercadolibre.fernandez_federico.dtos.responses.CountryDealerStockResponseDTO;
import com.mercadolibre.fernandez_federico.dtos.responses.PartDTO;
import com.mercadolibre.fernandez_federico.dtos.responses.SubsidiaryOrdersByDeliveryStatusDTO;
import com.mercadolibre.fernandez_federico.models.ApplicationUser;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

public interface IUserService extends UserDetailsService {
    ApplicationUser findByUsername(String username);

    void saveUser(ApplicationUser applicationUser);
}
