export interface ProdDetailType {
    prodSeqNo : number
    prodName : string
    price : number
    provider : string
    info : string
    useFlag : boolean
}

export interface ProdInsFormDataType {
    prodName : string
    price : number
    provider : string
    info : string
    useFlag : boolean

    prodNameMsg : string
    priceMsg : string
    providerMsg : string
    infoMsg : string
    useFlagMsg : string

    isProdName : boolean
    isPrice : boolean
    isProvider : boolean
    isInfo : boolean
}