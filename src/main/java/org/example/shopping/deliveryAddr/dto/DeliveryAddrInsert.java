package org.example.shopping.deliveryAddr.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.shopping.util.common.RetAttributes;

@Data
@AllArgsConstructor
public class DeliveryAddrInsert {

    @NotBlank
    private String userId;

    private int deliAddrNo;

    @NotBlank
    private String addrAlias;

    @NotBlank
    private String deliAddr;

    private boolean defDeliAddr;
}
