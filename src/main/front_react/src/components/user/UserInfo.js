"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const jsx_runtime_1 = require("react/jsx-runtime");
const react_1 = require("react");
const axios_1 = __importDefault(require("axios"));
const react_router_dom_1 = require("react-router-dom");
function UserInfo() {
    const [user, setUser] = (0, react_1.useState)(null);
    const { id } = (0, react_router_dom_1.useParams)();
    // 로딩 상태 관리
    const [loading, setLoading] = (0, react_1.useState)(true);
    // id 값이 변경 되었을 때 (props, state 가 바뀌었을 때, 마운트 될 때) 발동.
    // deps 에 특정 값을 넣게 된다면 컴포넌트가 처음 마운트 될 때, 지정한 값이 바뀔 때, 언마운트 될 때, 값이 바뀌기 직전에 모두 호출.
    // useEffect 안에서 사용하는 상태나, props 가 있다면, useEffect 의 deps 에 넣어주어야 하는 것이 규칙.
    // 만약 넣어주지 않으면, useEffect 안의 함수가 실핼될 때 최신 상태/props 를 가리키지 않는다.
    // deps 를 생략한다면, 컴포넌트가 리렌터링 될 때마다 함수가 호출 됨.
    (0, react_1.useEffect)(() => {
        const fetchUserInfo = async () => {
            await axios_1.default.get(`http://localhost:8080/api/user/${id}`)
                .then(res => {
                setUser(res.data.data);
            })
                .catch(error => {
                console.log(`Error fetching data:`, error);
            })
                .finally(() => {
                setLoading(false);
            });
        };
        fetchUserInfo();
    }, [id]);
    if (loading) {
        return ((0, jsx_runtime_1.jsx)("div", { className: 'loading', children: (0, jsx_runtime_1.jsx)("h1", { children: "\uB85C\uB529 \uC911 \uC785\uB2C8\uB2E4. \uAE30\uB2E4\uB9AC\uC138\uC694. " }) }));
    }
    if (user) {
        return ((0, jsx_runtime_1.jsxs)("div", { className: "UserInfo", children: [(0, jsx_runtime_1.jsxs)("h2", { children: [id, " \uD68C\uC6D0 \uC0C1\uC138 \uC815\uBCF4"] }), "\uC720\uC800\uBC88\uD638 : ", user.userNo, (0, jsx_runtime_1.jsx)("br", {}), "\uC720\uC800\uC544\uC774\uB514 : ", user.id, (0, jsx_runtime_1.jsx)("br", {}), "\uD328\uC2A4\uC6CC\uB4DC : ", user.pw, (0, jsx_runtime_1.jsx)("br", {}), "\uC720\uC800\uB124\uC784 : ", user.name, (0, jsx_runtime_1.jsx)("br", {}), "\uC8FC\uC18C : ", user.addr] }));
    }
    return ((0, jsx_runtime_1.jsx)("div", { className: "userInfo", children: (0, jsx_runtime_1.jsx)("h3", { children: "\uD574\uB2F9 \uC720\uC800\uB294 \uC874\uC7AC\uD558\uC9C0 \uC54A\uC2B5\uB2C8\uB2E4." }) }));
}
exports.default = UserInfo;
