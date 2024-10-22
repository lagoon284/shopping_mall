package org.example.shopping.service;

import lombok.RequiredArgsConstructor;
import org.example.shopping.mapper.ProductMapper;
import org.example.shopping.dto.ProductInfo;
import org.example.shopping.util.TimeConverter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductMapper productMapper;

    public int insertProduct(ProductInfo productInfo) {

        productInfo.setRegDate(TimeConverter.toDayToString());

        return productMapper.insertProduct(productInfo);
    }

    public int multiInsertProdct(List<ProductInfo> productInfos) {

        for (ProductInfo prod : productInfos) {
            prod.setRegDate(TimeConverter.toDayToString());
        }

        return productMapper.multiInsertProduct(productInfos);
    }

    public ProductInfo getOneProd(Long prodSeqNo) {
        return productMapper.getOneProd(prodSeqNo);
    }

    public List<ProductInfo> getQuanProd() {
        return productMapper.getQuanProd();
    }

    public int updateProd(ProductInfo productInfo) {

        productInfo.setUpdDate(TimeConverter.toDayToString());

        return productMapper.updateProd(productInfo);
    }
}
