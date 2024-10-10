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

    public void insertProduct(ProductInfo productInfo) {
        productMapper.insertProduct(productInfo);
    }

    public void multiInsertProdct(List<ProductInfo> productInfos) {
        productMapper.multiInsertProduct(productInfos);
    }

    public ProductInfo getOneProd(String prodNo) {
        return productMapper.getOneProd(prodNo);
    }

    public List<ProductInfo> getQuanProd() {
        return productMapper.getQuanProd();
    }

    public void updateProd(ProductInfo productInfo) {
        productMapper.updateProd(productInfo);
    }
}
