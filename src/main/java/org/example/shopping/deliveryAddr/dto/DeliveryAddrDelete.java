package org.example.shopping.deliveryAddr.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeliveryAddrDelete {

    @NotBlank
    private String userId;

    private int deliAddrNo;
}
