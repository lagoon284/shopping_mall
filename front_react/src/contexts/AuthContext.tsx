import React, { createContext, useState, useEffect, useContext } from "react";
import ApiClient, { setupAxiosInterceptors } from "../components/util/ApiClient";
import { jwtDecode } from "jwt-decode";

import { DecodedToken, AuthContextType, AuthProviderProps, AuthToken, LoginData } from "../interfaces/AuthInterface";

// Context 생성
const AuthContext = createContext<AuthContextType | undefined>(undefined);

// AuthProvider 컴포넌트 구현
export function AuthProvider({ children }: AuthProviderProps ) {
    const [ user, setUser] = useState<AuthToken | null>(null);
    const [ token, setToken] = useState<string | null>(() => localStorage.getItem('seokho_jwt'));

    useEffect(() => {
        const handlerNewToken = (newToken: string) => {
            localStorage.setItem('seokho_jwt', newToken);
            setToken(newToken);
        }

        setupAxiosInterceptors(handlerNewToken);
    }, []);

    // 앱이 시작되거나 새로고침 될 때 localStorage의 토큰으로 유저정보 세팅.
    useEffect(() => {
        if (token) {
            try {
                const decodedToken = jwtDecode<DecodedToken>(token);
                setUser({ id: decodedToken.sub, role: decodedToken.role });
                ApiClient.defaults.headers.common['Authorization'] = `seokhoAccAuth ${token}`;
            } catch (error) {
                // 토큰이 유효하지 않은 형식일 경우 디코딩 에러 발생
                console.error('유효하지 않은 토큰입니다.', error);
                logout(); // 잘못된 토큰이므로 로그아웃 처리
            }
        }
    }, [token]);

    const login = async (loginData: LoginData) => {
        try {
            const res = await ApiClient.put<string>('/auth/login', loginData);
            const accessToken = res.data;

            const decodedToken = jwtDecode<DecodedToken>(accessToken);

            localStorage.setItem('id', decodedToken.sub);
            localStorage.setItem('seokho_jwt', accessToken);
            ApiClient.defaults.headers.common['Authorization'] = `seokhoAccAuth ${accessToken}`;

            setToken(accessToken);
            setUser({ id: decodedToken.sub, role: decodedToken.role });
        } catch (err) {
            console.log('로그인 실패', err);
            // 실패 시 에러를 컴포넌트에게 던짐. 사용하는 컴포넌트에서 처리유도.
            throw err;
        }
    };

    const logout = () => {
        localStorage.removeItem('id');
        localStorage.removeItem('seokho_jwt');
        delete ApiClient.defaults.headers.common['Authorization'];
        setToken(null);
        setUser(null);
    };

    const value: AuthContextType = {
        user,
        token,
        // 로그인 했을 떄 ture 비로그인 때 false.
        isLoggedIn: !!user,
        login,
        logout
    };

    return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

export function useAuth() {
    const context = useContext(AuthContext);
    if (!context) {
        throw new Error('useAuth must be used within an AuthProvider');
    }
    return context;
}