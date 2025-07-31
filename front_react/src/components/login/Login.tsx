import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../../contexts/AuthContext";

import {LoginStateType} from "../../interfaces/LoginInterface";
import {LoginData} from "../../interfaces/AuthInterface";
import axios from "axios";

export default function Login() {
    const { login } = useAuth();
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

    const onSubmitHandler = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();

        const loginData: LoginData = {
            id: state.userId,
            pw: state.pw
        };

        try {
            await login(loginData)
            navigate('/');
        } catch (err) {
            if (axios.isAxiosError(err)) {
                if(err.response) {
                    console.error('API Error:', err.response.data);
                    console.error('Status Code:', err.response.status);

                    if (err.status === 403) {
                        setState(prev => ({
                            ...prev,
                            loginMsg: 'jwt 이 만료되었습니다. 재로그인 시도해주세요.'
                        }));
                    } else {
                        setState(prev => ({
                            ...prev,
                            loginMsg: '로그인에 실패하였습니다. 아이디와 비밀번호를 확인해 주세요.'
                        }));
                    }
                } else {
                    // 요청은 보냈지만 응답을 받지 못한 경우 (네트워크 문제 등)
                    alert('서버로부터 응답이 없습니다.');
                }
            } else {
                // axios 에러가 아닌 다른 종류의 에러일 경우 (예: 렌더링 에러)
                console.error('An unexpected error occurred:', err);
                alert('예상치 못한 오류가 발생했습니다.');
            }
        }
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
