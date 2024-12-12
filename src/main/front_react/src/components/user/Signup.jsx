import React, { useEffect, useState } from "react";
import { useNavigate } from 'react-router-dom';
import axios from "axios";

function Signup() {
    const navigate = useNavigate();

    // 사용할 상태변수 초기화.
    const [id, setId] = useState("");
    const [pw, setPw] = useState("");
    const [confirmPw, setConfirmPw] = useState("");
    const [name, setName] = useState("");
    const [addr, setAddr] = useState("");

    // 사용할 메세지 상태변수 초기화.
    const [idMsg, setIdMsg] = useState("");
    const [pwMsg, setPwMsg] = useState("");
    const [confirmMsg, setConfirmMsg] = useState("");
    const [nameMsg, setNameMsg] = useState("");
    const [addrMsg, setAddrMsg] = useState("");

    // 유효성 상태변수 초기화.
    const [isId, setIsId] = useState(false);
    const [isPw, setIsPw] = useState(false);
    const [isConfirm, setIsConfirm] = useState(false);
    const [isName, setIsName] = useState(false);
    const [isAddr, setIsAddr] = useState(false);


    const blurIdHandler = (event) => {
        const currentId = event.currentTarget.value;

        if (currentId !== '' && currentId !== null) {
            // 아이디 중복확인 true = 중복되지 않음, false = 중복됨.
            axios.get(`http://localhost:8080/api/user/${id}`)
                .then(res => {
                    if (res.data.data !== null) {
                        setIdMsg("이미 사용중인 아이디 입니다. 새롭게 다시 입력해 주세요.");
                        setIsId(false);
                    }
                })
        }
    }


    const onIdHandler = (event) => {
        const currentId = event.currentTarget.value;

        setId(currentId);

        const idRegExp = /^[a-zA-Z0-9]{4,12}$/;

        if (idRegExp.test(currentId)) {
            setIdMsg("사용가능한 아이디 입니다.");
            setIsId(true);
        } else {
            setIdMsg("4~12자리 대소문자 또는 숫자만 입력할 수 있습니다.");
            setIsId(false);
        }
    }
    const onPwHandler = (event) => {
        const currentPw = event.currentTarget.value;

        setPw(currentPw);

        const pwRegExp = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,25}$/;

        if (pwRegExp.test(currentPw)) {
            setPwMsg("사용가능한 비밀번호 입니다.");
            setIsPw(true);
        } else {
            setPwMsg("숫자+영문자+특수문자 조합으로 8자리 이상 입력해주세요!");
            setIsPw(false);
        }
    }
    const onConfirmPwHandler = (event) => {
        const currentConfirmPw = event.currentTarget.value;

        setConfirmPw(currentConfirmPw);

        if (isPw && pw === currentConfirmPw) {
            setConfirmMsg("비밀번호와 동일합니다.")
            setIsConfirm(true);
        } else {
            setConfirmMsg("비밀번호와 일치하지 않습니다. 재입력 해주세요.");
            setIsConfirm(false);
        }
    }
    const onNameHandler = (event) => {
        const currentName = event.currentTarget.value;

        setName(currentName);

        if (currentName.length > 2 && currentName.length < 6) {
            setNameMsg(`${currentName} 님, 안녕하세요.`);
            setIsName(true);
        } else {
            setNameMsg("이름은 2글자 이상, 5글자 이하여야 합니다. 제 맘 입니다.");
            setIsName(false);
        }
    }
    const onAddrHandler = (event) => {
        const currentAddr = event.currentTarget.value;

        setAddr(currentAddr);

        if (currentAddr.length > 3) {
            setAddrMsg("일단은 뭔가를 적긴 했군요. 합격입니다.");
            setIsAddr(true);
        } else {
            setAddrMsg("주소가 없거나 너무 짧네요. 다시 적으세요. 당장.");
            setIsAddr(false);
        }
    }
    const onSubmitHandler = (event) => {
        event.preventDefault();

        if (isId && isPw && isConfirm && isName && isAddr) {
            let reqData = {
                id: id,
                pw: pw,
                name: name,
                addr: addr
            }

            axios.post('http://localhost:8080/api/user/signup', reqData)
                .then(res => {
                    if (res.data.statCode === 'success') {
                        console.log("if inner : " + res);
                        navigate('/');
                    }
                    console.log(res);
                })
                .catch(err => {
                    console.log('Error fetching data:', err);
                })

        } else {
            alert("회원가입 양식이 무언가 잘못되었습니다. 확인해주세요.");
        }
    }

    return (
        <form onSubmit={onSubmitHandler}>
            <label htmlFor={'id'}>아이디 </label>
            <input onChange={onIdHandler}
                   onBlur={blurIdHandler}
                   type={'text'}
                   id={'id'}
                   name={'id'}
                   value={id}
                   placeholder={'아이디 입력'}
                   maxLength={15}
            />
            <br/>
            {idMsg && <small style={{color: isId ? "green" : "red"}}>{idMsg}</small>}<br/>
            <p/>

            <label htmlFor={'pw'}>비밀번호 </label>
            <input onChange={onPwHandler}
                   type={'password'}
                   id={'pw'}
                   name={'pw'}
                   value={pw}
                   placeholder={'비밀번호 입력'}
                   maxLength={24}
            />
            <br/>
            {pwMsg && <small style={{color: isPw ? "green" : "red"}}>{pwMsg}</small>}<br/>
            <p/>

            <label htmlFor={'confirmPw'}>비밀번호 확인 </label>
            <input onChange={onConfirmPwHandler}
                   type={'password'}
                   id={'confirmPw'}
                   name={'confirmPw'}
                   value={confirmPw}
                   placeholder={'비밀번호 재입력'}
                   maxLength={24}
            />
            <br/>
            {confirmMsg && <small style={{color: isConfirm ? "green" : "red"}}>{confirmMsg}</small>}<br/>
            <p/>

            <label htmlFor={'name'}>이름 </label>
            <input onChange={onNameHandler}
                   type={'name'}
                   id={'name'}
                   name={'name'}
                   value={name}
                   placeholder={'이름 입력'}
                   maxLength={6}
            />
            <br/>
            {nameMsg && <small style={{color: isName ? "green" : "red"}}>{nameMsg}</small>}<br/>
            <p/>

            <label htmlFor={'addr'}>주소 </label>
            <input onChange={onAddrHandler}
                   type={'text'}
                   id={'addr'}
                   name={'addr'}
                   value={addr}
                   placeholder={'주소를 적어주세요.'}
            />
            <br/>
            {addrMsg && <small style={{color: isAddr ? "green" : "red"}}>{addrMsg}</small>}<br/>
            <p/>

            <button onClick={onSubmitHandler} disabled= {!(isId && isPw && isConfirm && isName)}>가입하기</button>
        </form>
    )
}

export default Signup;