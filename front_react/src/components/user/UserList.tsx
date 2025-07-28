import React, { useEffect, useState } from "react";
import axios from "axios";
import {Link} from "react-router-dom";

import { CommonType } from "../../interfaces/CommonInterface";
import { UserInfoType } from "../../interfaces/UserInterface";

function UserList() {
    const [users, setUsers] = useState<UserInfoType[] | null>(null);

    // common 상태 관리
    const [ commonStat, setCommonStat ] = useState<CommonType>({
        loading: true, error: ""
    });

    useEffect(() => {
        const fetchUserInfo = async () => {await axios.get('http://localhost:8080/api/user/allUserSelect')
            .then(res => {
                // console.log(res.data.data);
                setUsers(res.data.data);
            })
            .catch(error => {
                console.log('Error fetching data:', error);
                setCommonStat(prev => ({
                    ...prev,
                    error: 'Error fetching data:' + error
                }))
            })
            .finally(() => {
                setCommonStat(prev => ({
                    ...prev,
                    loading: false
                }))
            })
        }
        fetchUserInfo();
    }, []);

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
                <h2 className={"section-title"}>회원 정보</h2>
                <div className={"divider"} />
                <h3>{commonStat.error}</h3>
            </>
        )
    }

    if (!users) {
        return (
            <>
                <h2 className={"section-title"}>회원 정보</h2>
                <div className={"divider"}/>
                <div className={"grid"}>
                    등록된 유저가 없습니다.
                </div>
            </>
        )
    }

    return (
        <>
            <h2 className={"section-title"}>회원 정보</h2>
            <div className={"divider"}/>
            <div className={"grid"}>
                {users.map((user) => {
                    return (
                        <Link to={`/user/${user.id}`} key={user.id}>
                            <div className={"card"}>
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
        </>
    );
}

export default UserList;