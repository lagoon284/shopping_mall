import React, {useEffect, useState} from "react";
import {Link, Location, useLocation} from "react-router-dom";
import {PropsType} from "../TypeInterface";

function Home({ propLoginInfo, propUserInfo }: PropsType) {
    const location = useLocation();
    const [ title, setTitle ] = useState<string>('');

    useEffect(() => {
        console.log('user info props :', propLoginInfo);

        const pathName: string = location.pathname;

        if (pathName === '/user/signup') {
            setTitle('회원가입 페이지');
        } else if (pathName.includes('/user')) {
            setTitle('회원 관련 페이지');
        } else if (pathName.includes('/product')) {
            setTitle('상품 관련 페이지');
        } else {
            setTitle('메인 페이지');
        }
    }, [location]);

    return (
        <div className={"Home"}>
            <h1>
                <Link to={"/"}>
                    REACT TEST
                </Link>
            </h1>
            {propLoginInfo.id && <p>{propLoginInfo.id} 님, 환영합니다.</p>}
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
                <li>
                    <Link to={"/user/signup"}>
                        회원가입
                    </Link>
                </li>
            </ul>
            <h1>{title} 입니다.</h1>
        </div>
    )
}

export default Home;