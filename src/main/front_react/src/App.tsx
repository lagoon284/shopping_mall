import React, {useEffect, useState} from "react";
import {Route, Routes, useNavigate} from "react-router-dom";
import Home from "./components/Home";
import Signup from "./components/user/Signup";
import Login from "./components/login/Login";
import UserInfo from "./components/user/UserInfo";
import UserInfos from "./components/user/UserInfos";
import ProductInfo from "./components/product/ProductInfo";
import ProductInfos from "./components/product/ProductInfos";
import axios from "axios";

import {PropsType, UserInfoType} from "./TypeInterface";

function App() {
    const getJwt = localStorage.getItem('seokho_jwt');
    // 로그인시 유저 정보 (props)
    const [ userInfo, setUserInfo ] = useState<UserInfoType | null>(null);
    // 로딩 상태 관리
    const [ loading, setLoading ] = useState<boolean>(true);
    // 오류 상태 관리
    const [ error, setError ] = useState<String |null>(null);

    const navigate = useNavigate();

    useEffect(() => {
        const fetchUserInfo = async () => {
            setLoading(true);
            if (getJwt) {
                try {
                    // console.log('자동 로그인 시도 중.');
                    const response = await axios.post('http://localhost:8080/api/protected/accData', {}, {
                        headers: { 'Authorization': 'seokhoAccAuth ' + getJwt }
                    });
                    setUserInfo(response.data.data);
                    // console.log('자동로그인 완료.');
                } catch (err) {
                    const getRefJwt = localStorage.getItem('seokho_ref_jwt');
                    await axios.put('http://localhost:8080/api/protected/refData', {}, {
                        headers: { "Authorization": 'seokhoRefAuth ' + getRefJwt }
                    })
                        .then(res => {
                            localStorage.setItem('id', res.data.data.userId);
                            localStorage.setItem('seokho_jwt', res.data.data.accessToken);
                            localStorage.setItem('seokho_ref_jwt', res.data.data.refreshToken);
                            setError('');
                        })
                        .catch(() => {
                            setError('');
                            localStorage.setItem('id', '');
                            localStorage.setItem('seokho_jwt', '');
                            localStorage.setItem('seokho_ref_jwt', '');
                            navigate('/login');
                        })
                } finally {
                    setLoading(false);
                }
            } else {
                setUserInfo(null);
                setLoading(false);
            }
        };
        fetchUserInfo();
    }, [getJwt, navigate]);

    const props: PropsType = {
        propLoginInfo: {
            // userInfo 가 null 일 경우 기본값.
            userNo: userInfo?.userNo || 0,
            id : userInfo?.id || '',
            name : userInfo?.name || ''
        }
    }

    // 로딩중일때 표시할 내용.
    if (loading) {
        return (
            <div className={'Loading'}>
                <h1>로딩 중 입니다. 기다리세요. </h1>
            </div>
        )
    }

    if (error) {
        return (
            <div className={'Error'}>
                <h1>error message : {error}</h1>
            </div>
        )
    }

    return (
        <div className="App">
            <Home {...props}/>
            <Routes>
                <Route path={"/"} element={null} />
                <Route path={"/login"} element={<Login />} />
                {props.propLoginInfo.id === '' && <Route path={"/user/signup"} element={<Signup />} />}
                <Route path={"/user/allUserSelect"} element={<UserInfos />} />
                <Route path={"/user/:id"} element={<UserInfo />} />
                <Route path={"/product/infoProds"} element={<ProductInfos />} />
                <Route path={"/product/:prodSeqNo"} element={<ProductInfo />} />
            </Routes>
        </div>
    );
}

export default App;
