package com.mercadolibre.fernandez_federico.controller;

import com.mercadolibre.fernandez_federico.util.TokenUtils;
import com.newrelic.api.agent.NewRelic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
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
