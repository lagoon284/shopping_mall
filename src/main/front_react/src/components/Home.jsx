import React from "react";
import {Link} from "react-router-dom";

function Home() {
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
            </ul>
            <h1>메인 화면 입니다.</h1>
        </div>
    )
}

export default Home;