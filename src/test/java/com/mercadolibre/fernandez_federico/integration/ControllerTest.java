package com.mercadolibre.fernandez_federico.integration;

import com.mercadolibre.fernandez_federico.dtos.request.ApplicationUserDTO;
import com.mysql.cj.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

public abstract class ControllerTest extends IntegrationTest {
	@Autowired
	protected TestRestTemplate testRestTemplate;

	protected <T> RequestEntity<T> getDefaultRequestEntity() {
		HttpHeaders headers = new HttpHeaders();
		return new RequestEntity<>(headers, HttpMethod.GET, null);
	}

	protected String getFullToken() {
		ResponseEntity<Object> responseEntity = signUp("admin","test1234","Argentina","ADMIN");
		boolean OK = responseEntity.getStatusCode() == HttpStatus.OK || responseEntity.getStatusCode() == HttpStatus.BAD_REQUEST;
		if (!OK)
			return "";
		responseEntity = login("admin", "test1234");
		OK = responseEntity.getStatusCode() == HttpStatus.OK;
		if (!OK) return "";
		return responseEntity.getHeaders().get("token").get(0);
	}

	protected String getToken() {
		ResponseEntity<Object> responseEntity = login("admin", "test1234");
		boolean OK = responseEntity.getStatusCode() == HttpStatus.OK;
		if (!OK) return "";
		return responseEntity.getHeaders().get("token").get(0);
	}

	protected ResponseEntity<Object> signUp(String username, String password, String country, String role) {
		ApplicationUserDTO user = new ApplicationUserDTO(username, password, country, role);
		ResponseEntity<Object> responseEntity = this.testRestTemplate.exchange("/api/v1/users/signUp", HttpMethod.POST, new HttpEntity<>(user), Object.class);
		return responseEntity;
	}

	protected ResponseEntity<Object> login(String username, String password) {
		ApplicationUserDTO login = new ApplicationUserDTO(username, password);
		ResponseEntity<Object> responseEntity = this.testRestTemplate.exchange("/login", HttpMethod.POST, new HttpEntity<>(login), Object.class);
		return responseEntity;
	}
}
