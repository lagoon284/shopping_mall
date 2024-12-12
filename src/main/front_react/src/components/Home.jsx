import React, {useEffect, useState} from "react";
import {Link, useLocation} from "react-router-dom";

function Home() {
    const location = useLocation();
    const [ title, setTitle ] = useState('');

    useEffect(() => {
        const pathName = location.pathname;

        const lastSlashIndex = pathName.lastIndexOf('/');
        const substrPath = lastSlashIndex > 5 ? pathName.substring(0, lastSlashIndex) : pathName;

        if (substrPath === '/user/signup') {
            setTitle('회원가입 페이지');
        } else if (substrPath === '/api/user') {
            setTitle('회원 관련 페이지');
        } else if (substrPath === '/api/product') {
            setTitle('상품 관련 페이지');
        } else {
            setTitle('메인 페이지');
        }
    }, [location]);

    return (
        <div className={"Home"}>
            <h1>
                <Link to={"/"}>
                    REACT TEST (GET)
                </Link>
            </h1>
            <ul>
                <li>
                    <Link to={"/api/user/allUserSelect"}>
                        유저정보 확인하기
                    </Link>
                </li>
                <li>
                    <Link to={"/api/product/infoProds"}>
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