import React, {useEffect, useState} from "react";
import {Route, Routes, useNavigate} from "react-router-dom";

// Layout Components
import Sidebar from "./components/layout/Sidebar";
import Footer from "./components/layout/Footer";
import PageLayout from "./components/layout/PageLayout";

// Page Components
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

// Interfaces
import { LoginDetailType } from "./interfaces/UserInterface";
import {useAuth} from "./contexts/AuthContext";

function NewApp() {
    // const [userInfo, setUserInfo] = useState<UserInfoType | null>(null);
    const { isLoggedIn } = useAuth();
    const [ loginInfo, setLoginInfo ] = useState<LoginDetailType | null>(null);
    const [isLoading, setIsLoading] = useState<boolean>(true); // 로딩 상태를 더 간단하게 관리

    const navigate = useNavigate();

    useEffect(() => {
        const getId = localStorage.getItem('id');
        const getJwt = localStorage.getItem('seokho_jwt');

        if (!getJwt) {
            setLoginInfo(null);
            setIsLoading(false);
            return;
        } else {
            setLoginInfo({
                userId: getId,
                accessToken: getJwt
            })
        }

        setIsLoading(false);

    }, [navigate, isLoggedIn]); // getJwt 값이 변경될 때만 실행되도록 수정

    if (isLoading) {
        return (
            <div className={'Loading'}>
                <h1>로딩 중 입니다. 기다리세요. </h1>
            </div>
        );
    }

    return (
        // 최상위 div의 클래스를 app-layout으로 변경하여 CSS가 올바르게 적용되도록 합니다.
        <div className="app-layout">
            <Sidebar />
            <div className="content-wrapper">
                <main className="main-content">
                    {/* Routes 바깥에 있던 불필요한 container와 title을 제거하고 각 페이지가 자체적으로 관리하도록 합니다. */}
                    <Routes>
                        {/* 메인 페이지: 여러 컴포넌트를 보여주려면 PageLayout을 여러 번 사용합니다. */}
                        <Route path={"/"} element={
                            <>
                                <PageLayout><UserList /></PageLayout>
                                <PageLayout><ProductList /></PageLayout>
                                <PageLayout><BoardList /></PageLayout>
                            </>
                        }/>

                        {/* 각 Route의 element를 PageLayout으로 감싸 코드 중복을 제거합니다. */}
                        <Route path={"/login"} element={<PageLayout><Login /></PageLayout>} />

                        {/* 로그인하지 않았을 때만 회원가입 페이지를 보여줍니다. */}
                        {!loginInfo && <Route path={"/user/signup"} element={<PageLayout><Signup /></PageLayout>} />}

                        <Route path={"/user/allUserSelect"} element={<PageLayout><UserList /></PageLayout>} />
                        <Route path={"/user/:id"} element={<PageLayout><UserDetail /></PageLayout>} />

                        <Route path={"/product/infoProds"} element={<PageLayout><ProductList /></PageLayout>} />
                        <Route path={"/product/insert"} element={<PageLayout><ProductRegister /></PageLayout>} />
                        <Route path={"/product/:prodSeqNo"} element={<PageLayout><ProductDetail /></PageLayout>} />

                        <Route path={"/board/getList"} element={<PageLayout><BoardList /></PageLayout>} />
                        <Route path={"/board/:seqNo"} element={<PageLayout><BoardDetail /></PageLayout>} />
                        <Route path={"/board/boardReg"} element={<PageLayout><BoardRegister /></PageLayout>} />
                    </Routes>
                </main>
                <Footer />
            </div>
        </div>
    );
}

export default NewApp;
