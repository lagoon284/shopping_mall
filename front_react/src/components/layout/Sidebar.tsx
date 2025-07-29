import React from "react";
import { Link, useLocation } from "react-router-dom";
import { PropsType } from "../../interfaces/PropsInterface";

// 컴포넌트 이름을 역할에 맞게 Sidebar로 변경하는 것을 추천합니다.
export default function Sidebar({ propLoginInfo, setUserInfo }: PropsType) {
    const loginFlag: boolean = propLoginInfo.id !== "";
    const location = useLocation(); // 현재 경로를 가져와 active 클래스를 적용하기 위함

    function handleLogout(event: React.MouseEvent<HTMLAnchorElement>) {
        event.preventDefault();

        localStorage.removeItem('id');
        localStorage.removeItem('seokho_jwt');

        // 상태를 초기화하거나 null로 설정합니다.
        setUserInfo(null); // 예시, 실제 타입에 맞게 수정

        alert("정상적으로 로그아웃 되었습니다.");
        // 필요하다면 window.location.href = '/login'; 등으로 리다이렉트 할 수 있습니다.
    }

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
                {!loginFlag && (
                    <Link to="/user/signup" className={location.pathname === '/user/signup' ? 'active' : ''}>
                        회원가입
                    </Link>
                )}
                {loginFlag ? (
                    <a href="/front_react/public" onClick={handleLogout}>로그아웃</a>
                ) : (
                    <Link to="/login" className={location.pathname === '/login' ? 'active' : ''}>
                        로그인
                    </Link>
                )}
            </nav>

            {/* 3. 로그인 정보 및 추가 정보가 들어갈 푸터 영역 */}
            <div className="sidebar-footer">
                {loginFlag && <p>{propLoginInfo.name}님, 환영합니다.</p>}
            </div>
        </aside>
    );
}
