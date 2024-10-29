package org.example.shopping.product.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.shopping.util.common.RetAttributes;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class ProductInsertReq extends RetAttributes {

    private String prodName;
    private String price;
    private String provider;
    private String info;
    private boolean useFrag;
}
