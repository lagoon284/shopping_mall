import React, { useEffect, useState } from "react";
import axios, {get} from "axios";
import {useLocation, useParams} from "react-router-dom";

function ProductInfo() {
    const [ prod, setProd ] = useState('');

    const { prodSeqNo } = useParams();

    useEffect(() => {
        axios.get(`http://localhost:8080/api/product/${prodSeqNo}`)
            .then(res => {
                setProd(res.data.data);
            })
            .catch(error => {
                console.log('Error fetching data:', error);
            })
    }, []);

    return (
        <div className={"ProductInfo"}>
            <h2>상품상세 정보</h2>
            상품 번호 : {prod.prodSeqNo}<br/>
            상품이름 : {prod.prodName}<br/>
            상품가격 : {prod.price}<br/>
            판매사 : {prod.provider}<br/>
            상품 정보 : {prod.info}
        </div>
    );
}

export default ProductInfo;