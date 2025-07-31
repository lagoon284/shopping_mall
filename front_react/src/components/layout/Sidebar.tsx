import React from "react";
import { Link, useLocation } from "react-router-dom";
import { useAuth } from "../../contexts/AuthContext";

export default function Sidebar() {
    const { isLoggedIn, logout, user } = useAuth();
    const location = useLocation(); // 현재 경로를 가져와 active 클래스를 적용하기 위함

    // '/user/'로 시작하지만, 예외 경로가 아닌 경우를 확인하는 함수
    const isUserActive = location.pathname.startsWith('/user/') &&
        location.pathname !== '/user/signup';

    return (
        // 모든 인라인 스타일을 제거하고 CSS 클래스로 제어합니다.
        <aside className="sidebar">
            {/* 1. 로고가 포함된 헤더 영역 */}
            <div className="sidebar-header">
                <Link to="/">
                    <h1 className="logo">REACT TEST</h1>
                </Link>
            </div>

            {/* 2. 내비게이션 링크 영역 */}
            <nav className="nav">
                <Link to="/user/allUserSelect" className={isUserActive ? 'active' : ''}>
                    유저정보 확인하기
                </Link>
                <Link to="/product/infoProds" className={location.pathname.startsWith('/product') ? 'active' : ''}>
                    상품정보 확인하기
                </Link>
                <Link to="/board/getList" className={location.pathname.startsWith('/board') ? 'active' : ''}>
                    게시판
                </Link>

                {/* 로그인 상태에 따라 다른 메뉴를 보여줍니다. */}
                {!isLoggedIn && (
                    <Link to="/user/signup" className={location.pathname === '/user/signup' ? 'active' : ''}>
                        회원가입
                    </Link>
                )}
                {isLoggedIn ? (
                    <a href="/" onClick={logout}>로그아웃</a>
                ) : (
                    <Link to="/login" className={location.pathname === '/login' ? 'active' : ''}>
                        로그인
                    </Link>
                )}
            </nav>

            {/* 3. 로그인 정보 및 추가 정보가 들어갈 푸터 영역 */}
            <div className="sidebar-footer">
                {isLoggedIn && <p>{user?.id}님, 환영합니다.</p>}
            </div>
        </aside>
    );
}
