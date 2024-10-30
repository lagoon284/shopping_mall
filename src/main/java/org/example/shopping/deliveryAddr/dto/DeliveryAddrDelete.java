package org.example.shopping.deliveryAddr.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeliveryAddrDelete {

    @NotBlank
    private String userId;

    @Size(min = 1, max = 5)
    private int deliAddrNo;
}
