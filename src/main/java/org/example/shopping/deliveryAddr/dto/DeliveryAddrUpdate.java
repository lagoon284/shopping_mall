package org.example.shopping.deliveryAddr.dto;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeliveryAddrUpdate {

    @NotBlank
    private String userId;

    @NotBlank
    private String addrAlias;

    private int deliAddrNo;

    @NotBlank
    private String deliAddr;
}
