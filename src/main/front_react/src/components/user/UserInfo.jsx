import React, {useEffect, useState} from "react";
import axios from "axios";
import {useParams} from "react-router-dom";

function UserInfo() {
    const [ user, setUser ] = useState([]);

    const { id } = useParams();

    useEffect(() => {
        axios.get(`http://localhost:8080/api/user/${id}`)
            .then(res => {
                setUser(res.data.data);
            })
            .catch(error => {
                console.log(`Error fetching data:`, error);
            })
    }, []);

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
