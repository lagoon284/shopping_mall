import React, { useEffect, useState } from "react";
import axios from "axios";
import {useParams} from "react-router-dom";

import { ProdInfoType } from "../../TypeInterface";

function ProductInfo() {
    const [ prod, setProd ] = useState<ProdInfoType | null >(null);

    const { prodSeqNo } = useParams();

    // 로딩 상태 관리
    const [ loading, setLoading ] = useState<boolean>(true);

    useEffect(() => {
        axios.get(`http://localhost:8080/api/product/${prodSeqNo}`)
            .then(res => {
                setProd(res.data.data);
            })
            .catch(error => {
                console.log('Error fetching data:', error);
            })
            .finally(() => {
                setLoading(false);
            })
    }, [prodSeqNo]);

    if (loading) {
        return (
            <div className={'loading'}>
                <h1>로딩 중 입니다. 기다리세요. </h1>
            </div>
        )
    }

    if (prod) {
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

    return (
        <div className={"ProductInfo"}>
            <h2>해당 상품은 존재하지 않습니다.</h2>
        </div>
    )

}

export default ProductInfo;