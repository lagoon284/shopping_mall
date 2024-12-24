import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

export default function Login() {
    const navigate = useNavigate();

    const [userId, setUserId] = useState<string>("");
    const [pw, setPw] = useState<string>("");
    const [loginMsg, setLoginMsg] = useState<string>("");

    const [isUserId, setIsUserId] = useState<boolean>(false);
    const [isPw, setIsPw] = useState<boolean>(false);

    const onIdHandler = (event: React.ChangeEvent<HTMLInputElement>) => {
        const currentUserId : string = event.currentTarget.value;

        setUserId(currentUserId);
        setIsUserId(true);
    }

    const onPwHandler = (event: React.ChangeEvent<HTMLInputElement>) => {
        const currentPw : string = event.currentTarget.value;

        setPw(currentPw);
        setIsPw(true);
    }

    const onSubmitHandler = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();

        const loginData = {
            userId: userId,
            pw: pw
        };

        axios.put('http://localhost:8080/api/auth/login', loginData)
            .then(res => {
                if (res.data.data) {
                    localStorage.setItem('id', res.data.data.userId);
                    localStorage.setItem('seokho_jwt', res.data.data.accessToken);
                    localStorage.setItem('seokho_ref_jwt', res.data.data.refreshToken);
                    navigate('/');
                }
            })
            .catch(err => {
                console.log('Error fetching data:', err);
                setLoginMsg('로그인에 실패하였습니다. 아이디와 비밀번호를 확인해 주세요.');
                // alert(err);
            })
    }


    return (
        <div className={'Signup'}>
            <form onSubmit={onSubmitHandler}>
                <label htmlFor={'userId'}>아이디 </label>
                <input onChange={onIdHandler}
                       type={'text'}
                       id={'userId'}
                       name={'userId'}
                       value={userId}
                       placeholder={'아이디 입력'}
                       maxLength={15}
                /><br />
                <label htmlFor={'pw'}>비밀번호 </label>
                <input onChange={onPwHandler}
                       type={'password'}
                       id={'pw'}
                       name={'pw'}
                       value={pw}
                       placeholder={'비밀번호 입력'}
                       maxLength={24}
                /><br />
                {loginMsg && <small style={{color: "red"}}>{loginMsg}</small>}<br/>
                <p/>
                <button type={'submit'} disabled={!(isUserId && isPw)}>로그인</button>
            </form>
        </div>
    )
}
