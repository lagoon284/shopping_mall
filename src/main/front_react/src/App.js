"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const jsx_runtime_1 = require("react/jsx-runtime");
const react_1 = require("react");
const react_router_dom_1 = require("react-router-dom");
// @ts-ignore
const Home_tsx_1 = __importDefault(require("./components/Home.tsx"));
// @ts-ignore
const ExampleLifeCycle_tsx_1 = __importDefault(require("./components/example/ExampleLifeCycle.tsx"));
// @ts-ignore
const Signup_tsx_1 = __importDefault(require("./components/user/Signup.tsx"));
// @ts-ignore
const UserInfo_tsx_1 = __importDefault(require("./components/user/UserInfo.tsx"));
// @ts-ignore
const UserInfos_tsx_1 = __importDefault(require("./components/user/UserInfos.tsx"));
// @ts-ignore
const ProductInfo_tsx_1 = __importDefault(require("./components/product/ProductInfo.tsx"));
// @ts-ignore
const ProductInfos_tsx_1 = __importDefault(require("./components/product/ProductInfos.tsx"));
const axios_1 = __importDefault(require("axios"));
function App() {
    const getJwt = localStorage.getItem('jwt');
    const [userInfo, setUserInfo] = (0, react_1.useState)(null);
    // 로딩 상태 관리
    const [loading, setLoading] = (0, react_1.useState)(true);
    // 오류 상태 관리
    const [error, setError] = (0, react_1.useState)(null);
    (0, react_1.useEffect)(() => {
        const fetchUserInfo = async () => {
            if (getJwt) {
                try {
                    const response = await axios_1.default.post('http://localhost:8080/api/protected/accData', {}, {
                        headers: {
                            'Authorization': getJwt
                        }
                    });
                    setUserInfo(response.data.data);
                }
                catch (err) {
                    setError('사용자 정보를 가져오는 데 실패했습니다.');
                }
                finally {
                    setLoading(false);
                }
            }
            else {
                setLoading(false);
            }
        };
        fetchUserInfo();
    }, [getJwt]);
    const props = {
        propLoginInfo: {
            // userInfo 가 null 일 경우 기본값.
            userNo: userInfo?.userNo || 0,
            id: userInfo?.id || '',
            name: userInfo?.name || ''
        },
        propUserInfo: {
            userNo: 0,
            id: '',
            pw: '',
            name: '',
            addr: '',
            sleepFlag: false
        }
    };
    // 로딩중일때 표시할 내용.
    if (loading) {
        return ((0, jsx_runtime_1.jsx)("div", { className: 'loading', children: (0, jsx_runtime_1.jsx)("h1", { children: "\uB85C\uB529 \uC911 \uC785\uB2C8\uB2E4. \uAE30\uB2E4\uB9AC\uC138\uC694. " }) }));
    }
    if (error) {
        return ((0, jsx_runtime_1.jsx)("div", { className: 'error', children: (0, jsx_runtime_1.jsxs)("h1", { children: ["error message : ", error] }) }));
    }
    return ((0, jsx_runtime_1.jsxs)("div", { className: "App", children: [(0, jsx_runtime_1.jsx)(Home_tsx_1.default, { ...props }), (0, jsx_runtime_1.jsxs)(react_router_dom_1.Routes, { children: [(0, jsx_runtime_1.jsx)(react_router_dom_1.Route, { path: "/", element: null }), (0, jsx_runtime_1.jsx)(react_router_dom_1.Route, { path: "/example", element: (0, jsx_runtime_1.jsx)(ExampleLifeCycle_tsx_1.default, {}) }), (0, jsx_runtime_1.jsx)(react_router_dom_1.Route, { path: "/user/signup", element: (0, jsx_runtime_1.jsx)(Signup_tsx_1.default, {}) }), (0, jsx_runtime_1.jsx)(react_router_dom_1.Route, { path: "/api/user/allUserSelect", element: (0, jsx_runtime_1.jsx)(UserInfos_tsx_1.default, {}) }), (0, jsx_runtime_1.jsx)(react_router_dom_1.Route, { path: "/api/user/:id", element: (0, jsx_runtime_1.jsx)(UserInfo_tsx_1.default, {}) }), (0, jsx_runtime_1.jsx)(react_router_dom_1.Route, { path: "/api/product/infoProds", element: (0, jsx_runtime_1.jsx)(ProductInfos_tsx_1.default, {}) }), (0, jsx_runtime_1.jsx)(react_router_dom_1.Route, { path: "/api/product/:prodSeqNo", element: (0, jsx_runtime_1.jsx)(ProductInfo_tsx_1.default, {}) })] })] }));
}
exports.default = App;
