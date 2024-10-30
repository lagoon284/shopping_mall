package org.example.shopping.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.shopping.util.common.RetAttributes;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class ProductUpdateReq extends RetAttributes {

    @NotNull
    private Long prodSeqNo;

    @NotBlank
    private String prodName;

    @NotBlank
    private String price;

    @NotBlank
    private String provider;

    @NotBlank
    private String info;

    private boolean useFrag;
}
