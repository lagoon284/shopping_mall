import React, {useEffect, useState} from "react";
import axios from "axios";
import {Link} from "react-router-dom";

import { ProdInfoType } from "../../TypeInterface";

function ProductInfos() {
    const [prods, setProds] = useState<ProdInfoType[]>([]);

    // 로딩 상태 관리
    const [ loading, setLoading ] = useState<boolean>(true);

    useEffect(() => {
        axios.get('http://localhost:8080/api/product/infoProds')
            .then(res => {
                // console.log(res.data.data);
                setProds(res.data.data);
            })
            .catch(error => {
                console.log('Error fetching data:', error);
            })
            .finally(() => {
                setLoading(false);
            })
    }, []);

    if (loading) {
        return (
            <div className={'Loading'}>
                <h1>로딩 중 입니다. 기다리세요. </h1>
            </div>
        )
    }

    return (
        <div className="ProductInfos">
            <h2>상품 정보</h2>

            <p></p>
            <h3>상품데이터 :</h3>
            <ul>
            {prods.map((prod) => {
                return (
                    <li key={prod.prodSeqNo}>
                        <Link to={`/product/${prod.prodSeqNo}`}>{prod.prodSeqNo}번 상품 상세보기!!</Link><br/>
                        상품 번호 : {prod.prodSeqNo}<br/>
                        상품이름 : {prod.prodName}<br/>
                        상품가격 : {prod.price}<br/>
                        판매사 : {prod.provider}<br/>
                        상품 정보 : {prod.info}
                        <p></p>
                    </li>
                );
            })}
            </ul>
        </div>
    );
}

export default ProductInfos;