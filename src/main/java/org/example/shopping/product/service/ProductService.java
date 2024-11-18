package org.example.shopping.product.service;

import lombok.RequiredArgsConstructor;
import org.example.shopping.product.dto.ProductInfo;
import org.example.shopping.product.dto.ProductInsertReq;
import org.example.shopping.product.dto.ProductUpdateReq;
import org.example.shopping.product.mapper.ProductMapper;
import org.example.shopping.util.common.TimeConverter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductMapper productMapper;

    public void insertProduct(ProductInsertReq productInfo) {

        productInfo.setRegDate(TimeConverter.toDayToString());

        productMapper.insertProduct(productInfo);
    }

    public void multiInsertProdct(List<ProductInsertReq> productInfos) {

        for (ProductInsertReq prod : productInfos) {
            prod.setRegDate(TimeConverter.toDayToString());
        }

        productMapper.multiInsertProduct(productInfos);
    }

    public ProductInfo getOneProd(Long prodSeqNo) {

        return productMapper.getOneProd(prodSeqNo);
    }

    public List<ProductInfo> getQuanProd() {

        return productMapper.getQuanProd();
    }

    public void updateProd(ProductUpdateReq productInfo) {

        productInfo.setUpdDate(TimeConverter.toDayToString());

        productMapper.updateProd(productInfo);
    }
}
