package com.ufps.gidisoft.requests.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserCredentialsRequest {

    @NotNull(message = "The username is required")
    @NotBlank(message = "The username cannot be empty")
    private String username;
    @NotNull(message = "The password is required")
    @NotBlank(message = "The password cannot be empty")
    private String password;
}
