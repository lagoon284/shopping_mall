package org.example.shopping.product.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.shopping.product.dto.ProductInfo;
import org.example.shopping.product.dto.ProductInsertReq;
import org.example.shopping.product.dto.ProductUpdateReq;

import java.util.List;

@Mapper
public interface ProductMapper {

    // 상품 등록
    int insertProduct(ProductInsertReq productInfo);

    // 다중 상품 등록
    int multiInsertProduct(List<ProductInsertReq> productInfos);

    // 상품 한개 조회?? 검색어?? 일단 read 느낌으로 (상품번호로 조회)
    ProductInfo getProdInfoBySeqNo(Long prodSeqNo);

    // 상품 한개 조회?? 검색어?? 일단 read 느낌으로 (상품이름으로 조회)
    List<ProductInfo> getProdInfoByName(String prodName);

    // 상품 전체 조회 일단은.
    List<ProductInfo> getQuanProd();

    int updateProd(ProductUpdateReq productInfo);
}
