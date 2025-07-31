// import React, {useEffect, useState} from "react";
// import {Route, Routes, useLocation, useNavigate} from "react-router-dom";
// import axios from "axios";
//
// // Layout Components
// // import Home from "./components/Home";
// import Sidebar from "./components/layout/Sidebar";
// import Footer from "./components/layout/Footer";
// import PageLayout from "./components/layout/PageLayout";
//
// // Page Components
// import Signup from "./components/user/Signup";
// import Login from "./components/login/Login";
// import UserDetail from "./components/user/UserDetail";
// import ProductDetail from "./components/product/ProductDetail";
// import ProductList from "./components/product/ProductList";
// import ProductRegister from "./components/product/ProductRegister";
// import UserList from "./components/user/UserList";
// import BoardRegister from "./components/board/BoardRegister";
// import BoardList from "./components/board/BoardList";
// import BoardDetail from "./components/board/BoardDetail";
//
// // Interfaces
// import { CommonType } from "./interfaces/CommonInterface";
// import { PropsType } from "./interfaces/PropsInterface";
// import { UserInfoType } from "./interfaces/UserInterface";
//
//
//
//
// function App() {
//     // 로그인시 유저 정보 (props)
//     const [ userInfo, setUserInfo ] = useState<UserInfoType | null>(null);
//     // common 상태 관리
//     const [ commonStat, setCommonStat ] = useState<CommonType>({
//         loading: true, error: ""
//     });
//
//     const location = useLocation();
//
//     const navigate = useNavigate();
//
//     const getJwt = localStorage.getItem('seokho_jwt');
//
//
//
//     const getTitle = () => {
//         const path = location.pathname;
//         if (path === '/user/signup') return '회원가입 페이지';
//         if (path === '/login') return '로그인 페이지';
//         if (path.includes('/user')) return '회원 관련 페이지';
//         if (path.includes('/product')) return '상품 관련 페이지';
//         if (path.includes('/board')) return '게시판 관련 페이지';
//         return '메인 페이지';
//     };
//
//     const title = getTitle();
//
//     const fetchUserInfo = async () => {
//         try {
//             const response = await axios.post('http://localhost:8080/api/protected/accData', {}, {
//                 headers: { 'Authorization': 'seokhoAccAuth ' + getJwt }
//             });
//             if(!userInfo || JSON.stringify(userInfo) !== JSON.stringify(response.data.data)) {
//                 localStorage.setItem('id', response.data.data.id);
//                 localStorage.setItem('seokho_jwt', response.data.data.accessToken);
//                 setUserInfo(response.data.data);
//             }
//         } catch (err) {
//             localStorage.setItem('id', '');
//             localStorage.setItem('seokho_jwt', '');
//             alert('자동 로그인 인증에 실패하였습니다. 다시 로그인 해주세요.');
//             navigate('/login');
//         }
//     };
//
//     const props: PropsType = {
//         propLoginInfo: {
//             // userInfo 가 null 일 경우 기본값.
//             userNo: userInfo?.userNo || 0,
//             id : userInfo?.id || '',
//             name : userInfo?.name || ''
//         },
//         setUserInfo
//     }
//
//     useEffect(() => {
//         if (!getJwt) {
//             setUserInfo(null);
//             setCommonStat(prev => ({
//                 ...prev,
//                 loading: false
//             }))
//             return;
//         }
//
//         (async () => {
//             await fetchUserInfo();
//             setCommonStat(prev => ({
//                 ...prev,
//                 loading: false
//             }))
//         })();
//
//     }, [navigate]);
//
//     // 로딩중일때 표시할 내용.
//     if (commonStat.loading) {
//         return (
//             <div className={'Loading'}>
//                 <h1>로딩 중 입니다. 기다리세요. </h1>
//             </div>
//         )
//     }
//
//     if (commonStat.error) {
//         return (
//             <div className={'Error'}>
//                 <h1>error message : {commonStat.error}</h1>
//             </div>
//         )
//     }
//
//     return (
//         <div className="App container" style={{paddingLeft: '3%'}}>
//             <Sidebar {...props} />
//             <div className="content-wrapper">
//                 <main className={"main-content"}>
//                     <div className={"container"}>
//                         <h2 style={{marginLeft: "1em"}}>{title} 입니다.</h2>
//                         <div className={"divider"}/>
//                         <Routes>
//                             <Route path={"/"}
//                                    element={
//                                        //  컴포넌트 이해를 위해 메인에서 렌더링 시도.
//                                        <>
//                                            <div className="section">
//                                                <UserList/>
//                                            </div>
//                                            <div className="section">
//                                                <ProductList/>
//                                            </div>
//                                            <div className="section">
//                                                <BoardList/>
//                                            </div>
//                                        </>}
//                             />
//                             <Route path={"/login"}
//                                    element={
//                                        <>
//                                            <div className="section">
//                                                <Login/>
//                                            </div>
//                                        </>}
//                             />
//                             {props.propLoginInfo.id === '' && (<Route path={"/user/signup"}
//                                                                       element={
//                                                                           <>
//                                                                               <div className="section">
//                                                                                   <Signup/>
//                                                                               </div>
//                                                                           </>}
//                             />)}
//                             <Route path={"/user/allUserSelect"}
//                                    element={
//                                        <>
//                                            <div className="section">
//                                                <UserList/>
//                                            </div>
//                                        </>}
//                             />
//                             <Route path={"/user/:id"}
//                                    element={
//                                        <>
//                                            <div className="section">
//                                                <UserDetail/>
//                                            </div>
//                                        </>}
//                             />
//                             <Route path={"/product/infoProds"}
//                                    element={
//                                        <>
//                                            <div className="section">
//                                                <ProductList/>
//                                            </div>
//                                        </>}
//                             />
//                             <Route path={"/product/insert"}
//                                    element={
//                                        <>
//                                            <div className="section">
//                                                <ProductRegister/>
//                                            </div>
//                                        </>}
//                             />
//                             <Route path={"/product/:prodSeqNo"}
//                                    element={
//                                        <>
//                                            <div className="section">
//                                                <ProductDetail/>
//                                            </div>
//                                        </>}
//                             />
//                             <Route path={"/board/getList"}
//                                    element={
//                                        <>
//                                            <div className="section">
//                                                <BoardList/>
//                                            </div>
//                                        </>}
//                             />
//                             <Route path={"/board/:seqNo"}
//                                    element={
//                                        <>
//                                            <div className="section">
//                                                <BoardDetail {...props}/>
//                                            </div>
//                                        </>
//                                    }
//                             />
//                             <Route path={"/board/boardReg"}
//                                    element={
//                                        <>
//                                            <div className="section">
//                                                <BoardRegister {...props} />
//                                            </div>
//                                        </>}
//                             />
//                         </Routes>
//                     </div>
//                 </main>
//                 <div className={"divider"}/>
//                 <Footer/>
//             </div>
//         </div>
//     );
// }
//
// export default App;
