package org.example.shopping.controller;

import lombok.RequiredArgsConstructor;
import org.example.shopping.dto.ProductInfo;
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
            return "PRODUCTINFO 테이블 INSERT 작업에 이상이 있습니다. => " + retVal;
        }

        return "PRODUCTINFO 테이블에 정상적으로 INSERT 되었습니다. => " + retVal;
    }

    // 상품 여러개 등록하기
    @PostMapping("/multiInsert")
    public String multiProductInsert(@RequestBody List<ProductInfo> productInfos) {

        int retVal = productService.multiInsertProdct(productInfos);

        if (retVal < 1) {
            return "PRODUCTINFO 테이블 INSERT 작업에 이상이 있습니다. (MULTI) => " + retVal;
        }

        return "PRODUCTINFO 테이블에 정상적으로 INSERT 되었습니다. (MULTI) => " + retVal;
    }

    // 한개의 상품 정보 가져오기
    @GetMapping("/info/{prodSeqNo}")
    public ProductInfo getOneProd(@PathVariable Long prodSeqNo) {

        return productService.getOneProd(prodSeqNo);
    }

    // 일단 전체로 불러오기 (추후 수정 예정 - 지시된 수량 별 혹은 페이지 별.)
    @GetMapping("/infoProds")
    public List<ProductInfo> getQuanProd() {

        return productService.getQuanProd();
    }

    // 단건 상품 수정
    // 상품 수정시 USEFRAG 값으로 사용 비사용 하여 조회시 노출/비노출 수정 가능.
    @PutMapping("/updateProd")
    public String updateProd(@RequestBody ProductInfo productInfo) {

        int retVal = productService.updateProd(productInfo);

        if (retVal != 1) {
            return "단건 UPDATE에 실패하였습니다. => " + retVal;
        }

        return "업데이트 성공 => " + retVal;
    }
}
