package org.example.shopping.controller;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.jdbc.Null;
import org.example.shopping.dto.ProductInfo;
import org.example.shopping.enums.ErrorCode;
import org.example.shopping.exception.CustomException;
import org.example.shopping.service.ProductService;
import org.example.shopping.util.TimeConverter;
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

        productInfo.setRegDate(TimeConverter.toDayToString());

        try {
            boolean prodParamCheck = productInfo.getProdName().isEmpty()
                    || productInfo.getPrice().isEmpty()
                    || productInfo.getProvider().isEmpty()
                    || productInfo.getRegDate().isEmpty();

            if (prodParamCheck) {
                throw new CustomException(ErrorCode.INVALID_PARAMETER);
            }
        } catch (NullPointerException e) {
            throw new CustomException(ErrorCode.INVALID_PARAMETER);
        }

        productService.insertProduct(productInfo);

        return "Success";
    }

    // 상품 여러개 등록하기
    @PostMapping("/multiInsert")
    public String multiProductInsert(@RequestBody List<ProductInfo> productInfos) {

        if (productInfos.isEmpty()) {
            throw new CustomException(ErrorCode.INVALID_PARAMETER);
        }

        for (ProductInfo prod : productInfos) {

            prod.setRegDate(TimeConverter.toDayToString());

            try {
                boolean prodParamCheck = prod.getProdName().isEmpty()
                        || prod.getPrice().isEmpty()
                        || prod.getProvider().isEmpty()
                        || prod.getRegDate().isEmpty();

                if (prodParamCheck) {
                    throw new CustomException(ErrorCode.INVALID_PARAMETER);
                }
            } catch (NullPointerException e) {
                throw new CustomException(ErrorCode.INVALID_PARAMETER);
            }
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
    public String updateProd(@RequestBody ProductInfo productInfo) {

        productInfo.setUpdDate(TimeConverter.toDayToString());

        try {
            boolean prodParamCheck = productInfo.getProdSeqNo() == 0
                    || productInfo.getProdName().isEmpty()
                    || productInfo.getPrice().isEmpty()
                    || productInfo.getProvider().isEmpty()
                    || productInfo.getInfo().isEmpty()
                    || productInfo.getUpdDate().isEmpty();

            if (prodParamCheck) {
                throw new CustomException(ErrorCode.INVALID_PARAMETER);
            }
        } catch (NullPointerException e) {
            throw new CustomException(ErrorCode.INVALID_PARAMETER);
        }

        productService.updateProd(productInfo);

        return "Success";
    }
}
