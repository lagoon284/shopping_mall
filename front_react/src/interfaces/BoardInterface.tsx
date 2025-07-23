export interface BoardInsFormDataType {
    title : string
    content : string
    writer : string
    writerId : string

    // 입력 안내 메세지
    titleMsg : string
    contentMsg : string
    writerMsg : string

    // 필수 입력 확인
    isTitle : boolean
    isContent : boolean
    isWriter : boolean

    // 공지글 적용 유무 (관리자 영역)
    isNotice : boolean
    // 비밀글 적용 유무 (회원 영역)
    isSecret : boolean
    // 임시저장 유무
    isDraft : boolean

    status : number
    parentId : number
    depth : number

    // 첨부파일
    attachments : string[]
    // 이미지
    images : string[]
}

export interface BoardListType {
    seqNo : string
    title : string
    // content : string
    writer : string
    // writerId : string

    // isNotice : boolean
    // isSecret : boolean
    // isDraft : boolean

    // status : number
    parentId : number
    depth : number

    viewCnt : number
    likeCnt : number
    hateCnt : number
    reportCnt : number
    commentCnt : number

    regDate : string
    updDate : string
}

export interface BoardDetailType {
    seqNo : string
    title : string
    content : string
    writer : string
    writerId : string

    isNotice : boolean
    isSecret : boolean
    isDraft : boolean

    status : number
    parentId : number
    depth : number

    viewCnt : number
    likeCnt : number
    commentCnt : number

    regDate : string
    updDate : string
}