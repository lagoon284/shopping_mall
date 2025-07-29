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
        const fetchUserInfo = async () => {
            await axios.get('http://localhost:8080/api/user/allUserSelect')
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
            <>
                <div className="container">
                    <header className="page-header">
                        <h1>회원 정보</h1>
                    </header>
                    <div className="section">
                        <p>데이터를 불러오는 중입니다...</p>
                    </div>
                </div>
            </>
        )
    }

    if (commonStat.error) {
        return (
            <>
                <div className="container">
                    <header className="page-header">
                        <h1>오류 발생</h1>
                    </header>
                    <div className="section">
                        <p style={{color: 'red'}}>{commonStat.error}</p>
                    </div>
                </div>
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
            <div className="container">
                <header className="page-header">
                    <h1>회원 정보</h1>
                    <p>전체 회원 목록을 관리합니다.</p>
                </header>

                <div className="section">
                    {/* 테이블로 사용자 정보를 표시합니다. */}
                    <table>
                        <thead>
                        <tr>
                            <th>유저번호</th>
                            <th>아이디</th>
                            <th>이름</th>
                            <th>주소</th>
                            <th>관리</th>
                        </tr>
                        </thead>
                        <tbody>
                        {/* 사용자가 없을 경우 메시지를 표시합니다. */}
                        {(!users || users.length === 0) ? (
                            <tr>
                                <td colSpan={5} className="text-center">등록된 유저가 없습니다.</td>
                            </tr>
                        ) : (
                            // users 배열을 map으로 돌려 각 사용자를 행(tr)으로 렌더링합니다.
                            users.map((user) => (
                                <tr key={user.id}>
                                    <td className="text-center">{user.userNo}</td>
                                    <td>{user.id}</td>
                                    <td>{user.name}</td>
                                    <td>{user.addr}</td>
                                    <td className="text-center">
                                        {/* 상세보기/수정 페이지로 이동하는 링크를 버튼 형태로 제공합니다. */}
                                        <Link to={`/user/${user.id}`} className="btn btn-sm btn-secondary">
                                            상세
                                        </Link>
                                    </td>
                                </tr>
                            ))
                        )}
                        </tbody>
                    </table>
                </div>
            </div>
        </>
    );
}

export default UserList;