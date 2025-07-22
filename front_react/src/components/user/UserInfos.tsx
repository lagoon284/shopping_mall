import React, { useEffect, useState } from "react";
import axios from "axios";
import {Link} from "react-router-dom";
import { UserInfoType } from "../../interfaces/UserInterface";

function UserInfos() {
    const [users, setUsers] = useState<UserInfoType[] | null>(null);

    // 로딩 상태 관리
    const [ loading, setLoading ] = useState<boolean>(true);

    useEffect(() => {
        const fetchUserInfo = async () => {await axios.get('http://localhost:8080/api/user/allUserSelect')
            .then(res => {
                // console.log(res.data.data);
                setUsers(res.data.data);
            })
            .catch(error => {
                console.log('Error fetching data:', error);
            })
            .finally(() => {
                setLoading(false);
            })
        }
        fetchUserInfo();
    }, []);

    if (loading) {
        return (
            <div className={'Loading'}>
                <h1>로딩 중 입니다. 기다리세요. </h1>
            </div>
        )
    }

    return (
        <div className="section">
            <h2 className={"section-title"}>회원 정보</h2>
            <div className={"divider"}/>
            <h3 key={"user"}>회원데이터 :</h3>
            <div className={"grid"}>
                {users && users.map((user) => {
                    return (
                        <Link to={`/user/${user.id}`}>
                        <div className={"card"} key={user.id}>
                            유저번호 : {user.userNo}<br/>
                            유저아이디 : {user.id}<br/>
                            패스워드 : {user.pw}<br/>
                            유저네임 : {user.name}<br/>
                            주소 : {user.addr}
                        </div>
                        </Link>
                    );
                })}
            </div>
            <pre>{JSON.stringify(users, null, 4)}</pre>
        </div>
    );
}

export default UserInfos;