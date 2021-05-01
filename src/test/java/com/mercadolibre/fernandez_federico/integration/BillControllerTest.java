package com.mercadolibre.fernandez_federico.integration;

import org.junit.jupiter.api.Test;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BillControllerTest extends ControllerTest {
    @Test
    void getOrdersListWithParamOK() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", this.getFullToken());
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<Object> response = this.testRestTemplate.exchange(
                "/api/v1/orders/list?orderNumberCM=0002-0001-00000001",
                HttpMethod.GET,
                entity,
                Object.class
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
