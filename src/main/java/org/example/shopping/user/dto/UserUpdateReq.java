package org.example.shopping.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.shopping.util.common.RetAttributes;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class UserUpdateReq extends RetAttributes {

    private String id;
    private String pw;
    private String name;
    private String addr;
}
