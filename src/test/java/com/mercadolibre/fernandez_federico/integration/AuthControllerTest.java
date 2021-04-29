package com.mercadolibre.fernandez_federico.integration;

import com.mercadolibre.fernandez_federico.dtos.request.ApplicationUserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

public class AuthControllerTest extends ControllerTest {

    @Test
    public String getToken() {
        ApplicationUserDTO user = new ApplicationUserDTO("admin","test1234","Argentina","ADMIN");
        ResponseEntity<Object> responseEntity = this.testRestTemplate.exchange("/users/signUp", HttpMethod.POST, new HttpEntity<>(user), Object.class);
        System.out.println(responseEntity.toString());

        ApplicationUserDTO login = new ApplicationUserDTO("admin","test1234");
        responseEntity = this.testRestTemplate.exchange("/login", HttpMethod.POST, new HttpEntity<>(login), Object.class);
        return responseEntity.getHeaders().get("token").get(0);
    }
}
