import React, { useEffect, useState } from "react";
import axios from "axios";
import {Link} from "react-router-dom";

function UserInfos() {
    const [users, setUsers] = useState([]);

    useEffect(() => {
        axios.get('http://localhost:8080/api/user/allUserSelect')
            .then(res => {
                console.log(res.data.data);
                setUsers(res.data.data);
            })
            .catch(error => {
                console.log('Error fetching data:', error);
            })
    }, []);

    return (
        <div className="UserInfo">
            <h2>회원 정보</h2>
            <h3>회원데이터 :</h3>
            <ul>
                {users.map((user, index) => {
                    return (
                        <li key={user.id}>
                            <Link to={`/api/user/${user.id}`}>{user.id} 회원 상세보기</Link><br/>
                            유저번호 : {user.userNo}<br/>
                            유저아이디 : {user.id}<br/>
                            패스워드 : {user.pw}<br/>
                            유저네임 : {user.name}<br/>
                            주소 : {user.addr}
                            <p></p>
                        </li>
                    );
                })}
            </ul>
            <pre>{JSON.stringify(users, null, 4)}</pre>
        </div>
    );
}

export default UserInfos;