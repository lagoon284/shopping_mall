import React, {useEffect, useState} from "react";
import axios from "axios";
import {Link} from "react-router-dom";

import { ProdInfoType } from "../../interfaces/ProdInterface";

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
        <div className="section">
            <h2 className={"section-title"}>상품 정보</h2>
            <div className={"divider"} />
            <h3>상품데이터 :</h3>
            <button style={{ marginLeft: "auto", display: "block" }}>
                <Link to={"/product/insert"}>상품등록</Link>
            </button>
            <div className={"grid"}>
                {prods && prods.map((prod) => {
                    return (
                        <Link to={`/product/${prod.prodSeqNo}`}>
                        <div className={"card"} key={prod.prodSeqNo}>
                            상품 번호 : {prod.prodSeqNo}<br/>
                            상품이름 : {prod.prodName}<br/>
                            상품가격 : {prod.price}<br/>
                            판매사 : {prod.provider}<br/>
                            상품 정보 : {prod.info}
                        </div>
                        </Link>
                    );
                })}
            </div>
        </div>
    );
}

export default ProductInfos;