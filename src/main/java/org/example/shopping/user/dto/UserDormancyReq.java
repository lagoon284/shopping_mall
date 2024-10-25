package org.example.shopping.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.example.shopping.util.common.RetAttributes;

@Data
@AllArgsConstructor
@NoArgsConstructor          // 빼면 오류...?  JSON parse error: Cannot construct instance of `org.example.shopping.user.dto.UserDormancyReq` (although at least one Creator exists): cannot deserialize from Object value (no delegate- or property-based Creator)
@EqualsAndHashCode(callSuper = false)
public class UserDormancyReq extends RetAttributes {

    private String id;
}
