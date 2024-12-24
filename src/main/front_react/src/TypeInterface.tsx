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

