import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

import {LoginStateType} from "../../interfaces/LoginInterface";

export default function Login() {
    const navigate = useNavigate();

    const [ state, setState ] = useState<LoginStateType>({
        userId: "",
        pw: "",
        loginMsg: "",

        isUserId: false,
        isPw: false
    });

    const onIdHandler = (event: React.ChangeEvent<HTMLInputElement>) => {
        const currentUserId : string = event.currentTarget.value;

        setState(prev => ({
            ...prev,
            userId: currentUserId,
            isUserId: true
        }));
    }

    const onPwHandler = (event: React.ChangeEvent<HTMLInputElement>) => {
        const currentPw : string = event.currentTarget.value;

        setState(prev => ({
            ...prev,
            pw: currentPw,
            isPw: true
        }));
    }

    const onSubmitHandler = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();

        const loginData = {
            userId: state.userId,
            pw: state.pw
        };

        axios.put('http://localhost:8080/api/auth/login', loginData)
            .then(res => {
                if (res.data.data) {
                    localStorage.setItem('id', res.data.data.userId);
                    localStorage.setItem('seokho_jwt', res.data.data.accessToken);
                    navigate('/');
                }
            })
            .catch(err => {
                console.log('Error fetching data:', err);

                setState(prev => ({
                    ...prev,
                    loginMsg: '로그인에 실패하였습니다. 아이디와 비밀번호를 확인해 주세요.'
                }));
            })
    }


    return (
        <>
            <h2 className={"section-title"}>로그인을 진행해주세요.</h2>
            <div className={"divider"}/>
            <form onSubmit={onSubmitHandler}>
                <div className={"form-group"}>
                    <label htmlFor={'userId'}>아이디 (ID)</label><br/>
                    <input className="form-control"
                           onChange={onIdHandler}
                           type={'text'}
                           id={'userId'}
                           name={'userId'}
                           value={state.userId}
                           placeholder={'아이디 입력'}
                           maxLength={15}
                           autoComplete={"username"}
                    /><br/>
                    <p/>
                    <label htmlFor={'pw'}>비밀번호 (PASSWORD)</label><br/>
                    <input className="form-control"
                           onChange={onPwHandler}
                           type={'password'}
                           id={'pw'}
                           name={'pw'}
                           value={state.pw}
                           placeholder={'비밀번호 입력'}
                           maxLength={24}
                           autoComplete={"current-password"}
                    /><br/>
                    {state.loginMsg && <small style={{color: "red"}}>{state.loginMsg}</small>}<br/>
                    <p/>
                    <button className="btn btn-primary" type={'submit'} disabled={!(state.isUserId && state.isPw)}>로그인</button>
                </div>
            </form>
        </>
    )
}
