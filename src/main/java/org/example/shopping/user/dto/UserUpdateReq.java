package org.example.shopping.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.shopping.util.common.dto.RetAttributes;


@Data
@AllArgsConstructor
public class UserUpdateReq {        // 유저 정보 업데이트 요청 모델.

    @NotBlank
    private String id;              // 유저 아이디.

    @NotBlank
    private String pw;              // 계정 패스워드.

    @NotBlank
    private String name;            // 유저 네임.

    @NotBlank
    private String addr;            // 유저 주소.
}
