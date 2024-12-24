import React, { useState } from "react";
import { useNavigate } from 'react-router-dom';
import axios from "axios";

function Signup() {
    const navigate = useNavigate();

    // 로딩 상태 관리
    const [ loading, setLoading ] = useState<boolean>();

    // 사용할 상태변수 초기화.
    const [id, setId] = useState<string>("");
    const [pw, setPw] = useState<string>("");
    const [confirmPw, setConfirmPw] = useState<string>("");
    const [name, setName] = useState<string>("");
    const [addr, setAddr] = useState<string>("");

    // 사용할 메세지 상태변수 초기화.
    const [idMsg, setIdMsg] = useState<string>("");
    const [pwMsg, setPwMsg] = useState<string>("");
    const [confirmMsg, setConfirmMsg] = useState<string>("");
    const [nameMsg, setNameMsg] = useState<string>("");
    const [addrMsg, setAddrMsg] = useState<string>("");

    // 유효성 상태변수 초기화.
    const [isId, setIsId] = useState<boolean>(false);
    const [isPw, setIsPw] = useState<boolean>(false);
    const [isConfirm, setIsConfirm] = useState<boolean>(false);
    const [isName, setIsName] = useState<boolean>(false);
    const [isAddr, setIsAddr] = useState<boolean>(false);


    const blurIdHandler = (event: React.FocusEvent<HTMLInputElement>) => {
        const currentId : string = event.currentTarget.value;

        if (currentId !== '' && currentId !== null) {
            // 아이디 중복확인 true = 중복되지 않음, false = 중복됨.
            const fetchIdCheck = async () => {
                await axios.get(`http://localhost:8080/api/user/${id}`)
                    .then(res => {
                        if (res.data.data !== null) {
                            setIdMsg("이미 사용중인 아이디 입니다. 새롭게 다시 입력해 주세요.");
                            setIsId(false);
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

        setId(currentId);

        const idRegExp : RegExp = /^[a-zA-Z0-9]{4,12}$/;

        if(currentId !== "") {
            if (idRegExp.test(currentId)) {
                setIdMsg("사용가능한 아이디 입니다.");
                setIsId(true);
            } else {
                setIdMsg("4~12자리 대소문자 또는 숫자만 입력할 수 있습니다.");
                setIsId(false);
            }
        } else {
            setIdMsg("");
        }

    }
    const onPwHandler = (event: React.ChangeEvent<HTMLInputElement>) => {
        const currentPw : string = event.currentTarget.value;

        setPw(currentPw);

        const pwRegExp : RegExp = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,25}$/;

        if(currentPw !== "") {
            if (pwRegExp.test(currentPw)) {
                setPwMsg("사용가능한 비밀번호 입니다.");
                setIsPw(true);
            } else {
                setPwMsg("숫자+영문자+특수문자 조합으로 8자리 이상 입력해주세요!");
                setIsPw(false);
            }
        } else { setPwMsg(""); }
    }
    const onConfirmPwHandler = (event: React.ChangeEvent<HTMLInputElement>) => {
        const currentConfirmPw : string = event.currentTarget.value;

        setConfirmPw(currentConfirmPw);

        if (currentConfirmPw !== "") {
            if (isPw && pw === currentConfirmPw) {
                setConfirmMsg("비밀번호와 동일합니다.")
                setIsConfirm(true);
            } else {
                setConfirmMsg("비밀번호와 일치하지 않습니다. 재입력 해주세요.");
                setIsConfirm(false);
            }
        } else { setConfirmMsg(""); }

    }
    const onNameHandler = (event: React.ChangeEvent<HTMLInputElement>) => {
        const currentName : string = event.currentTarget.value;

        setName(currentName);

        if (currentName !== "") {
            if (currentName.length > 2 && currentName.length < 6) {
                setNameMsg(`${currentName} 님, 안녕하세요.`);
                setIsName(true);
            } else {
                setNameMsg("이름은 2글자 이상, 5글자 이하여야 합니다. 제 맘 입니다.");
                setIsName(false);
            }
        } else { setNameMsg(""); }
    }
    const onAddrHandler = (event: React.ChangeEvent<HTMLInputElement>) => {
        const currentAddr : string = event.currentTarget.value;

        setAddr(currentAddr);

        if(currentAddr !== "") {
            if (currentAddr.length > 3) {
                setAddrMsg("일단은 뭔가를 적긴 했군요. 합격입니다.");
                setIsAddr(true);
            } else {
                setAddrMsg("주소가 없거나 너무 짧네요. 다시 적으세요. 당장.");
                setIsAddr(false);
            }
        } else { setAddrMsg(""); }
    }
    const onSubmitHandler = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();

        if (isId && isPw && isConfirm && isName && isAddr) {
            let reqData = {
                id: id,
                pw: pw,
                name: name,
                addr: addr
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
                   value={id}
                   placeholder={'아이디 입력'}
                   maxLength={15}
            />
            <br/>
            {/* idMsg 값이 있어야만(true)(falsy한 값이라면 태그는 렌더링 되지 않음.) && 를 기준으로 오른쪽 값을 반환함. */}
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

            <button type={'submit'} disabled= {!(isId && isPw && isConfirm && isName)}>가입하기</button>
        </form>
    )
}

export default Signup;