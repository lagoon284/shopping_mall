package org.example.shopping.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInsertReq extends User {

    @NotBlank
    private String id;

    @NotBlank
    private String pw;

    @NotBlank
    private String name;

    @NotBlank
    private String addr;

}
