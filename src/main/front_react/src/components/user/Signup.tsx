import React, { useState } from "react";
import { useNavigate } from 'react-router-dom';
import axios from "axios";

import {FormDataType} from "../../TypeInterface";

function Signup() {
    const navigate = useNavigate();

    // 로딩 상태 관리
    const [ loading, setLoading ] = useState<boolean>();

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
                await axios.get(`http://localhost:8080/api/user/${formData.id}`)
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
                        console.log('Error fetching data:', error);
                    })
                    .finally(() => {
                        setLoading(false);
                    })
            }
            fetchIdCheck();
        }
    }

    const onIdHandler = (event: React.ChangeEvent<HTMLInputElement>) => {
        const currentId : string = event.currentTarget.value;

        const idRegExp : RegExp = /^[a-zA-Z0-9]{4,12}$/;

        let msg : string = "";
        let flag : boolean = false;

        if(currentId !== "") {
            if (idRegExp.test(currentId)) {
                msg = "사용가능한 아이디 입니다.";
                flag = true;
            } else {
                msg = "4~12자리 대소문자 또는 숫자만 입력할 수 있습니다.";
                flag = false;
            }
        } else { flag = false }

        setFormData(prvData => ({
            ...prvData,
            id : currentId,
            idMsg : msg,
            isId : flag
        }))
    }

    const onPwHandler = (event: React.ChangeEvent<HTMLInputElement>) => {
        const currentPw : string = event.currentTarget.value;

        const pwRegExp : RegExp = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,25}$/;

        let msg : string = "";
        let flag : boolean = false;

        if(currentPw !== "") {
            if (pwRegExp.test(currentPw)) {
                msg = "사용가능한 비밀번호 입니다.";
                flag = true;
            } else {
                msg = "숫자+영문자+특수문자 조합으로 8자리 이상 입력해주세요!";
                flag = false;
            }
        } else { flag = false }

        setFormData(prvData => ({
            ...prvData,
            pw : currentPw,
            pwMsg : msg,
            isPw : flag
        }))
    }

    const onConfirmPwHandler = (event: React.ChangeEvent<HTMLInputElement>) => {
        const currentConfirmPw : string = event.currentTarget.value;

        let msg : string = "";
        let flag : boolean = false;

        if (currentConfirmPw !== "") {
            if (formData.isPw && formData.pw === currentConfirmPw) {
                msg = "비밀번호와 동일합니다.";
                flag = true;
            } else {
                msg = "비밀번호와 일치하지 않습니다. 재입력 해주세요.";
                flag = false;
            }
        } else { flag = false }

        setFormData(prvData => ({
            ...prvData,
            confirmPw : currentConfirmPw,
            confirmMsg : msg,
            isConfirm : flag
        }))

    }

    const onNameHandler = (event: React.ChangeEvent<HTMLInputElement>) => {
        const currentName : string = event.currentTarget.value;

        let msg : string = "";
        let flag : boolean = false;

        if (currentName !== "") {
            if (currentName.length > 2 && currentName.length < 6) {
                msg = `${currentName} 님, 안녕하세요.`;
                flag = true;
            } else {
                msg = "이름은 2글자 이상, 5글자 이하여야 합니다. 제 맘 입니다.";
                flag = false;
            }
        } else { flag = false }

        setFormData(prvData => ({
            ...prvData,
            name : currentName,
            nameMsg : msg,
            isName : flag
        }))
    }

    const onAddrHandler = (event: React.ChangeEvent<HTMLInputElement>) => {
        const currentAddr : string = event.currentTarget.value;

        let msg : string = "";
        let flag : boolean = false;

        if(currentAddr !== "") {
            if (currentAddr.length > 3) {
                msg = "일단은 뭔가를 적긴 했군요. 합격입니다.";
                flag = true;
            } else {
                msg = "주소가 없거나 너무 짧네요. 다시 적으세요. 당장.";
                flag = false;
            }
        } else { flag = false }

        setFormData(prvData => ({
            ...prvData,
            addr : currentAddr,
            addrMsg : msg,
            isAddr : flag
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
                setLoading(true);
                await axios.post('http://localhost:8080/api/user/signup', reqData)
                    .then(res => {
                        if (res.data.statCode === 'success') {
                            // console.log("if inner : " + res.data.statCode);
                            alert("회원가입이 완료되었습니다. 메인화면으로 이동합니다.");
                            navigate('/');
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
            alert("회원가입 양식이 잘못되었습니다. 확인해주세요.");
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
        <form onSubmit={onSubmitHandler}>
            <label htmlFor={'id'}>아이디 </label>
            <input onChange={onIdHandler}
                   onBlur={blurIdHandler}
                   type={'text'}
                   id={'id'}
                   name={'id'}
                   value={formData.id}
                   placeholder={'아이디 입력'}
                   maxLength={15}
            />
            <br/>
            {/* idMsg 값이 있어야만(true)(falsy한 값이라면 태그는 렌더링 되지 않음.) && 를 기준으로 오른쪽 값을 반환함. */}
            {formData.idMsg && <small style={{color: formData.isId ? "green" : "red"}}>{formData.idMsg}</small>}<br/>
            <p/>

            <label htmlFor={'pw'}>비밀번호 </label>
            <input onChange={onPwHandler}
                   type={'password'}
                   id={'pw'}
                   name={'pw'}
                   value={formData.pw}
                   placeholder={'비밀번호 입력'}
                   maxLength={24}
            />
            <br/>
            {formData.pwMsg && <small style={{color: formData.isPw ? "green" : "red"}}>{formData.pwMsg}</small>}<br/>
            <p/>

            <label htmlFor={'confirmPw'}>비밀번호 확인 </label>
            <input onChange={onConfirmPwHandler}
                   type={'password'}
                   id={'confirmPw'}
                   name={'confirmPw'}
                   value={formData.confirmPw}
                   placeholder={'비밀번호 재입력'}
                   maxLength={24}
            />
            <br/>
            {formData.confirmMsg && <small style={{color: formData.isConfirm ? "green" : "red"}}>{formData.confirmMsg}</small>}<br/>
            <p/>

            <label htmlFor={'name'}>이름 </label>
            <input onChange={onNameHandler}
                   type={'name'}
                   id={'name'}
                   name={'name'}
                   value={formData.name}
                   placeholder={'이름 입력'}
                   maxLength={6}
            />
            <br/>
            {formData.nameMsg && <small style={{color: formData.isName ? "green" : "red"}}>{formData.nameMsg}</small>}<br/>
            <p/>

            <label htmlFor={'addr'}>주소 </label>
            <input onChange={onAddrHandler}
                   type={'text'}
                   id={'addr'}
                   name={'addr'}
                   value={formData.addr}
                   placeholder={'주소를 적어주세요.'}
            />
            <br/>
            {formData.addrMsg && <small style={{color: formData.isAddr ? "green" : "red"}}>{formData.addrMsg}</small>}<br/>
            <p/>

            <button type={'submit'} disabled= {!(formData.isId && formData.isPw && formData.isConfirm && formData.isName && formData.isAddr)}>가입하기</button>
        </form>
    )
}

export default Signup;