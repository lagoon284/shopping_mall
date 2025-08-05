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
    exp: number
}

export interface AuthContextType {
    user: AuthToken | null;
    token: string | null | undefined;
    isLoggedIn: boolean;
    login: (loginData: LoginData) => Promise<void>; // 로그인 함수
    logout: () => void; // 로그아웃 함수
}

export interface AuthProviderProps {
    children: ReactNode; // React 컴포넌트는 ReactNode 타입이야.
}