import React, {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import ApiClient from "../util/ApiClient";

import { CommonType } from "../../interfaces/CommonInterface";
import { UserInfoType } from "../../interfaces/UserInterface";

function UserDetail() {
    const { id } = useParams<{ id: string }>();
    const [ user, setUser ] = useState<UserInfoType | null>(null);
    // common 상태 관리
    const [ commonStat, setCommonStat ] = useState<CommonType>({
        loading: true, error: ""
    });

    // id 값이 변경 되었을 때 (props, state 가 바뀌었을 때, 마운트 될 때) 발동.
    // deps 에 특정 값을 넣게 된다면 컴포넌트가 처음 마운트 될 때, 지정한 값이 바뀔 때, 언마운트 될 때, 값이 바뀌기 직전에 모두 호출.
    // useEffect 안에서 사용하는 상태나, props 가 있다면, useEffect 의 deps 에 넣어주어야 하는 것이 규칙.
    // 만약 넣어주지 않으면, useEffect 안의 함수가 실핼될 때 최신 상태/props 를 가리키지 않는다.
    // deps 를 생략한다면, 컴포넌트가 리렌터링 될 때마다 함수가 호출 됨.
    useEffect(() => {
        if (!id) return;

        const fetchUserInfo = async (id: string) => {
            try {
                const res = await ApiClient.get(`/user/${id}`);
                setUser(res.data.data);
            } catch (err) {
                setCommonStat(prev => ({
                    ...prev,
                    error: "정보를 불러오는 중 에러가 발생했습니다."
                }))
            } finally {
                setCommonStat(prev => ({
                    ...prev,
                    loading: false
                }))
            }
        };
        fetchUserInfo(id);
    }, [id]);

    if (commonStat.loading) {
        return (
            <div className={'Loading'}>
                <h1>로딩 중 입니다. 기다리세요. </h1>
            </div>
        )
    }

    if (commonStat.error) {
        return (
            <>
                <h2 className={"section-title"}>회원 상세 정보</h2>
                <div className={"divider"} />
                <h3>{commonStat.error}</h3>
            </>
        );
    }


    if (!user) {
        return (
            <>
                <h2 className={"section-title"}>회원 상세 정보</h2>
                <div className={"divider"}/>
                <h3>해당 유저는 존재하지 않습니다.</h3>
            </>
        )
    }

    return (
        <>
            <h2 className={"section-title"}>{id} 회원 상세 정보</h2>
            <div className={"divider"}/>
            유저번호 : {user.userNo}<br/>
            유저아이디 : {user.id}<br/>
            패스워드 : {user.pw}<br/>
            유저네임 : {user.name}<br/>
            주소 : {user.addr}
        </>
    )
}

export default UserDetail;
