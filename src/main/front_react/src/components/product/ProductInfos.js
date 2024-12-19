"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const jsx_runtime_1 = require("react/jsx-runtime");
const react_1 = require("react");
const axios_1 = __importDefault(require("axios"));
const react_router_dom_1 = require("react-router-dom");
function ProductInfos() {
    const [prods, setProds] = (0, react_1.useState)([]);
    (0, react_1.useEffect)(() => {
        axios_1.default.get('http://localhost:8080/api/product/infoProds')
            .then(res => {
            console.log(res.data.data);
            setProds(res.data.data);
        })
            .catch(error => {
            console.log('Error fetching data:', error);
        });
    }, []);
    return ((0, jsx_runtime_1.jsxs)("div", { className: "ProductInfos", children: [(0, jsx_runtime_1.jsx)("h2", { children: "\uC0C1\uD488 \uC815\uBCF4" }), (0, jsx_runtime_1.jsx)("p", {}), (0, jsx_runtime_1.jsx)("h3", { children: "\uC0C1\uD488\uB370\uC774\uD130 :" }), (0, jsx_runtime_1.jsx)("ul", { children: prods.map((prod, index) => {
                    return ((0, jsx_runtime_1.jsxs)("li", { children: [(0, jsx_runtime_1.jsxs)(react_router_dom_1.Link, { to: `/api/product/${prod.prodSeqNo}`, children: [prod.prodSeqNo, "\uBC88 \uC0C1\uD488 \uC0C1\uC138\uBCF4\uAE30!!"] }), (0, jsx_runtime_1.jsx)("br", {}), "\uC0C1\uD488 \uBC88\uD638 : ", prod.prodSeqNo, (0, jsx_runtime_1.jsx)("br", {}), "\uC0C1\uD488\uC774\uB984 : ", prod.prodName, (0, jsx_runtime_1.jsx)("br", {}), "\uC0C1\uD488\uAC00\uACA9 : ", prod.price, (0, jsx_runtime_1.jsx)("br", {}), "\uD310\uB9E4\uC0AC : ", prod.provider, (0, jsx_runtime_1.jsx)("br", {}), "\uC0C1\uD488 \uC815\uBCF4 : ", prod.info, (0, jsx_runtime_1.jsx)("p", {})] }, prod.prodSeqNo));
                }) })] }));
}
exports.default = ProductInfos;
