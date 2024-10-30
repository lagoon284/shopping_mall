package org.example.shopping.authLogin.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Login {

    @NotBlank
    private String userId;

    @NotBlank
    private String pw;
}
