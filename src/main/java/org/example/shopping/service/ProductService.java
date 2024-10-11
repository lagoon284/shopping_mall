package org.example.shopping.service;

import org.example.shopping.mapper.ProductMapper;
import org.example.shopping.model.ProductInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductMapper productMapper;

    public int insertProduct(ProductInfo productInfo) {
        return productMapper.insertProduct(productInfo);
    }

    public int multiInsertProdct(List<ProductInfo> productInfos) {
        return productMapper.multiInsertProduct(productInfos);
    }

    public ProductInfo getOneProd(Long prodSeqNo) {
        return productMapper.getOneProd(prodSeqNo);
    }

    public List<ProductInfo> getQuanProd() {
        return productMapper.getQuanProd();
    }

    public int updateProd(ProductInfo productInfo) {
        return productMapper.updateProd(productInfo);
    }
}
