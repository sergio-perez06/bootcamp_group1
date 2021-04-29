package com.mercadolibre.fernandez_federico.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.fernandez_federico.dtos.request.ApplicationUserDTO;
import com.mercadolibre.fernandez_federico.models.Bill;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import java.util.ArrayList;
import java.util.List;;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PartControllerTest extends ControllerTest {


    @Test
    void getOrderDetailsTest() throws JsonProcessingException {
        ApplicationUserDTO user = new ApplicationUserDTO("janet","1234","Uruguay","admin");

       ResponseEntity<String> signUpResponse = this.testRestTemplate.exchange(
               "/users/signUp",
               HttpMethod.POST,
               new HttpEntity<>(user),
               String.class);

       if (HttpStatus.OK.equals(signUpResponse.getStatusCode())){

            JSONObject personJsonObject = new JSONObject();
            personJsonObject.put("username", "janet");
            personJsonObject.put("password", "1234");

            ResponseEntity<String> loginResponse = this.testRestTemplate.postForObject(
                    "localhost:8080/login",
                    personJsonObject,
                    ResponseEntity.class);

            if(HttpStatus.OK.equals(loginResponse.getStatusCode())){
               String token = loginResponse.getHeaders().getFirst("token");

               if(!token.isEmpty()){
                   HttpHeaders headers = new HttpHeaders();
                   headers.add("Authorization",token);

                   HttpEntity entity = new HttpEntity(headers);
                   ResponseEntity<String> response = this.testRestTemplate.exchange
                           ("localhost:8080/api/v1/parts/list", HttpMethod.GET, entity, String.class);

                   assertEquals(HttpStatus.OK, response.getStatusCode());
                   assertEquals( createListBill() ,response.getBody());
               }
            }
        }
    }

    private List<Bill> createListBill() throws JsonProcessingException {
        List<Bill> resp = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        String bill = "[\n" +
                "    {\n" +
                "        “part_code”: “00000001\",\n" +
                "        “description”: “Farol genérico de Chevrolet Spark”,\n" +
                "        “maker”: “CHEVROLET”,\n" +
                "        “quantity”: 2500,\n" +
                "        “discount_type”: “Cliente VIP”,\n" +
                "        “normal_price”: 110.0,\n" +
                "        “urgent_price”: 135.0,\n" +
                "        “net_weight”: 1000,\n" +
                "        “long_dimension”: 30,\n" +
                "        “width_dimension”: 60,\n" +
                "        “tall_dimension”: 50,\n" +
                "        “last_modification”: “2021-02-25”\n" +
                "    },\n" +
                "    {\n" +
                "        “part_code”: “00000002”,\n" +
                "        “description”: “Paragolpe trasero de Ford Fiesta”,\n" +
                "        “maker”: “FORD”,\n" +
                "        “quantity”: 1500,\n" +
                "        “discount_type”: “Cliente VIP”,\n" +
                "        “normal_price”: 175.0,\n" +
                "        “urgent_price”: 200.0,\n" +
                "        “net_weight”: 2500,\n" +
                "        “long_dimension”: 130,\n" +
                "        “width_dimension”: 40,\n" +
                "        “tall_dimension”: 50,\n" +
                "        “last_modification”: “2021-01-05\"\n" +
                "    },\n" +
                "    {\n" +
                "        “part_code”: “00000003\",\n" +
                "        “description”: “Paragolpe frontal de Fiat Uno”,\n" +
                "        “maker”: “FIAT”,\n" +
                "        “quantity”: 1750,\n" +
                "        “discount_type”: “Cliente VIP”,\n" +
                "        “normal_price”: 160.0,\n" +
                "        “urgent_price”: 175.0,\n" +
                "        “net_weight”: 2500,\n" +
                "        “long_dimension”: 130,\n" +
                "        “width_dimension”: 40,\n" +
                "        “tall_dimension”: 50,\n" +
                "        “last_modification”: “2021-02-21”\n" +
                "    },\n" +
                "    {\n" +
                "        “part_code”: “00000004”,\n" +
                "        “description”: “Volante original de Fiat 127”,\n" +
                "        “maker”: “FIAT”,\n" +
                "        “quantity”: 125,\n" +
                "        “discount_type”: “Cliente VIP”,\n" +
                "        “normal_price”: 140.0,\n" +
                "        “urgent_price”: 160.0,\n" +
                "        “net_weight”: 1500,\n" +
                "        “long_dimension”: 60,\n" +
                "        “width_dimension”: 40,\n" +
                "        “tall_dimension”: 50,\n" +
                "        “last_modification”: “2021-01-15\"\n" +
                "    },\n" +
                "    {\n" +
                "        “part_code”: “00000005\",\n" +
                "        “description”: “Tablero de Nissan March”,\n" +
                "        “maker”: “NISSAN”,\n" +
                "        “quantity”: 475,\n" +
                "        “discount_type”: “Cliente VIP”,\n" +
                "        “normal_price”: 235.0,\n" +
                "        “urgent_price”: 265.0,\n" +
                "        “net_weight”: 2500,\n" +
                "        “long_dimension”: 130,\n" +
                "        “width_dimension”: 40,\n" +
                "        “tall_dimension”: 50,\n" +
                "        “last_modification”: “2020-05-25”\n" +
                "    }\n" +
                "]";
        return objectMapper.readValue(bill, new TypeReference<List<Bill>>(){});
    }


}
