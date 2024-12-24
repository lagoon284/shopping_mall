package org.example.shopping.util.exception.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    // 400 BAD_REQUEST 잘못된 요청.
    INVALID_PARAMETER(400, "요청 값이 적절하지 않습니다."),

    // 401 Unauthorized
    USER_NOT_EQUALS_PASSWORD(401, "입력하신 아이디 혹은 비밀번호가 일치하지 않습니다."),

    // 404 NOT_FOUND 잘못된 리소스 접근
    USER_NOT_FOUND(404, "존재하지 않는 회원 ID 입니다."),
    PRODUCT_NOT_FOUND(404, "존재하지 않는 상품 ID 입니다."),
    ORDER_NOT_FOUND(404, "존재하지 않는 주문 ID 입니다."),
    DELIVERY_ADDR_NOT_FOUND(404, "존재하지 않는 배송지 입니다."),   // 일단 넣어놓은 추후 빠질 수 있음...

    // 403 FORBIDDEN 지정한 리소스에 대한 엑세스가 금지됨.
    AUTH_SIGNATURE_FAIL_ERROR(403, "엑세스 토큰이 일치하지 않습니다."),
    AUTH_SIGNATURE_EXPIRED_ERROR(401, "엑세스 토큰의 유효시간이 만료 되었습니다."),
    AUTH_REF_SIGNATURE_FAIL_ERROR(403, "REFRESH TOKEN 값이 일치하지 않습니다."),
    AUTH_REF_SIGNATURE_EXPIRED_ERROR(401, "REFRESH TOKEN 의 유효기간이 만료 되었습니다."),

    AUTH_REF_SIGNATURE_UPDATE_ERROR(403, "새로 발급된 엑세스 토큰의 UPDATE가 실패하였습니다."),
    AUTH_REF_SIGNATURE_INSERT_ERROR(403, "새로 발급된 엑세스 토큰의 INSERT가 실패하였습니다."),

    // 409 CONFLICT 중복된 리소스.
    ALREADY_SAVED_USER(409, "이미 존재하는 회원 ID 입니다."),
    // ALREADY_SAVED_PRODUCT(409, "이미 존재하는 상품 입니다."),          // 일단 주석... 동일한 상품도 재등록 할 수 있음...
    ALREADY_SAVED_ORDER(409, "이미 존재하는 주문 입니다."),
    ALREADY_SAVED_DELIVERY(409, "이미 존재하는 배송지 입니다."),
    ALREADY_SAVED_DELIVERY_ALIAS(409, "이미 존재하는 배송지 별명입니다."),

    // 409 CONFLICT 비즈니스 로직 충돌이나 이미 수정한 리소스를 수정하려 할 때.
    CONFLICT_REQUEST_USER(409, "단일 회원 정보 수정에 실패하였습니다."),
    CONFLICT_REQUEST_DORMENCY(409, "단일 회원 휴면계정 전환에 실패하였습니다."),
    CONFLICT_REQUEST_PRODUCT(409, "단일 상품정보 수정에 실패하였습니다."),
    CONFLICT_REQUEST_ORDER(409, "단일 주문 수정에 실패하였습니다."),
    CONFLICT_REQUEST_DELIVERY(409, "단일 배송지정보 수정에 실패하였습니다."),

    DELETE_REQUEST_DELIVERY_ERROR(409, "배송지 삭제 작업에 실패하였습니다."),

    NOT_DELETE_DEFAULT_DELIADDR(409, "기본 배송지로 설정된 배송지는 지울 수 없습니다."),

    // 503 기타 공용적인 에러
    NOT_READY_SERVICE_ERROR(503, "아직 지정되지 않은 공용적인 오류입니다. (TEST ERROR)"),

    // 500 INTERNAL SERVER ERROR
    INTERNAL_SERVER_ERROR(500, "서버 에러입니다."),
    INSERT_FAIL_USER_ERROR(500, "회원 인서트 작업에 실패하였습니다."),
    INSERT_FAIL_PRODUCT_ERROR(500, "상품 인서트 작업에 실패하였습니다."),
    INSERT_FAIL_DELIVERY_ERROR(500, "배송지 인서트 작업에 실패하였습니다."),
    INSERT_FAIL_DELIVERY_OVER_ERROR(500, "배송지는 5개까지 등록이 가능합니다."),
    SELECT_FAIL_PRODUCT_ERROR(500, "상품 테이블에 데이터가 없습니다."),
    SELECT_FAIL_ORDER_ERROR(500, "주문 인서트 작업에 실패하였습니다."),
    USER_ERROR(500, "유저 SERVICE에서 에러가 발생했습니다.")

    ;



    private final int status;
    private final String message;
}
