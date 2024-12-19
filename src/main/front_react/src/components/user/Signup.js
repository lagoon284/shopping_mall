"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const jsx_runtime_1 = require("react/jsx-runtime");
const react_1 = require("react");
const react_router_dom_1 = require("react-router-dom");
const axios_1 = __importDefault(require("axios"));
function Signup() {
    const navigate = (0, react_router_dom_1.useNavigate)();
    // 로딩 상태 관리
    const [loading, setLoading] = (0, react_1.useState)(true);
    // 사용할 상태변수 초기화.
    const [id, setId] = (0, react_1.useState)("");
    const [pw, setPw] = (0, react_1.useState)("");
    const [confirmPw, setConfirmPw] = (0, react_1.useState)("");
    const [name, setName] = (0, react_1.useState)("");
    const [addr, setAddr] = (0, react_1.useState)("");
    // 사용할 메세지 상태변수 초기화.
    const [idMsg, setIdMsg] = (0, react_1.useState)("");
    const [pwMsg, setPwMsg] = (0, react_1.useState)("");
    const [confirmMsg, setConfirmMsg] = (0, react_1.useState)("");
    const [nameMsg, setNameMsg] = (0, react_1.useState)("");
    const [addrMsg, setAddrMsg] = (0, react_1.useState)("");
    // 유효성 상태변수 초기화.
    const [isId, setIsId] = (0, react_1.useState)(false);
    const [isPw, setIsPw] = (0, react_1.useState)(false);
    const [isConfirm, setIsConfirm] = (0, react_1.useState)(false);
    const [isName, setIsName] = (0, react_1.useState)(false);
    const [isAddr, setIsAddr] = (0, react_1.useState)(false);
    const blurIdHandler = (event) => {
        const currentId = event.currentTarget.value;
        if (currentId !== '' && currentId !== null) {
            // 아이디 중복확인 true = 중복되지 않음, false = 중복됨.
            const fetchIdCheck = async () => {
                await axios_1.default.get(`http://localhost:8080/api/user/${id}`)
                    .then(res => {
                    if (res.data.data !== null) {
                        setIdMsg("이미 사용중인 아이디 입니다. 새롭게 다시 입력해 주세요.");
                        setIsId(false);
                    }
                })
                    .catch(error => {
                    console.log('Error fetching data:', error);
                })
                    .finally(() => {
                    setLoading(false);
                });
            };
            fetchIdCheck();
        }
    };
    const onIdHandler = (event) => {
        const currentId = event.currentTarget.value;
        setId(currentId);
        const idRegExp = /^[a-zA-Z0-9]{4,12}$/;
        if (idRegExp.test(currentId)) {
            setIdMsg("사용가능한 아이디 입니다.");
            setIsId(true);
        }
        else {
            setIdMsg("4~12자리 대소문자 또는 숫자만 입력할 수 있습니다.");
            setIsId(false);
        }
    };
    const onPwHandler = (event) => {
        const currentPw = event.currentTarget.value;
        setPw(currentPw);
        const pwRegExp = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,25}$/;
        if (pwRegExp.test(currentPw)) {
            setPwMsg("사용가능한 비밀번호 입니다.");
            setIsPw(true);
        }
        else {
            setPwMsg("숫자+영문자+특수문자 조합으로 8자리 이상 입력해주세요!");
            setIsPw(false);
        }
    };
    const onConfirmPwHandler = (event) => {
        const currentConfirmPw = event.currentTarget.value;
        setConfirmPw(currentConfirmPw);
        if (isPw && pw === currentConfirmPw) {
            setConfirmMsg("비밀번호와 동일합니다.");
            setIsConfirm(true);
        }
        else {
            setConfirmMsg("비밀번호와 일치하지 않습니다. 재입력 해주세요.");
            setIsConfirm(false);
        }
    };
    const onNameHandler = (event) => {
        const currentName = event.currentTarget.value;
        setName(currentName);
        if (currentName.length > 2 && currentName.length < 6) {
            setNameMsg(`${currentName} 님, 안녕하세요.`);
            setIsName(true);
        }
        else {
            setNameMsg("이름은 2글자 이상, 5글자 이하여야 합니다. 제 맘 입니다.");
            setIsName(false);
        }
    };
    const onAddrHandler = (event) => {
        const currentAddr = event.currentTarget.value;
        setAddr(currentAddr);
        if (currentAddr.length > 3) {
            setAddrMsg("일단은 뭔가를 적긴 했군요. 합격입니다.");
            setIsAddr(true);
        }
        else {
            setAddrMsg("주소가 없거나 너무 짧네요. 다시 적으세요. 당장.");
            setIsAddr(false);
        }
    };
    const onSubmitHandler = (event) => {
        event.preventDefault();
        if (isId && isPw && isConfirm && isName && isAddr) {
            let reqData = {
                id: id,
                pw: pw,
                name: name,
                addr: addr
            };
            const fetchSignup = async () => {
                await axios_1.default.post('http://localhost:8080/api/user/signup', reqData)
                    .then(res => {
                    if (res.data.statCode === 'success') {
                        console.log("if inner : " + res);
                        navigate('/');
                    }
                    console.log(res);
                })
                    .catch(err => {
                    console.log('Error fetching data:', err);
                })
                    .finally(() => {
                    setLoading(false);
                });
            };
            fetchSignup();
        }
        else {
            alert("회원가입 양식이 잘못되었습니다. 확인해주세요.");
        }
    };
    return ((0, jsx_runtime_1.jsxs)("form", { onSubmit: onSubmitHandler, children: [(0, jsx_runtime_1.jsx)("label", { htmlFor: 'id', children: "\uC544\uC774\uB514 " }), (0, jsx_runtime_1.jsx)("input", { onChange: onIdHandler, onBlur: blurIdHandler, type: 'text', id: 'id', name: 'id', value: id, placeholder: '아이디 입력', maxLength: 15 }), (0, jsx_runtime_1.jsx)("br", {}), idMsg && (0, jsx_runtime_1.jsx)("small", { style: { color: isId ? "green" : "red" }, children: idMsg }), (0, jsx_runtime_1.jsx)("br", {}), (0, jsx_runtime_1.jsx)("p", {}), (0, jsx_runtime_1.jsx)("label", { htmlFor: 'pw', children: "\uBE44\uBC00\uBC88\uD638 " }), (0, jsx_runtime_1.jsx)("input", { onChange: onPwHandler, type: 'password', id: 'pw', name: 'pw', value: pw, placeholder: '비밀번호 입력', maxLength: 24 }), (0, jsx_runtime_1.jsx)("br", {}), pwMsg && (0, jsx_runtime_1.jsx)("small", { style: { color: isPw ? "green" : "red" }, children: pwMsg }), (0, jsx_runtime_1.jsx)("br", {}), (0, jsx_runtime_1.jsx)("p", {}), (0, jsx_runtime_1.jsx)("label", { htmlFor: 'confirmPw', children: "\uBE44\uBC00\uBC88\uD638 \uD655\uC778 " }), (0, jsx_runtime_1.jsx)("input", { onChange: onConfirmPwHandler, type: 'password', id: 'confirmPw', name: 'confirmPw', value: confirmPw, placeholder: '비밀번호 재입력', maxLength: 24 }), (0, jsx_runtime_1.jsx)("br", {}), confirmMsg && (0, jsx_runtime_1.jsx)("small", { style: { color: isConfirm ? "green" : "red" }, children: confirmMsg }), (0, jsx_runtime_1.jsx)("br", {}), (0, jsx_runtime_1.jsx)("p", {}), (0, jsx_runtime_1.jsx)("label", { htmlFor: 'name', children: "\uC774\uB984 " }), (0, jsx_runtime_1.jsx)("input", { onChange: onNameHandler, type: 'name', id: 'name', name: 'name', value: name, placeholder: '이름 입력', maxLength: 6 }), (0, jsx_runtime_1.jsx)("br", {}), nameMsg && (0, jsx_runtime_1.jsx)("small", { style: { color: isName ? "green" : "red" }, children: nameMsg }), (0, jsx_runtime_1.jsx)("br", {}), (0, jsx_runtime_1.jsx)("p", {}), (0, jsx_runtime_1.jsx)("label", { htmlFor: 'addr', children: "\uC8FC\uC18C " }), (0, jsx_runtime_1.jsx)("input", { onChange: onAddrHandler, type: 'text', id: 'addr', name: 'addr', value: addr, placeholder: '주소를 적어주세요.' }), (0, jsx_runtime_1.jsx)("br", {}), addrMsg && (0, jsx_runtime_1.jsx)("small", { style: { color: isAddr ? "green" : "red" }, children: addrMsg }), (0, jsx_runtime_1.jsx)("br", {}), (0, jsx_runtime_1.jsx)("p", {}), (0, jsx_runtime_1.jsx)("button", { type: 'submit', disabled: !(isId && isPw && isConfirm && isName), children: "\uAC00\uC785\uD558\uAE30" })] }));
}
exports.default = Signup;
