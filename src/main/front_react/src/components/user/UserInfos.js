"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const jsx_runtime_1 = require("react/jsx-runtime");
const react_1 = require("react");
const axios_1 = __importDefault(require("axios"));
const react_router_dom_1 = require("react-router-dom");
function UserInfos() {
    const [users, setUsers] = (0, react_1.useState)(null);
    // 로딩 상태 관리
    const [loading, setLoading] = (0, react_1.useState)(true);
    (0, react_1.useEffect)(() => {
        const fetchUserInfo = async () => {
            await axios_1.default.get('http://localhost:8080/api/user/allUserSelect')
                .then(res => {
                console.log(res.data.data);
                setUsers(res.data.data);
            })
                .catch(error => {
                console.log('Error fetching data:', error);
            })
                .finally(() => {
                setLoading(false);
            });
        };
        fetchUserInfo();
    }, []);
    if (loading) {
        return ((0, jsx_runtime_1.jsx)("div", { className: 'loading', children: (0, jsx_runtime_1.jsx)("h1", { children: "\uB85C\uB529 \uC911 \uC785\uB2C8\uB2E4. \uAE30\uB2E4\uB9AC\uC138\uC694. " }) }));
    }
    return ((0, jsx_runtime_1.jsxs)("div", { className: "UserInfo", children: [(0, jsx_runtime_1.jsx)("h2", { children: "\uD68C\uC6D0 \uC815\uBCF4" }), (0, jsx_runtime_1.jsx)("h3", { children: "\uD68C\uC6D0\uB370\uC774\uD130 :" }), (0, jsx_runtime_1.jsx)("ul", { children: users && users.map((user) => {
                    return ((0, jsx_runtime_1.jsxs)("li", { children: [(0, jsx_runtime_1.jsxs)(react_router_dom_1.Link, { to: `/api/user/${user.id}`, children: [user.id, " \uD68C\uC6D0 \uC0C1\uC138\uBCF4\uAE30"] }), (0, jsx_runtime_1.jsx)("br", {}), "\uC720\uC800\uBC88\uD638 : ", user.userNo, (0, jsx_runtime_1.jsx)("br", {}), "\uC720\uC800\uC544\uC774\uB514 : ", user.id, (0, jsx_runtime_1.jsx)("br", {}), "\uD328\uC2A4\uC6CC\uB4DC : ", user.pw, (0, jsx_runtime_1.jsx)("br", {}), "\uC720\uC800\uB124\uC784 : ", user.name, (0, jsx_runtime_1.jsx)("br", {}), "\uC8FC\uC18C : ", user.addr, (0, jsx_runtime_1.jsx)("p", {})] }, user.id));
                }) }), (0, jsx_runtime_1.jsx)("pre", { children: JSON.stringify(users, null, 4) })] }));
}
exports.default = UserInfos;
