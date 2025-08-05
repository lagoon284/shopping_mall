import React, { useState } from "react";
import { useNavigate } from 'react-router-dom';
import ApiClient from "../util/ApiClient";

import { CommonType } from "../../interfaces/CommonInterface";
import {FormDataRegExpType, FormDataType} from "../../interfaces/UserInterface";

export default function Signup() {
    const navigate = useNavigate();
    // 로딩 상태 관리
    const [ commonStat, setCommonStat ] = useState<CommonType>({
        loading: true, error: ""
    });
    const [ formData, setFormData ] = useState<FormDataType>({
        // 사용할 상태변수 초기화.
        id: "", pw: "", confirmPw: "", name: "", addr: "",
        // 사용할 메세지 상태변수 초기화.
        idMsg: "", pwMsg: "", confirmMsg: "", nameMsg: "", addrMsg: "",
        // 유효성 상태변수 초기화.
        isId: false, isPw: false, isConfirm: false, isName: false, isAddr: false
    });

    const blurIdHandler = (event: React.FocusEvent<HTMLInputElement>) => {
        const currentId : string = event.currentTarget.value;

        if (currentId !== '' && currentId !== null) {
            // 아이디 중복확인 true = 중복되지 않음, false = 중복됨.
            const fetchIdCheck = async () => {
                await ApiClient.get(`/user/${currentId}`)
                    .then(res => {
                        if (res.data.data !== null) {
                            setFormData(prvData => ({
                                ...prvData,
                                idMsg : "이미 사용중인 아이디 입니다. 새롭게 다시 입력해 주세요.",
                                isId : false
                            }));
                        }
                    })
                    .catch(error => {
                        // 아이디 중복 확인 로직 -> exception 발생시 중복없는 것.
                        console.log('Error fetching data:', error);
                        setFormData(prvData => ({
                            ...prvData,
                            idMsg: "사용가능한 아이디 입니다.",
                            isId: true
                        }));
                    })
            }
            fetchIdCheck();
        }
    }

    const onChangeEventHandler = (event : React.ChangeEvent<HTMLInputElement>, strVal : string) => {
        const currentVal : string = event.currentTarget.value;

        const regExp : FormDataRegExpType = {
            id : /^[a-zA-Z0-9]{4,12}$/,
            pw : /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,25}$/
        }

        // updateFormData 매개변수 초기화.
        let key : string = "";
        let value : string = "";
        let msgKey : string = "";
        let msg : string = "";
        let flagKey : string = "";
        let flag : boolean = false;

        // 케이스별 updateFormData 매개변수 세팅.
        switch (strVal) {
            case 'id':
                key = "id";
                value = currentVal;
                msgKey = "idMsg";
                flagKey = "isId";

                break;

            case 'pw':
                key = "pw";
                value = currentVal;
                msgKey = "pwMsg";
                flagKey = "isPw";

                if (currentVal !== "") {
                    if (regExp.pw.test(currentVal)) {
                        msg = "사용가능한 비밀번호 입니다.";
                        flag = true;
                    } else {
                        msg = "숫자+영문자+특수문자 조합으로 8자리 이상 입력해주세요!";
                        flag = false;
                    }
                } else { flag = false; }

                break;

            case 'confirmPw':
                key = "confirmPw";
                value = currentVal;
                msgKey = "confirmMsg";
                flagKey = "isConfirm";

                if (currentVal !== "") {
                    if (formData.isPw && formData.pw === currentVal) {
                        msg = "비밀번호와 동일합니다.";
                        flag = true;
                    } else {
                        msg = "비밀번호와 일치하지 않습니다. 재입력 해주세요.";
                        flag = false;
                    }
                } else { flag = false; }

                break;

            case 'name':
                key = "name";
                value = currentVal;
                msgKey = "nameMsg";
                flagKey = "isName";

                if (currentVal !== "") {
                    if (currentVal.length > 2 && currentVal.length < 6) {
                        msg = `${currentVal} 님, 안녕하세요.`;
                        flag = true;
                    } else {
                        msg = "이름은 2글자 이상, 5글자 이하여야 합니다. 제 맘 입니다.";
                        flag = false;
                    }
                } else { flag = false; }

                break;

            case 'addr':
                key = "addr";
                value = currentVal;
                msgKey = "addrMsg";
                flagKey = "isAddr";

                if (currentVal !== "") {
                    if (currentVal.length > 3) {
                        msg = "일단은 뭔가를 적긴 했군요. 합격입니다.";
                        flag = true;
                    } else {
                        msg = "주소가 없거나 너무 짧네요. 다시 적으세요. 당장.";
                        flag = false;
                    }
                } else { flag = false; }

                break;
        }

        updateFormData(key, value, msgKey, msg, flagKey, flag);
    }

    const updateFormData = (key : string, value : string, msgKey : string, msg : string, flagKey : string, flag : boolean) => {
        setFormData(prvData => ({
            ...prvData,
            [key] : value,
            [msgKey] : msg,
            [flagKey] : flag
        }))
    }

    const onSubmitHandler = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();

        if (formData.isId && formData.isPw && formData.isConfirm && formData.isName && formData.isAddr) {
            let reqData = {
                id: formData.id,
                pw: formData.pw,
                name: formData.name,
                addr: formData.addr
            }

            const fetchSignup = async () => {
                await ApiClient.post('/user/signup', reqData)
                    .then(res => {
                        if (res.data.statCode === 'success') {
                            alert("회원가입이 완료되었습니다. 로그인화면으로 이동합니다.");
                            navigate('/login');
                        }
                    })
                    .catch(err => {
                        console.log('Error fetching data:', err);
                        setCommonStat(prev => ({
                            ...prev,
                            error: "회원가입에 실패하였습니다. : " + err
                        }))
                    })
            }
            fetchSignup();
        } else {
            alert("회원가입 양식이 잘못되었습니다. 확인해주세요.");
        }
    }

    if (commonStat.error) {
        return (
            <>
                <h2 className={"section-title"}>회원정보 입력</h2>
                <div className={"divider"} />
                <h3>{commonStat.error}</h3>
            </>
        );
    }

    return (
        <>
            <h2 className={"section-title"}>회원정보 입력</h2>
            <div className={"divider"}/>
            <form onSubmit={(event) => onSubmitHandler(event)}>
                <div className={"form-group"}>
                    <label htmlFor={'id'}>아이디 (ID)</label>
                    <br/>
                    <input className="form-control"
                           onChange={(event) => onChangeEventHandler(event, 'id')}
                           onBlur={(event) => blurIdHandler(event)}
                           type={'text'}
                           id={'id'}
                           name={'id'}
                           value={formData.id}
                           placeholder={'아이디 입력'}
                           maxLength={15}
                           autoComplete={"username"}
                    />
                    <br/>
                    {/* idMsg 값이 있어야만(true)(falsy한 값이라면 태그는 렌더링 되지 않음.) && 를 기준으로 오른쪽 값을 반환함. */}
                    {formData.idMsg &&
                        <small style={{color: formData.isId ? "green" : "red"}}>{formData.idMsg}</small>}<br/>
                    <p/>

                    <label htmlFor={'pw'}>비밀번호 (PASSWORD)</label>
                    <br/>
                    <input className="form-control"
                           onChange={(event) => onChangeEventHandler(event, 'pw')}
                           type={'password'}
                           id={'pw'}
                           name={'pw'}
                           value={formData.pw}
                           placeholder={'비밀번호 입력'}
                           maxLength={24}
                           autoComplete={"new-password"}
                    />
                    <br/>
                    {formData.pwMsg &&
                        <small style={{color: formData.isPw ? "green" : "red"}}>{formData.pwMsg}</small>}<br/>
                    <p/>

                    <label htmlFor={'confirmPw'}>비밀번호 확인</label>
                    <br/>
                    <input className="form-control"
                           onChange={(event) => onChangeEventHandler(event, 'confirmPw')}
                           type={'password'}
                           id={'confirmPw'}
                           name={'confirmPw'}
                           value={formData.confirmPw}
                           placeholder={'비밀번호 재입력'}
                           maxLength={24}
                           autoComplete={"new-password"}
                    />
                    <br/>
                    {formData.confirmMsg &&
                        <small style={{color: formData.isConfirm ? "green" : "red"}}>{formData.confirmMsg}</small>}<br/>
                    <p/>

                    <label htmlFor={'name'}>이름</label>
                    <br/>
                    <input className="form-control"
                           onChange={(event) => onChangeEventHandler(event, 'name')}
                           type={'name'}
                           id={'name'}
                           name={'name'}
                           value={formData.name}
                           placeholder={'이름 입력'}
                           maxLength={6}
                           autoComplete={"username"}
                    />
                    <br/>
                    {formData.nameMsg &&
                        <small style={{color: formData.isName ? "green" : "red"}}>{formData.nameMsg}</small>}<br/>
                    <p/>

                    <label htmlFor={'addr'}>주소</label>
                    <br/>
                    <input className="form-control"
                           onChange={(event) => onChangeEventHandler(event, 'addr')}
                           type={'text'}
                           id={'addr'}
                           name={'addr'}
                           value={formData.addr}
                           placeholder={'주소를 적어주세요.'}
                    />
                    <br/>
                    {formData.addrMsg &&
                        <small style={{color: formData.isAddr ? "green" : "red"}}>{formData.addrMsg}</small>}<br/>
                    <p/>

                    <div style={{textAlign: 'right'}}>
                        <button className="btn btn-primary"
                                type={'submit'}
                                disabled={!(formData.isId && formData.isPw && formData.isConfirm && formData.isName && formData.isAddr)}>가입하기
                        </button>
                    </div>
                </div>
            </form>
        </>
)
}