package org.example.shopping.deliveryAddr.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.shopping.util.common.RetAttributes;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DeliveryAddrInsert extends RetAttributes {

    @NotBlank
    private String userId;

    @NotBlank
    private String addrAlias;

    @NotBlank
    private String deliAddr;
}
