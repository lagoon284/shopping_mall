package org.example.shopping.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.shopping.util.common.RetAttributes;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class ProductUpdateReq extends RetAttributes {

    private Long prodSeqNo;
    private String prodName;
    private String price;
    private String provider;
    private String info;
    private boolean useFrag;
}
