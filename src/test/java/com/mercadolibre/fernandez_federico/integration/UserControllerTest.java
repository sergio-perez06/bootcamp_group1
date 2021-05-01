package com.mercadolibre.fernandez_federico.integration;

import com.mercadolibre.fernandez_federico.dtos.request.ApplicationUserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class UserControllerTest extends ControllerTest {

    @Test
    void signUp() {
        ApplicationUserDTO user = new ApplicationUserDTO("sergio", "passs", "Argentina", "Admin");
        ResponseEntity<Object> responseEntity = this.testRestTemplate.exchange("/api/v1/users/signUp", HttpMethod.POST, new HttpEntity<>(user), Object.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }
}
