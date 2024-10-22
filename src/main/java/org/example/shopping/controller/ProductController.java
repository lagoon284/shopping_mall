package org.example.shopping.controller;

import lombok.RequiredArgsConstructor;
import org.example.shopping.dto.ProductInfo;
import org.example.shopping.enums.ErrorCode;
import org.example.shopping.exception.CustomException;
import org.example.shopping.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // 상품 한개 등록하기
    @PostMapping("/insert")
    public String productInsert(@RequestBody ProductInfo productInfo) {

        int retVal = productService.insertProduct(productInfo);

        if (retVal != 1) {
            throw new CustomException(ErrorCode.INSERT_FAIL_PRODUCT_ERROR);
        }

        return "Success";
    }

    // 상품 여러개 등록하기
    @PostMapping("/multiInsert")
    public String multiProductInsert(@RequestBody List<ProductInfo> productInfos) {

        int retVal = productService.multiInsertProdct(productInfos);

        if (retVal < 1) {
            throw new CustomException(ErrorCode.INSERT_FAIL_PRODUCT_ERROR);
        }

        return "Success";
    }

    // 한개의 상품 정보 가져오기
    @GetMapping("/info/{prodSeqNo}")
    public ProductInfo getOneProd(@PathVariable Long prodSeqNo) {

        ProductInfo prodInfo = productService.getOneProd(prodSeqNo);

        if (prodInfo == null) {
            throw new CustomException(ErrorCode.PRODUCT_NOT_FOUND);
        }

        return prodInfo;
    }

    // 일단 전체로 불러오기 (추후 수정 예정 - 지시된 수량 별 혹은 페이지 별.)
    @GetMapping("/infoProds")
    public List<ProductInfo> getQuanProd() {

        List<ProductInfo> prodInfos = productService.getQuanProd();

        if (prodInfos.isEmpty()) {
            throw new CustomException(ErrorCode.SELECT_FAIL_PRODUCT_ERROR);
        }

        return prodInfos;
    }

    // 단건 상품 수정
    // 상품 수정시 USEFRAG 값으로 사용 비사용 하여 조회시 노출/비노출 수정 가능.
    @PutMapping("/updateProd")
    public String updateProd(@RequestBody ProductInfo productInfo) {

        int retVal = productService.updateProd(productInfo);

        if (retVal != 1) {
            throw new CustomException(ErrorCode.CONFLICT_REQUEST_PRODUCT);
        }

        return "Success";
    }
}
