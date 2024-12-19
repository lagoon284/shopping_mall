"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const jsx_runtime_1 = require("react/jsx-runtime");
const react_1 = require("react");
const react_router_dom_1 = require("react-router-dom");
function Home({ propLoginInfo, propUserInfo }) {
    const location = (0, react_router_dom_1.useLocation)();
    const [title, setTitle] = (0, react_1.useState)('');
    (0, react_1.useEffect)(() => {
        console.log('user info props :', propLoginInfo);
        const pathName = location.pathname;
        const lastSlashIndex = pathName.lastIndexOf('/');
        const substrPath = lastSlashIndex > 5 ? pathName.substring(0, lastSlashIndex) : pathName;
        console.log(substrPath);
        if (substrPath === '/user/signup') {
            setTitle('회원가입 페이지');
        }
        else if (substrPath === '/api/user') {
            setTitle('회원 관련 페이지');
        }
        else if (substrPath === '/api/product') {
            setTitle('상품 관련 페이지');
        }
        else {
            setTitle('메인 페이지');
        }
    }, [location]);
    return ((0, jsx_runtime_1.jsxs)("div", { className: "Home", children: [(0, jsx_runtime_1.jsx)("h1", { children: (0, jsx_runtime_1.jsx)(react_router_dom_1.Link, { to: "/", children: "REACT TEST" }) }), propLoginInfo.id && (0, jsx_runtime_1.jsxs)("p", { children: [propLoginInfo.id, " \uB2D8, \uD658\uC601\uD569\uB2C8\uB2E4."] }), (0, jsx_runtime_1.jsxs)("ul", { children: [(0, jsx_runtime_1.jsx)("li", { children: (0, jsx_runtime_1.jsx)(react_router_dom_1.Link, { to: "/api/user/allUserSelect", children: "\uC720\uC800\uC815\uBCF4 \uD655\uC778\uD558\uAE30" }) }), (0, jsx_runtime_1.jsx)("li", { children: (0, jsx_runtime_1.jsx)(react_router_dom_1.Link, { to: "/api/product/infoProds", children: "\uC0C1\uD488\uC815\uBCF4 \uD655\uC778\uD558\uAE30" }) }), (0, jsx_runtime_1.jsx)("li", { children: (0, jsx_runtime_1.jsx)(react_router_dom_1.Link, { to: "/user/signup", children: "\uD68C\uC6D0\uAC00\uC785" }) })] }), (0, jsx_runtime_1.jsxs)("h1", { children: [title, " \uC785\uB2C8\uB2E4."] })] }));
}
exports.default = Home;
