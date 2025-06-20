package org.example.shopping.product.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.shopping.product.dto.ProductInfo;
import org.example.shopping.product.dto.ProductInsertReq;
import org.example.shopping.product.dto.ProductUpdateReq;
import org.example.shopping.product.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    // 상품 한개 등록하기
    @PostMapping("/insert")
    public void productInsert(@RequestBody @Valid ProductInsertReq productInfo) {

        productService.insertProduct(productInfo);
    }

    // 상품 여러개 등록하기
    @PostMapping("/multiInsert")
    public void multiProductInsert(@RequestBody @Valid List<ProductInsertReq> productInfos) {

        productService.multiInsertProdct(productInfos);
    }

    // 한개의 상품 정보 가져오기 (상품 번호 기준)
    @GetMapping("/prodNo/{prodSeqNo}")
    public ProductInfo getProdInfoBySeqNo(@PathVariable Long prodSeqNo) {

        return productService.getProdInfoBySeqNo(prodSeqNo);
    }

    // 상품 이름을 기반으로 여러개의 정보 가져오기 (상품 이름 기준)
    @GetMapping("/prodName/{prodName}")
    public List<ProductInfo> getProdInfoByName(@PathVariable String prodName) {

        return productService.getProdInfoByName(prodName);
    }

    // 일단 전체로 불러오기 (추후 수정 예정 - 지시된 수량 별 혹은 페이지 별.)
    @GetMapping("/infoProds")
    public List<ProductInfo> getQuanProd() {

        return productService.getQuanProd();
    }

    // 단건 상품 수정
    // 상품 수정시 USEFLAG 값으로 사용 비사용 하여 조회시 노출/비노출 수정 가능.
    @PutMapping("/updateProd")
    public void updateProd(@RequestBody @Valid ProductUpdateReq productInfo) {

        productService.updateProd(productInfo);
    }
}
