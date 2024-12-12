import React, {useEffect, useState} from "react";
import axios from "axios";
import {useParams} from "react-router-dom";

function UserInfo() {
    const [ user, setUser ] = useState([]);

    const { id } = useParams();

    // id 값이 변경 되었을 때 (props, state 가 바뀌었을 때, 마운트 될 때) 발동.
    // deps 에 특정 값을 넣게 된다면 컴포넌트가 처음 마운트 될 때, 지정한 값이 바뀔 때, 언마운트 될 때, 값이 바뀌기 직전에 모두 호출.
    // useEffect 안에서 사용하는 상태나, props 가 있다면, useEffect 의 deps 에 넣어주어야 하는 것이 규칙.
    // 만약 넣어주지 않으면, useEffect 안의 함수가 실핼될 때 최신 상태/props 를 가리키지 않는다.
    // deps 를 생략한다면, 컴포넌트가 리렌터링 될 때마다 함수가 호출 됨.
    useEffect(() => {
        axios.get(`http://localhost:8080/api/user/${id}`)
            .then(res => {
                setUser(res.data.data);
            })
            .catch(error => {
                console.log(`Error fetching data:`, error);
            })
    }, [id]);

    return (
        <div className={"UserInfo"}>
            <h2>{id} 회원 상세 정보</h2>
            유저번호 : {user.userNo}<br/>
            유저아이디 : {user.id}<br/>
            패스워드 : {user.pw}<br/>
            유저네임 : {user.name}<br/>
            주소 : {user.addr}
        </div>
    )
}

export default UserInfo;
