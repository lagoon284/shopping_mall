package org.example.shopping.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.example.shopping.util.common.dto.RetAttributes;

@Data
@AllArgsConstructor
@NoArgsConstructor          // 빼면 오류...?  JSON parse error: Cannot construct instance of `org.example.shopping.user.dto.UserDormancyReq` (although at least one Creator exists): cannot deserialize from Object value (no delegate- or property-based Creator)
public class UserDormencyReq {        // 유저 휴면전환 요청 모델.

    @NotBlank
    private String id;                // 유저 아이디.
}
