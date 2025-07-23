import React, { useState } from "react";
import { useNavigate } from 'react-router-dom';
import axios from "axios";

import {ProdInsFormDataType} from "../../interfaces/ProdInterface";

export default function ProductRegister() {
    const navigate = useNavigate();

    // 로딩 상태 관리
    const [ loading, setLoading ] = useState<boolean>(false);

    const [ formData, setFormData ] = useState<ProdInsFormDataType>({
        // 사용할 상태변수 초기화.
        prodName: "", price: 0, provider: "", info: "", useFlag: true,

        // 사용할 메세지 상태변수 초기화.
        prodNameMsg: "", priceMsg: "", providerMsg: "", infoMsg: "", useFlagMsg: "",

        // 유효성 상태변수 초기화.
        isProdName: false, isPrice: false, isProvider: false, isInfo: false
    });

    const blurIdHandler = (event: React.FocusEvent<HTMLInputElement>) => {
        setLoading(true);

        const currentVal : string = event.currentTarget.value;

        if (currentVal !== '' && currentVal !== null) {
            // 아이디 중복확인 true = 중복되지 않음, false = 중복됨.
            const fetchIdCheck = async () => {
                await axios.get(`http://localhost:8080/api/product/prodName/${currentVal}`)
                    .then(res => {
                        if (!Array.isArray(res.data.data) && res.data.data.length !== 0) {
                            setFormData(prvData => ({
                                ...prvData,
                                prodNameMsg : "이미 사용중인 상품이름 입니다. 새롭게 다시 입력해 주세요.",
                                isProdName : false
                            }));
                        } else {
                            setFormData(prvData => ({
                                ...prvData,
                                prodNameMsg : "사용 가능한 상품이름 입니다.",
                                isProdName : true
                            }));
                        }
                    })
                    .catch(error => {
                        console.log('Error fetching data:', error);
                    })
                    .finally(() => {
                        setLoading(false);
                    })
            }
            fetchIdCheck();
        }
    }

    const onChangeEventHandler = (event : React.ChangeEvent<HTMLInputElement> | React.ChangeEvent<HTMLTextAreaElement>, strVal : string) => {
        const currentVal : string = event.currentTarget.value;

        // updateFormData 매개변수 초기화.
        let key : string = "";
        let value : string | number | boolean = "";
        let msgKey : string = "";
        let msg : string = "";
        let flagKey : string = "";
        let flag : boolean = false;

        // 케이스별 updateFormData 매개변수 세팅.
        switch (strVal) {
            case 'prodName':
                key = "prodName";
                value = currentVal;
                msgKey = "prodNameMsg";
                flagKey = "isProdName";

                // if (currentVal !== "") {
                //     msg = "사용가능한 상품이름 입니다.";
                //     flag = true;
                // } else {
                //     flag = false;
                // }

                break;

            case 'price':
                key = "price";
                value = Number(currentVal);
                msgKey = "priceMsg";
                flagKey = "isPrice";

                if (currentVal !== "") {
                    if (value !== 0 && value > 0 && value < 1000001) {
                        msg = "등록 가능한 금액입니다.";
                        flag = true;
                    } else {
                        msg = "1원 ~ 1,000,000원까지 입력해주세요!";
                        flag = false;
                    }
                } else { flag = false; }

                break;

            case 'provider':
                key = "provider";
                value = currentVal;
                msgKey = "providerMsg";
                flagKey = "isProvider";

                if (currentVal !== "") {
                    flag = true;
                } else {
                    msg = "판매자 명을 옳바르게 입력해주세요.";
                    flag = false;
                }

                break;

            case 'info':
                key = "info";
                value = currentVal;
                msgKey = "infoMsg";
                flagKey = "isInfo";

                if (currentVal !== "") {
                    flag = true;
                } else {
                    msg = "상품 정보를 입력해 주세요.";
                    flag = false;
                }

                break;

            case 'useFlag':
                key = "useFlag";
                value = currentVal === "true";
                msgKey = "useFlagMsg";
                flag = true;

                break;
        }

        updateFormData(key, value, msgKey, msg, flagKey, flag);
    }

    const updateFormData = (key : string, value : string | number | boolean, msgKey : string, msg : string, flagKey : string, flag : boolean) => {
        setFormData(prvData => ({
            ...prvData,
            [key] : value,
            [msgKey] : msg,
            [flagKey] : flag
        }))
    }

    const onSubmitHandler = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();

        if (formData.isProdName && formData.isPrice && formData.isProvider && formData.isInfo) {
            let reqData = {
                prodName: formData.prodName,
                price: formData.price,
                provider: formData.provider,
                info: formData.info,
                useFlag: formData.useFlag
            }

            const fetchSignup = async () => {
                setLoading(true);
                await axios.post('http://localhost:8080/api/product/insert', reqData)
                    .then(res => {
                        if (res.data.statCode === 'success') {
                            // console.log("if inner : " + res.data.statCode);
                            alert("상품등록이 완료되었습니다. 상품정보 화면으로 이동합니다.");
                            navigate('/product/infoProds');
                        }
                    })
                    .catch(err => {
                        console.log('Error fetching data:', err);
                    })
                    .finally(() => {
                        setLoading(false);
                    })
            }
            fetchSignup();
        } else {
            alert("상품등록 양식이 잘못되었습니다. 확인해주세요.");
        }
    }

    if (loading) {
        return (
            <div className={'loading'}>
                <h1>로딩 중 입니다. 기다리세요. </h1>
            </div>
        )
    }

    return (
        <>
            <h2 className={"section-title"}>상품 정보 입력</h2>
            <div className={"divider"}/>
            <form onSubmit={(event) => onSubmitHandler(event)}>
                <label htmlFor={'prodName'}>상품이름</label>
                <br/>
                <input onChange={(event) => onChangeEventHandler(event, 'prodName')}
                       onBlur={(event) => blurIdHandler(event)}
                       type={'text'}
                       id={'prodName'}
                       name={'prodName'}
                       value={formData.prodName}
                       placeholder={'상품이름 입력'}
                       maxLength={30}
                />
                <br/>
                {/* idMsg 값이 있어야만(true)(false한 값이라면 태그는 렌더링 되지 않음.) && 를 기준으로 오른쪽 값을 반환함. */}
                {formData.prodNameMsg &&
                    <small style={{color: formData.isProdName ? "green" : "red"}}>{formData.prodNameMsg}</small>}<br/>
                <p/>

                <label htmlFor={'price'}>상품가격</label>
                <br/>
                <input onChange={(event) => onChangeEventHandler(event, 'price')}
                       type={'number'}
                       id={'price'}
                       name={'price'}
                       value={formData.price}
                       placeholder={'상품가격 입력'}
                />
                <br/>
                {formData.priceMsg &&
                    <small style={{color: formData.isPrice ? "green" : "red"}}>{formData.priceMsg}</small>}<br/>
                <p/>

                <label htmlFor={'provider'}>판매처 이름</label>
                <br/>
                <input onChange={(event) => onChangeEventHandler(event, 'provider')}
                       type={'text'}
                       id={'provider'}
                       name={'provider'}
                       value={formData.provider}
                       placeholder={'판매처 이름 입력'}
                       maxLength={15}
                />
                <br/>
                {formData.providerMsg &&
                    <small style={{color: formData.isProvider ? "green" : "red"}}>{formData.providerMsg}</small>}<br/>
                <p/>

                <label htmlFor={'info'}>상품 정보</label>
                <br/>
                <textarea onChange={(event) => onChangeEventHandler(event, 'info')}
                          id={'info'}
                          name={'info'}
                          value={formData.info}
                          placeholder={'상품 정보를 적어주세요.'}
                />
                <br/>
                {formData.infoMsg &&
                    <small style={{color: formData.isInfo ? "green" : "red"}}>{formData.infoMsg}</small>}<br/>
                <p/>

                <label htmlFor={'useFlag'}>노출/비노출 여부</label><br/>
                <input onChange={(event) => onChangeEventHandler(event, 'useFlag')}
                       type={'radio'}
                       id={'useFlag'}
                       value={'true'}
                       name={'useFlag'}
                       defaultChecked
                /> 노출
                <br/>
                <input onChange={(event) => onChangeEventHandler(event, 'useFlag')}
                       type={'radio'}
                       id={'useFlag'}
                       value={'false'}
                       name={'useFlag'}
                /> 비노출
                <br/>
                <p/>

                <button type={'submit'}
                        disabled={!(formData.isProdName && formData.isPrice && formData.isProvider && formData.isInfo)}>등록하기
                </button>
            </form>
        </>
    )
}