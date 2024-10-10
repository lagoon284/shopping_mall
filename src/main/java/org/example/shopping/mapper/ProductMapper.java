package org.example.shopping.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.shopping.model.ProductInfo;

import java.util.List;

@Mapper
public interface ProductMapper {

    // 상품 등록
    void insertProduct(ProductInfo productInfo);

    // 다중 상품 등록
    void multiInsertProduct(List<ProductInfo> productInfos);

    // 상품 한개 조회?? 검색어?? 일단 read 느낌으로
    ProductInfo getOneProd(String prodNo);

    // 상품 전체 조회 일단은.
    List<ProductInfo> getQuanProd();

    void updateProd(ProductInfo productInfo);
}
