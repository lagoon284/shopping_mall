// import { createRoot } from 'react-dom/client';
//
// // 기존 HTML 컨텐츠를 지웁니다.
// document.body.innerHTML = '<div id="app"></div>';
//
// // 대신에 여러분이 작성한 React 컴포넌트를 렌더링합니다.
// const root = createRoot(document.getElementById('app'));
// root.render(<h1>Hello, world</h1>);


import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
