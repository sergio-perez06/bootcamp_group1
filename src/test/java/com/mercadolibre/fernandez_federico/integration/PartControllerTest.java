package com.mercadolibre.fernandez_federico.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.mercadolibre.fernandez_federico.dtos.request.ApplicationUserDTO;
import com.mercadolibre.fernandez_federico.dtos.request.BillRequestDTO;
import com.mercadolibre.fernandez_federico.dtos.request.CountryDealerStockDTO;
import com.mercadolibre.fernandez_federico.dtos.request.PostBillDetailDTO;
import com.mercadolibre.fernandez_federico.dtos.responses.BillDetailDTO;
import com.mercadolibre.fernandez_federico.models.Bill;
import com.mercadolibre.fernandez_federico.models.BillDetail;
import com.mercadolibre.fernandez_federico.util.enums.AccountType;
import net.minidev.json.JSONObject;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.http.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PartControllerTest extends ControllerTest {

    @BeforeAll
    void register (){
        this.getFullToken();
    }

    @Test
    void getOrderDetailsTest() throws JsonProcessingException {
        ApplicationUserDTO user = new ApplicationUserDTO("janet", "1234", "Uruguay", "admin");

        ResponseEntity<String> signUpResponse = this.testRestTemplate.exchange(
                "/api/v1/users/signUp",
                HttpMethod.POST,
                new HttpEntity<>(user),
                String.class);

        if (HttpStatus.OK.equals(signUpResponse.getStatusCode())) {

            JSONObject personJsonObject = new JSONObject();
            personJsonObject.put("username", "janet");
            personJsonObject.put("password", "1234");

            ResponseEntity<String> loginResponse = this.testRestTemplate.exchange(
                    "/login",
                    HttpMethod.POST,
                    new HttpEntity<>(personJsonObject),
                    String.class);

            if (HttpStatus.OK.equals(loginResponse.getStatusCode())) {
                String token = loginResponse.getHeaders().getFirst("token");

                if(token!=null && !token.isEmpty()){
                    HttpHeaders headers = new HttpHeaders();
                    headers.add("Authorization", token);

                    HttpEntity entity = new HttpEntity(headers);
                    ResponseEntity<String> response = this.testRestTemplate.exchange
                            ("/api/v1/parts/list", HttpMethod.GET, entity, String.class);

                    assertEquals(HttpStatus.OK, response.getStatusCode());
                }
            }
        }
    }

    @Test
    public void getPartsOrdersWithParamOK() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", this.getToken());

        HttpEntity entity = new HttpEntity(headers);

        ResponseEntity<Object> response = this.testRestTemplate.exchange(
                "/api/v1/parts/orders?dealerNumber=0001",
                HttpMethod.GET,
                entity,
                Object.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void getPartsOrdersWithParamDeliveryStatusOK() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", this.getToken());
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<Object> response = this.testRestTemplate.exchange(
                "/api/v1/parts/orders?dealerNumber=0001&deliveryStatus=D",
                HttpMethod.GET,
                entity,
                Object.class
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void getPartsOrdersWithParamDeliveryStatusAndOrderOK() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", this.getToken());
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<Object> response = this.testRestTemplate.exchange(
                "/api/v1/parts/orders?dealerNumber=0001&deliveryStatus=D&order=1",
                HttpMethod.GET,
                entity,
                Object.class
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void getPartsOrdersWithParamOrderOK() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", this.getToken());
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<Object> response = this.testRestTemplate.exchange(
                "/api/v1/parts/orders?dealerNumber=0001&order=1",
                HttpMethod.GET,
                entity,
                Object.class
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void getPartsOrdersShouldThrowSubsidiaryNotFound() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", this.getToken());
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<Object> response = this.testRestTemplate.exchange(
                "/api/v1/parts/orders?dealerNumber=0348&deliveryStatus=D&order=1",
                HttpMethod.GET,
                entity,
                Object.class
        );
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void getPartsOrdersShouldThrowBadRequestDeliveryStatus() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", this.getToken());
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<Object> response = this.testRestTemplate.exchange(
                "/api/v1/parts/orders?dealerNumber=0001&deliveryStatus=Demorado&order=1",
                HttpMethod.GET,
                entity,
                Object.class
        );
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void postPartsOK() {
        CountryDealerStockDTO request = new CountryDealerStockDTO();
        request.setPartCode("00000005");
        request.setQuantity(10);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", this.getToken());

        HttpEntity entity = new HttpEntity(request, headers);

        ResponseEntity<Object> response = this.testRestTemplate.exchange(
                "/api/v1/parts",
                HttpMethod.POST,
                entity,
                Object.class
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void postPartsOrdersOK() {
        PostBillDetailDTO billDetail = new PostBillDetailDTO();
        billDetail.setPartCode("00000001");
        billDetail.setQuantity(1);
        billDetail.setAccountType(AccountType.Repuesto);

        BillRequestDTO request = new BillRequestDTO();
        request.setBillDetails(List.of(billDetail));
        request.setDeliveryDate(LocalDate.of(2021, 06, 01));
        request.setSubsidiaryNumber("0001");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", this.getToken());

        HttpEntity entity = new HttpEntity(request, headers);

        ResponseEntity<Object> response = this.testRestTemplate.exchange(
                "/api/v1/parts/orders",
                HttpMethod.POST,
                entity,
                Object.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
