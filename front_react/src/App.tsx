import React, {useEffect, useState} from "react";
import {Route, Routes, useNavigate} from "react-router-dom";
import axios from "axios";

import Home from "./components/Home";
import Footer from "./components/Footer";
import Signup from "./components/user/Signup";
import Login from "./components/login/Login";
import UserDetail from "./components/user/UserDetail";
import ProductDetail from "./components/product/ProductDetail";
import ProductList from "./components/product/ProductList";
import ProductRegister from "./components/product/ProductRegister";
import UserList from "./components/user/UserList";
import BoardRegister from "./components/board/BoardRegister";
import BoardList from "./components/board/BoardList";
import BoardDetail from "./components/board/BoardDetail";

import { PropsType } from "./interfaces/PropsInterface";
import { UserInfoType } from "./interfaces/UserInterface";




function App() {
    // 로그인시 유저 정보 (props)
    const [ userInfo, setUserInfo ] = useState<UserInfoType | null>(null);
    // 로딩 상태 관리
    const [ loading, setLoading ] = useState<boolean>(true);
    // 오류 상태 관리
    const [ error, setError ] = useState<string |null>(null);

    const navigate = useNavigate();

    const getJwt = localStorage.getItem('seokho_jwt');

    const props: PropsType = {
        propLoginInfo: {
            // userInfo 가 null 일 경우 기본값.
            userNo: userInfo?.userNo || 0,
            id : userInfo?.id || '',
            name : userInfo?.name || ''
        },
        setUserInfo
    }

    const fetchUserInfo = async () => {
        try {
            const response = await axios.post('http://localhost:8080/api/protected/accData', {}, {
                headers: { 'Authorization': 'seokhoAccAuth ' + getJwt }
            });
            if(!userInfo || JSON.stringify(userInfo) !== JSON.stringify(response.data.data)) {
                localStorage.setItem('id', response.data.data.id);
                localStorage.setItem('seokho_jwt', response.data.data.accessToken);
                setUserInfo(response.data.data);
            }
        } catch (err) {
            localStorage.setItem('id', '');
            localStorage.setItem('seokho_jwt', '');
            alert('인증에 실패하였습니다. 다시 로그인 해주세요.');
            navigate('/login');
        }
    };

    useEffect(() => {
        if (!getJwt) {
            setUserInfo(null);
            return;
        }

        (async () => {
            await fetchUserInfo();
            setLoading(false);
        })();

    }, [navigate]);

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
        <div className="App container" style={{paddingLeft: '3%'}}>
            <Home {...props}/>
            <Routes>
                <Route path={"/"}
                       element={
                           //  컴포넌트 이해를 위해 메인에서 렌더링 시도.
                           <>
                               <div className="section">
                                   <UserList/>
                               </div>
                               <div className="section">
                                   <ProductList/>
                               </div>
                               <div className="section">
                                   <BoardList/>
                               </div>
                           </>}
                />
                <Route path={"/login"}
                       element={
                           <>
                               <div className="section">
                                   <Login/>
                               </div>
                           </>}
                />
                {props.propLoginInfo.id === '' && (<Route path={"/user/signup"}
                                                          element={
                                                              <>
                                                                  <div className="section">
                                                                      <Signup/>
                                                                  </div>
                                                              </>}
                />)}
                <Route path={"/user/allUserSelect"}
                       element={
                           <>
                               <div className="section">
                                   <UserList/>
                               </div>
                           </>}
                />
                <Route path={"/user/:id"}
                       element={
                           <>
                               <div className="section">
                                   <UserDetail/>
                               </div>
                           </>}
                />
                <Route path={"/product/infoProds"}
                       element={
                           <>
                               <div className="section">
                                   <ProductList/>
                               </div>
                           </>}
                />
                <Route path={"/product/insert"}
                       element={
                           <>
                               <div className="section">
                                   <ProductRegister/>
                               </div>
                           </>}
                />
                <Route path={"/product/:prodSeqNo"}
                       element={
                           <>
                               <div className="section">
                                   <ProductDetail/>
                               </div>
                           </>}
                />
                <Route path={"/board/getList"}
                       element={
                           <>
                               <div className="section">
                                   <BoardList/>
                               </div>
                           </>}
                />
                <Route path={"/board/:seqNo"}
                       element={
                           <>
                               <div className="section">
                                   <BoardDetail {...props}/>
                               </div>
                           </>
                       }
                />
                <Route path={"/board/boardReg"}
                       element={
                           <>
                               <div className="section">
                                   <BoardRegister propLoginInfo={props.propLoginInfo} setUserInfo={props.setUserInfo}/>
                               </div>
                           </>}
                />
            </Routes>
            <div className={"divider"} style={{ opacity: "0.3", background: "#868686" }}/>
            <Footer/>

        </div>
    );
}

export default App;
