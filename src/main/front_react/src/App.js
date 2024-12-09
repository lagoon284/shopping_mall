import React, {useEffect, useState} from "react";
import RouterDOM, {Route, Routes} from "react-router-dom";
import Home from "./components/Home";
import UserInfo from "./components/user/UserInfo";
import UserInfos from "./components/user/UserInfos";
import ProductInfo from "./components/product/ProductInfo";
import ProductInfos from "./components/product/ProductInfos";

function App() {
  return (
      <div className="App">
          <Home />
          <Routes>
              <Route path={"/"} element={null} />
              <Route path={"/api/user/signup"} element={<Signup />} {/* 개발 중 */}
              <Route path={"/api/user/allUserSelect"} element={<UserInfos />} />
              <Route path={"/api/user/:id"} element={<UserInfo />} />
              <Route path={"/api/product/infoProds"} element={<ProductInfos />} />
              <Route path={"/api/product/:prodSeqNo"} element={<ProductInfo />} />
          </Routes>
      </div>
  );
}

export default App;
