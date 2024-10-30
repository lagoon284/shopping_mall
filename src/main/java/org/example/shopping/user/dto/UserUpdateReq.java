package org.example.shopping.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.shopping.util.common.RetAttributes;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class UserUpdateReq extends RetAttributes {

    @NotBlank
    private String id;

    @NotBlank
    private String pw;

    @NotBlank
    private String name;

    @NotBlank
    private String addr;
}
