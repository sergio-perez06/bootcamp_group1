package com.mercadolibre.fernandez_federico.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ApiError {
	private String error;
	private String message;
	private Integer status;

	public ApiError() { }

	public ApiError(String error, String message, Integer status) {
		this.error = error;
		this.message = message;
		this.status = status;
	}

	public ApiError(String error, List<String> messages, Integer status) {
		this.error = error;
		this.message = String.join(", ", messages);
		this.status = status;
	}
}
