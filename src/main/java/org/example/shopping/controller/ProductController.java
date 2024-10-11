package org.example.shopping.controller;

import org.example.shopping.model.ProductInfo;
import org.example.shopping.model.api.ApiRes;
import org.example.shopping.service.ProductService;
import org.example.shopping.util.TimeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    // 상품 한개 등록하기
    @PostMapping("/insert")
    public ResponseEntity<ApiRes<String>> productInsert(@RequestBody ProductInfo productInfo) {

        productInfo.setRegDate(TimeConverter.toDayToString());

        int insertResult = productService.insertProduct(productInfo);

        if (insertResult != 1) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ApiRes.diyResult("PRODUCTINFO 테이블 INSERT 작업에 이상이 있습니다.", null));
        }

        return ResponseEntity
                .ok(ApiRes.diyResult("PRODUCTINFO 테이블에 정상적으로 INSERT 되었습니다.", null));
    }

    // 상품 여러개 등록하기
    @PostMapping("/multiInsert")
    public ResponseEntity<ApiRes<String>> multiProductInsert(@RequestBody List<ProductInfo> productInfos) {

        for (ProductInfo prod : productInfos) {
            prod.setRegDate(TimeConverter.toDayToString());
        }

        int insertResult = productService.multiInsertProdct(productInfos);

        if (insertResult < 1) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ApiRes.diyResult("PRODUCTINFO 테이블 INSERT 작업에 이상이 있습니다. (MULTI)", null));
        }

        return ResponseEntity
                .ok(ApiRes.diyResult("PRODUCTINFO 테이블에 정상적으로 INSERT 되었습니다. (MULTI)", null));
    }

    // 한개의 상품 정보 가져오기
    @GetMapping("/info/{prodSeqNo}")
    public ResponseEntity<ApiRes<ProductInfo>> getOneProd(@PathVariable Long prodSeqNo) {
        ProductInfo oneProdInfo = productService.getOneProd(prodSeqNo);

        if (oneProdInfo == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ApiRes.diyResult("SELECT 작업 중 오류가 발생했습니다.", null));
        }

        return ResponseEntity
                .ok(ApiRes.diyResult("성공", oneProdInfo));
    }

    // 일단 전체로 불러오기 (추후 수정 예정 - 지시된 수량 별 혹은 페이지 별.)
    @GetMapping("/infoProds")
    public ResponseEntity<ApiRes<List<ProductInfo>>> getQuanProd() {

        List<ProductInfo> prodsInfo = productService.getQuanProd();

        if (prodsInfo == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ApiRes.diyResult("TABLE 에 데이터가 없습니다.", null));
        }

        return ResponseEntity
                .ok(ApiRes.diyResult("성공", prodsInfo));
    }

    // 단건 상품 수정
    // 상품 수정시 USEFRAG 값으로 사용 비사용 하여 조회시 노출/비노출 수정 가능.
    @PostMapping("/updateProd")
    public ResponseEntity<ApiRes<String>> updateProd(@RequestBody ProductInfo productInfo) {

        productInfo.setUpdDate(TimeConverter.toDayToString());

        int updResult = productService.updateProd(productInfo);

        if (updResult != 1) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ApiRes.diyResult("단건 UPDATE에 실패하였습니다.", null));
        }

        return ResponseEntity
                .ok(ApiRes.diyResult("업데이트 성공", null));
    }
}
