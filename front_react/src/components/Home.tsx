import React from "react";
import {Link, useLocation} from "react-router-dom";
import {PropsType} from "../interfaces/PropsInterface";

function Home({ propLoginInfo, setUserInfo }: PropsType) {
    const location = useLocation();

    const loginFlag: boolean = propLoginInfo.id !== "";

    const getTitle = () => {
        const path = location.pathname;
        if (path === '/user/signup') return '회원가입 페이지';
        if (path === '/login') return '로그인 페이지';
        if (path.includes('/user')) return '회원 관련 페이지';
        if (path.includes('/product')) return '상품 관련 페이지';
        if (path.includes('/board')) return '게시판 관련 페이지';
        return '메인 페이지';
    };
    const title = getTitle();

    function Logout(event: React.MouseEvent<HTMLAnchorElement>) {
        event.preventDefault();

        localStorage.setItem('id', '');
        localStorage.setItem('seokho_jwt', '');

        setUserInfo(null);

        alert("정상적으로 로그아웃 되었습니다.");
    }



    return (
        <>
            <div className={"header"}>
                <nav className={"nav"} style={{ alignItems: "center", justifyContent: "space-between", padding: "0 1em" }}>
                    <Link to={"/"}>
                        <h1 style={{ margin: "0.7em", marginLeft: "0" }}>
                            REACT TEST
                        </h1>
                    </Link>
                    <div style={{ textAlign: "right" }}>
                        <Link to={"/user/allUserSelect"}>유저정보 확인하기</Link>
                        <Link to={"/product/infoProds"}>상품정보 확인하기</Link>
                        <Link to={"/board/getList"}>게시판</Link>
                        {!loginFlag &&<Link to={"/user/signup"}>회원가입</Link>}
                        {loginFlag ? (<a href={"/"} onClick={(event) => Logout(event)}>로그아웃</a>) : <Link to={"/login"}>로그인</Link>}
                    </div>
                </nav>
            </div>
            <p/>
            <div style={{display: 'flex', justifyContent: 'space-between'}}>
                <h2 style={{ marginLeft: "1em" }}>{title} 입니다.</h2>
                {loginFlag && <p style={{textAlign: "right", marginRight: "2em"}}>{propLoginInfo.name} 님, 환영합니다.</p>}
            </div>
        </>
    )
}

export default Home;