package org.example.shopping.model;

import lombok.*;
import org.example.shopping.util.TimeConverter;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductInfo {

    // 시퀀스 번호 auto increment.
    private Long prodSeqNo;
    // 상품 이름.
    private String prodName;
    // 상품 가격.
    private String price;
    // 상품 판매처명.
    private String provider;
    // 상품 정보.
    private String info;
    // 조회 시 노출 여부. (판매/비판매)
    private boolean useFrag;
    // 등록 날짜. ex) yyyy-MM-dd HH:mm:ss
    private String regDate;
    // 수정 날짜. 패턴은 등록 날짜와 동일.
    private String updDate;

}
