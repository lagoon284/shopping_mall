package org.example.shopping.product.service;

import lombok.RequiredArgsConstructor;
import org.example.shopping.product.dto.ProductDetail;
import org.example.shopping.product.dto.ProductInsertReq;
import org.example.shopping.product.dto.ProductUpdateReq;
import org.example.shopping.product.mapper.ProductMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductMapper productMapper;

    public void insertProduct(ProductInsertReq productInfo) {

        productMapper.insertProduct(productInfo);
    }

    @Transactional
    public void multiInsertProdct(List<ProductInsertReq> productInfos) {

        productMapper.multiInsertProduct(productInfos);
    }

    public ProductDetail getProdInfoBySeqNo(Long prodSeqNo) {

        return productMapper.getProdInfoBySeqNo(prodSeqNo);
    }

    public List<ProductDetail> getProdInfoByName(String prodName) {

        return productMapper.getProdInfoByName(prodName);
    }

    public List<ProductDetail> getQuanProd() {

        return productMapper.getQuanProd();
    }

    public void updateProd(ProductUpdateReq productInfo) {

        productMapper.updateProd(productInfo);
    }
}
