package org.example.shopping.controller;

import org.example.shopping.model.ProductInfo;
import org.example.shopping.service.ProductService;
import org.example.shopping.util.TimeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/insert")
    public void productInsert(@RequestBody ProductInfo productInfo) {

        productInfo.setRegDate(TimeConverter.toDayToString());

        productService.insertProduct(productInfo);
    }

    @PostMapping("/multiInsert")
    public void multiProductInsert(@RequestBody List<ProductInfo> productInfos) {

        for (ProductInfo prod : productInfos) {
            prod.setRegDate(TimeConverter.toDayToString());
        }

        productService.multiInsertProdct(productInfos);
    }
}
