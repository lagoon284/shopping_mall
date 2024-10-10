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

    // 일단 전체로 불러오기 (추후 수정 예정 - 지시된 수량 별 혹은 페이지 별.)
    @GetMapping("/infoProds")
    public List<ProductInfo> getQuanProd() {
        return productService.getQuanProd();
    }

    // 단건 상품 수정
    // 상품 수정시 USEFRAG 값으로 사용 비사용 하여 조회시 노출/비노출 수정 가능.
    @PostMapping("/updateProd")
    public void updateProd(@RequestBody ProductInfo productInfo) {

        productInfo.setUpdDate(TimeConverter.toDayToString());

        productService.updateProd(productInfo);
    }
}
