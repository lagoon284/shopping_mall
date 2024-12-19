"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const jsx_runtime_1 = require("react/jsx-runtime");
const react_1 = require("react");
const axios_1 = __importDefault(require("axios"));
const react_router_dom_1 = require("react-router-dom");
function ProductInfo() {
    const [prod, setProd] = (0, react_1.useState)('');
    const { prodSeqNo } = (0, react_router_dom_1.useParams)();
    (0, react_1.useEffect)(() => {
        axios_1.default.get(`http://localhost:8080/api/product/${prodSeqNo}`)
            .then(res => {
            setProd(res.data.data);
        })
            .catch(error => {
            console.log('Error fetching data:', error);
        });
    }, [prodSeqNo]);
    return ((0, jsx_runtime_1.jsxs)("div", { className: "ProductInfo", children: [(0, jsx_runtime_1.jsx)("h2", { children: "\uC0C1\uD488\uC0C1\uC138 \uC815\uBCF4" }), "\uC0C1\uD488 \uBC88\uD638 : ", prod.prodSeqNo, (0, jsx_runtime_1.jsx)("br", {}), "\uC0C1\uD488\uC774\uB984 : ", prod.prodName, (0, jsx_runtime_1.jsx)("br", {}), "\uC0C1\uD488\uAC00\uACA9 : ", prod.price, (0, jsx_runtime_1.jsx)("br", {}), "\uD310\uB9E4\uC0AC : ", prod.provider, (0, jsx_runtime_1.jsx)("br", {}), "\uC0C1\uD488 \uC815\uBCF4 : ", prod.info] }));
}
exports.default = ProductInfo;
