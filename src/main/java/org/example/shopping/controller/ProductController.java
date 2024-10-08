package org.example.shopping.controller;

import org.example.shopping.model.ProductInfo;
import org.example.shopping.service.ProductService;
import org.example.shopping.util.TimeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    // 상품 한개 등록하기
    @PostMapping("/insert")
    public void productInsert(@RequestBody ProductInfo productInfo) {

        productInfo.setRegDate(TimeConverter.toDayToString());

        productService.insertProduct(productInfo);
    }

    // 상품 여러개 등록하기
    @PostMapping("/multiInsert")
    public void multiProductInsert(@RequestBody List<ProductInfo> productInfos) {

        for (ProductInfo prod : productInfos) {
            prod.setRegDate(TimeConverter.toDayToString());
        }

        productService.multiInsertProdct(productInfos);
    }

    // 한개의 상품 정보 가져오기
    @GetMapping("/info/{prodNo}")
    public ProductInfo getOneProd(@PathVariable String prodNo) {

        return productService.getOneProd(prodNo);
    }

    // 일단 전체로 불러오기
    @GetMapping("/infoProds")
    public List<ProductInfo> getQuanProd() {
        return productService.getQuanProd();
    }
}
