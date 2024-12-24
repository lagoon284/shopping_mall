import React, {useEffect, useState} from "react";
import {Link, useLocation, useNavigate} from "react-router-dom";
import {PropsType} from "../TypeInterface";

function Home({ propLoginInfo }: PropsType) {
    const location = useLocation();
    const navigate = useNavigate();
    const [ title, setTitle ] = useState<string>('');



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

    function Logout(event:React.MouseEvent<HTMLButtonElement>) {
        event.preventDefault();

        // console.log('로그아웃 시도');

        localStorage.setItem('id', '');
        localStorage.setItem('seokho_jwt', '');
        localStorage.setItem('seokho_ref_jwt', '');

        alert("정상적으로 로그아웃 되었습니다.");
        navigate("/");
    }



    return (
        <div className={"Home"}>
            <h1>
                <Link to={"/"}>
                    REACT TEST
                </Link>
            </h1>
            {propLoginInfo.id && <p>{propLoginInfo.name} 님, 환영합니다.</p>}
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
                {!propLoginInfo.id && <li>
                    <Link to={"/user/signup"}>
                        회원가입
                    </Link>
                </li>}
                <li>
                    {propLoginInfo.id ? <button onClick={Logout} style={{ background: 'none', color: 'blue', textDecoration: 'underline', border: 'none' }}>로그아웃</button> :
                        <Link to={"/login"}>로그인 </Link>}
                </li>
            </ul>
            <h1>{title} 입니다.</h1>
        </div>
    )
}

export default Home;