package org.example.shopping.product.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.shopping.product.dto.ProductInfo;
import org.example.shopping.product.dto.ProductInsertReq;
import org.example.shopping.product.dto.ProductUpdateReq;
import org.example.shopping.product.service.ProductService;
import org.example.shopping.util.common.ValidationUtil;
import org.example.shopping.util.exception.enums.ErrorCode;
import org.example.shopping.util.exception.CustomException;
import org.example.shopping.util.common.TimeConverter;
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
    public String productInsert(@RequestBody ProductInsertReq productInfo) {

        if (ValidationUtil.validateObject(productInfo)) {
            throw new CustomException(ErrorCode.INVALID_PARAMETER);
        }

        productService.insertProduct(productInfo);

        return "Success";
    }

    // 상품 여러개 등록하기
    @PostMapping("/multiInsert")
    public String multiProductInsert(@RequestBody List<ProductInsertReq> productInfos) {

        int index = 1;

        for (ProductInsertReq prod : productInfos) {

            prod.setRegDate(TimeConverter.toDayToString());

            if (ValidationUtil.validateObject(prod)) {
                log.info("{} 개 중, {} 번째 항목에서 적정하지 않은 Parameter가 있습니다.", productInfos.size(), index);
                throw new CustomException(ErrorCode.INVALID_PARAMETER);
            }

            index++;
        }

        productService.multiInsertProdct(productInfos);

        return "Success";
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
    public String updateProd(@RequestBody ProductUpdateReq productInfo) {

        if (ValidationUtil.validateObject(productInfo)) {
            throw new CustomException(ErrorCode.INVALID_PARAMETER);
        }

        productService.updateProd(productInfo);

        return "Success";
    }
}
