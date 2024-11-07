package org.example.shopping.deliveryAddr.dto;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeliveryAddrDelete {

    @NotBlank
    private String userId;

    @Min(value = 1)
    @Max(value = 5)
    private int deliAddrNo;
}
