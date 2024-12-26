/*
* props 의 type 을 정해줌
* */
export interface PropsType {
    propLoginInfo : LoginInfoType
}

export interface LoginInfoType {
    userNo : number
    id : string
    name : string
}

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

export interface ProdInfoType {
    prodSeqNo : number
    prodName : string
    price : number
    provider : string
    info : string
    useFlag : boolean
}

