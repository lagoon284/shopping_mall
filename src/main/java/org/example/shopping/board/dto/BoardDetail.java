package org.example.shopping.board.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.example.shopping.util.common.dto.RetAttributes;

import java.awt.*;
import java.io.File;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class BoardDetail extends RetAttributes {

    // 게시글 번호. (PK)
    private int seqNo;

    // 게시글 제목.
    private String title;

    // 게시글 내용.
    private String content;

    // 게시글 작성자.
    private String writer;

    // 작성자 아이디
    private String writerId;

    /* ---------------------------------------------------------------- */
    // 상태 및 분류
    // boolean 값 변수명에 is를 추가하면 나중에 bean 규약 때문에 is가 사라니는 경험을 함.
    // getter, setter 메소드를 거치면서 is는 자동으로 boolean 타입의 구분을 위한거라 판당해서 is를 없엠.
    // 이 현상을 방지하기 위해 @JsonProperty 어노테이션을 사용하여 방지함.

    // 공지글 여부
    @JsonProperty("isNotice")
    private boolean isNotice;

    // 비밀글 여부
    @JsonProperty("isSecret")
    private boolean isSecret;

    // 임시저장 여부
    @JsonProperty("isDraft")
    private boolean isDraft;

    // 게시글 상태   -   타입을 int로 해놓아서 상태별 번호의 규칙을 만들어야함.
    private int status;

    // 게시글 태그   (리스트 타입 주의)
    private List<String> tags;

    // 부모글/답글 번호
    private int parentId;

    // 답글 깊이
    private int depth;

    // 게시글 만료시점 (일/시)
    private String expireAt;

    // 에디터 타입
    private String editType;

    /* ---------------------------------------------------------------- */
    // 조회 및 통계

    // 조회수
    private int viewCnt;

    // 추천수 or 좋아요
    private int likeCnt;

    // 비추천 or 싫어요
    private int hateCnt;

    // 신고 횟수
    private int reportCnt;

    // 댓글 수
    private int commentCnt;

    /* ---------------------------------------------------------------- */
    // 첨부 및 연관 정보

    // 첨부파일 목록
    private List<File> attachments;

    // 이미지 목록
    private List<Image> images;

    /* ---------------------------------------------------------------- */



    /* 원본 항목 참조 */

    /*// 게시글 번호. (PK)
    private int seqNo;

    // 게시글 제목.
    private String title;

    // 게시글 부제목.
    // private String sub_title;

    // 게시글 내용.
    private String content;

    // 게시글 작성자.
    private String writer;

    // 작성자 아이디
    private String writerId;

    // 작성자 닉네임
    // private String writerNickName;

    // 작성자 이름
    // private String writerName;

    // 작성자 프로필 이미지
    // private String writerProfileImage;

    // 작성자 IP
    // private String writerIP;

    // 작성자 권한 등급
    // private String writerRole;

    *//* ---------------------------------------------------------------- *//*
    // 상태 및 분류

    // 공지글 여부
    private boolean isNotice;

    // 비밀글 여부
    private boolean isSecret;

    // 임시저장 여부
    private boolean isDraft;

    // 게시글 상태   -   타입을 int로 해놓아서 상태별 번호의 규칙을 만들어야함.
    private int status;

    // 게시글 태그   (리스트 타입 주의)
    private List<String> tags;

    // 부모글/답글 번호
    private int parentId;

    // 답글 깊이
    private int depth;

    // 게시글 만료시점 (일/시)
    private String expireAt;

    // 에디터 타입
    private String editType;

    *//* ---------------------------------------------------------------- *//*
    // 조회 및 통계

    // 조회수
    private int viewCnt;

    // 추천수 or 좋아요
    private int likeCnt;

    // 비추천 or 싫어요
    private int hateCnt;

    // 신고 횟수
    private int reportCnt;

    // 댓글 수
    private int commentCnt;

    // 첨부파일 수
    // private int attachmentCnt;

    // 스크랩/북마크 수
    // private int bookmarkCnt;

    // 게시글 우선순위
    // private int priority;

    *//* ---------------------------------------------------------------- *//*
    // 첨부 및 연관 정보

    // 첨부파일 목록
    private List<File> attachments;

    // 이미지 목록
    private List<Image> images;

    // 동영상 링크
    private String videoUrl;

    // 외부링크
    private String externalLink;

    *//* ---------------------------------------------------------------- *//*
    // 기타

    // 추가 할 수 있는 항목. String 형식
    private String strExtraFields;
    private String strExtraFields2;
    private String strExtraFields3;

    // 추가 할 수 있는 항목. int 형식
    private int intExtraFields;
    private int intExtraFields2;
    private int intExtraFields3;

    // 추가 할 수 있는 항목. boolean 형식
    private boolean isExtraFields;
    private boolean isExtraFields2;
    private boolean isExtraFields3;

    // 추가 할 수 있는 항목. Map 형식
    private Map<String, Object> mapExtraField;
    private Map<String, Object> mapExtraField2;
    private Map<String, Object> mapExtraField3;*/
}
