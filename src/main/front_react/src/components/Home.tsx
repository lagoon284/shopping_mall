import React, {useEffect, useState} from "react";
import {Link, useLocation, useNavigate} from "react-router-dom";
import {PropsType} from "../interfaces/PropsInterface";

function Home({ propLoginInfo, setUserInfo }: PropsType) {
    const location = useLocation();
    const navigate = useNavigate();
    const [ title, setTitle ] = useState<string>('');

    let loginFlag: boolean = propLoginInfo.id !== "";

    useEffect(() => {
        // console.log('user info props :', propLoginInfo);

        const pathName: string = location.pathname;

        if (pathName === '/user/signup') {
            setTitle('회원가입 페이지');
        } else if (pathName === '/login') {
            setTitle('로그인 페이지');
        } else if (pathName.includes('/user')) {
            setTitle('회원 관련 페이지');
        } else if (pathName.includes('/product')) {
            setTitle('상품 관련 페이지');
        } else {
            setTitle('메인 페이지');
        }
    }, [location, propLoginInfo]);

    function Logout(event: React.MouseEvent<HTMLAnchorElement>) {
        event.preventDefault();

        localStorage.setItem('id', '');
        localStorage.setItem('seokho_jwt', '');
        localStorage.setItem('seokho_ref_jwt', '');

        setUserInfo(null);

        alert("정상적으로 로그아웃 되었습니다.");
    }



    return (
        <div className={"Home"}>
            <h1>
                <Link to={"/"}>
                    REACT TEST
                </Link>
            </h1>
            {loginFlag && <p>{propLoginInfo.name} 님, 환영합니다.</p>}
            <ul>
                <li>
                    <Link to={"/user/allUserSelect"}>
                        유저정보 확인하기
                    </Link>
                </li>
                <li>
                    <Link to={"/product/infoProds"}>
                        상품정보 확인하기
                    </Link>
                </li>
                {!loginFlag && <li>
                    <Link to={"/user/signup"}>
                        회원가입
                    </Link>
                </li>}
                <li>
                    {loginFlag ? <a href={"/"} onClick={(event) => Logout(event)} >로그아웃</a> :
                        <Link to={"/login"}>로그인 </Link>}
                </li>
            </ul>
            <h1>{title} 입니다.</h1>
        </div>
    )
}

export default Home;