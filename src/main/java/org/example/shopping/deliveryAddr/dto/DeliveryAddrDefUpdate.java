package org.example.shopping.deliveryAddr.dto;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryAddrDefUpdate {

    @NotBlank
    private String userId;

    private int deliAddrNo;

    private boolean defDeliAddr;
}
