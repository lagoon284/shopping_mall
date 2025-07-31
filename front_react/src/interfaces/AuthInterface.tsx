import {ReactNode} from "react";

export interface AuthToken {
    id: string | null | undefined
    role: string | null | undefined
}

export interface LoginData {
    id: string
    pw: string
}

export interface DecodedToken {
    sub: string
    role: string
    // exp: number      토큰 만료 처리는 자바에서 진행함으로 사용하지 않음.
}

export interface AuthContextType {
    user: AuthToken | null;
    token: string | null;
    isLoggedIn: boolean;
    login: (loginData: LoginData) => Promise<void>; // 로그인 함수
    logout: () => void; // 로그아웃 함수
}

export interface AuthProviderProps {
    children: ReactNode; // React 컴포넌트는 ReactNode 타입이야.
}