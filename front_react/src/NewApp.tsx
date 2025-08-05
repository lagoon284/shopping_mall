import React from "react";
import {Route, Routes} from "react-router-dom";

// Layout Components
import Sidebar from "./components/layout/Sidebar";
import Footer from "./components/layout/Footer";
import PageLayout from "./components/layout/PageLayout";
import PrivateRoute from "./components/util/PrivateRoute";

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
import {useAuth} from "./contexts/AuthContext";

function NewApp() {
    // 로그인 되어있는지 여부.
    const { isLoggedIn } = useAuth();

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
                        {!isLoggedIn && <Route path={"/user/signup"} element={<PageLayout><Signup /></PageLayout>} />}

                        <Route path={"/user/allUserSelect"} element={   <PrivateRoute requiredRole={'ADMIN'}>
                                                                            <PageLayout>
                                                                                <UserList />
                                                                            </PageLayout>
                                                                        </PrivateRoute>} />
                        <Route path={"/user/:id"} element={<PageLayout><UserDetail /></PageLayout>} />

                        <Route path={"/product/infoProds"} element={<PageLayout><ProductList /></PageLayout>} />
                        <Route path={"/product/insert"} element={<PrivateRoute requiredRole={'ADMIN'}><PageLayout><ProductRegister /></PageLayout></PrivateRoute>} />
                        <Route path={"/product/:prodSeqNo"} element={<PageLayout><ProductDetail /></PageLayout>} />

                        <Route path={"/board/getList"} element={<PageLayout><BoardList /></PageLayout>} />
                        <Route path={"/board/:seqNo"} element={<PageLayout><BoardDetail /></PageLayout>} />
                        <Route path={"/board/boardReg"} element={   <PrivateRoute requiredRole={'USER'}>
                                                                        <PageLayout>
                                                                            <BoardRegister />
                                                                        </PageLayout>
                                                                    </PrivateRoute>} />
                    </Routes>
                </main>
                <Footer />
            </div>
        </div>
    );
}

export default NewApp;
