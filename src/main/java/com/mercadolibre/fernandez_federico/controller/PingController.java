package com.mercadolibre.fernandez_federico.controller;

import com.mercadolibre.fernandez_federico.util.TokenUtils;
import com.mercadolibre.fernandez_federico.util.filters.AuthenticationFilter;
import com.newrelic.api.agent.NewRelic;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.DatatypeConverter;
import java.util.Hashtable;
import java.util.Map;

@RestController
public class PingController {

	@Autowired
	private TokenUtils tokenUtils;


	@GetMapping("/ping")
	public String ping(@RequestHeader("Authorization") String token ) {
		NewRelic.ignoreTransaction();


		System.out.println(token);

		Map<String,Object> claims = tokenUtils.getAllClaimsFromToken(token);

		System.out.println(claims);
		System.out.println(claims.get("country"));


		return "pong";
	}
}
