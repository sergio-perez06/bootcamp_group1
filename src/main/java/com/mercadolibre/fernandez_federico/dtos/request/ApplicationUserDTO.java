package com.mercadolibre.fernandez_federico.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
public class ApplicationUserDTO {

    @NonNull
    private String username;

    @NonNull
    private String password;

    @JsonProperty("country")
    private String country;

    @JsonProperty("role")
    private String role;

}
