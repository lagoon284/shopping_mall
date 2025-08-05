export interface FormDataType {
    // 사용할 상태변수 초기화.
    id : string
    pw : string
    confirmPw : string
    name : string
    addr : string

    // 사용할 메세지 상태변수 초기화.
    idMsg : string
    pwMsg : string
    confirmMsg : string
    nameMsg : string
    addrMsg : string

    // 유효성 상태변수 초기화.
    isId : boolean
    isPw : boolean
    isConfirm : boolean
    isName : boolean
    isAddr : boolean
}

export interface FormDataRegExpType {
    id : RegExp
    pw : RegExp
}

export interface UserInfoType {
    userNo : number
    id : string
    pw : string
    name : string
    addr : string
    sleepFlag : boolean
}