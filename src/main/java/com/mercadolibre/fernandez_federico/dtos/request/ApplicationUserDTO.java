package com.mercadolibre.fernandez_federico.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class ApplicationUserDTO {

    private String username;
    private String password;
    @JsonProperty("country")
    private String country;
    @JsonProperty("role")
    private String role;

}
