// import React, {useEffect, useState} from "react";
import {Route, Routes, useLocation} from "react-router-dom";
import Home from "./components/Home";
import ExampleLifeCycle from "./components/example/ExampleLifeCycle";
import Signup from "./components/user/Signup";
import UserInfo from "./components/user/UserInfo";
import UserInfos from "./components/user/UserInfos";
import ProductInfo from "./components/product/ProductInfo";
import ProductInfos from "./components/product/ProductInfos";
import {useEffect, useState} from "react";

function App() {
    return (
        <div className="App">
            <Home />
            <Routes>
                <Route path={"/"} element={null} />
                <Route path={"/example"} element={<ExampleLifeCycle />} />
                <Route path={"/user/signup"} element={<Signup />} />
                <Route path={"/api/user/allUserSelect"} element={<UserInfos />} />
                <Route path={"/api/user/:id"} element={<UserInfo />} />
                <Route path={"/api/product/infoProds"} element={<ProductInfos />} />
                <Route path={"/api/product/:prodSeqNo"} element={<ProductInfo />} />
            </Routes>
        </div>
    );
}

export default App;
