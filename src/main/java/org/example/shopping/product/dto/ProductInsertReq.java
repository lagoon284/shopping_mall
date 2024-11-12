package org.example.shopping.product.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.shopping.util.common.RetAttributes;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class ProductInsertReq extends RetAttributes {

    @NotBlank
    private String prodName;

    @NotBlank
    private String price;

    @NotBlank
    private String provider;

    @NotBlank
    private String info;

    private boolean useFlag;
}
