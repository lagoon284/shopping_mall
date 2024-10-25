package org.example.shopping.product.service;

import lombok.RequiredArgsConstructor;
import org.example.shopping.product.dto.ProductInfo;
import org.example.shopping.product.mapper.ProductMapper;
import org.example.shopping.util.exception.enums.ErrorCode;
import org.example.shopping.util.exception.CustomException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductMapper productMapper;

    public void insertProduct(ProductInfo productInfo) {

        int retVal = productMapper.insertProduct(productInfo);

        if (retVal != 1) {
            throw new CustomException(ErrorCode.INSERT_FAIL_PRODUCT_ERROR);
        }
    }

    public void multiInsertProdct(List<ProductInfo> productInfos) {

        int retVal = productMapper.multiInsertProduct(productInfos);

        if (productInfos.size() != retVal) {
            throw new CustomException(ErrorCode.INSERT_FAIL_PRODUCT_ERROR);
        }
    }

    public ProductInfo getOneProd(Long prodSeqNo) {

        ProductInfo prodInfo = productMapper.getOneProd(prodSeqNo);

        if (prodInfo == null) {
            throw new CustomException(ErrorCode.PRODUCT_NOT_FOUND);
        }

        return prodInfo;
    }

    public List<ProductInfo> getQuanProd() {

        List<ProductInfo> prodInfos = productMapper.getQuanProd();

        if (prodInfos.isEmpty()) {
            throw new CustomException(ErrorCode.SELECT_FAIL_PRODUCT_ERROR);
        }

        return prodInfos;
    }

    public void updateProd(ProductInfo productInfo) {

        int retVal = productMapper.updateProd(productInfo);

        if (retVal != 1) {
            throw new CustomException(ErrorCode.CONFLICT_REQUEST_PRODUCT);
        }
    }
}
