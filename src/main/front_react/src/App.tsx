import React, {useEffect, useState} from "react";
import {Route, Routes, useLocation} from "react-router-dom";
// @ts-ignore
import Home from "./components/Home.tsx";
// @ts-ignore
import ExampleLifeCycle from "./components/example/ExampleLifeCycle.tsx";
// @ts-ignore
import Signup from "./components/user/Signup.tsx";
// @ts-ignore
import UserInfo from "./components/user/UserInfo.tsx";
// @ts-ignore
import UserInfos from "./components/user/UserInfos.tsx";
// @ts-ignore
import ProductInfo from "./components/product/ProductInfo.tsx";
// @ts-ignore
import ProductInfos from "./components/product/ProductInfos.tsx";
import axios from "axios";
import userInfo from "./components/user/UserInfo";

import {PropsType, LoginInfoType, UserInfoType} from "./TypeInterface";

function App() {
    const getJwt = localStorage.getItem('jwt');

    const [ userInfo, setUserInfo ] = useState<UserInfoType | null>(null);
    // 로딩 상태 관리
    const [ loading, setLoading ] = useState<boolean>(true);
    // 오류 상태 관리
    const [ error, setError ] = useState<String |null>(null);

    useEffect(() => {
        const fetchUserInfo = async () => {
            if (getJwt) {
                try {
                    const response = await axios.post('http://localhost:8080/api/protected/accData', {}, {
                            headers: {
                                'Authorization': getJwt
                            }
                        });
                    setUserInfo(response.data.data);
                } catch (err) {
                    setError('사용자 정보를 가져오는 데 실패했습니다.');
                } finally {
                    setLoading(false);
                }
            } else {
                setLoading(false);
            }
        };
        fetchUserInfo();
    }, [getJwt]);

    const props: PropsType = {
        propLoginInfo: {
            // userInfo 가 null 일 경우 기본값.
            userNo: userInfo?.userNo || 0,
            id : userInfo?.id || '',
            name : userInfo?.name || ''
        },
        propUserInfo: {
            userNo : 0,
            id : '',
            pw : '',
            name : '',
            addr : '',
            sleepFlag : false
        }
    }

    // 로딩중일때 표시할 내용.
    if (loading) {
        return (
            <div className={'loading'}>
                <h1>로딩 중 입니다. 기다리세요. </h1>
            </div>
        )
    }

    if (error) {
        return (
            <div className={'error'}>
                <h1>error message : {error}</h1>
            </div>
        )
    }

    return (
        <div className="App">
            <Home {...props}/>
            <Routes>
                <Route path={"/"} element={null} />
                <Route path={"/example"} element={<ExampleLifeCycle />} />
                <Route path={"/user/signup"} element={<Signup />} />
                <Route path={"/api/user/allUserSelect"} element={<UserInfos />} />
                <Route path={"/api/user/:id"} element={<UserInfo />} />
                <Route path={"/api/product/infoProds"} element={<ProductInfos />} />
                <Route path={"/api/product/:prodSeqNo"} element={<ProductInfo />} />
            </Routes>
        </div>
    );
}

export default App;
