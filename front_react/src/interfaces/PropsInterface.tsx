import {LoginDetailType} from "./UserInterface";
import React from "react";

/*
* props 의 type 을 정해줌
* */
export interface PropsType {
    propLoginInfo : LoginDetailType;
    // 로그아웃 시 프롭스를 제어하기 위해 set 함수를 넣어줌.
    setLoginInfo: React.Dispatch<React.SetStateAction<LoginDetailType | null>>;
    // logoutClick: (event: React.MouseEvent<HTMLAnchorElement>) => void;
}

