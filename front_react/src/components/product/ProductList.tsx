import React, {useEffect, useState} from "react";
import axios from "axios";
import {Link} from "react-router-dom";

import { CommonType } from "../../interfaces/CommonInterface";
import { ProdDetailType } from "../../interfaces/ProdInterface";

function ProductList() {
    const [prods, setProds] = useState<ProdDetailType[]>([]);

    // 로딩 상태 관리
    const [ commonStat, setCommonStat ] = useState<CommonType>({
        loading: true,
        error: ""
    });

    useEffect(() => {
        axios.get('http://localhost:8080/api/product/infoProds')
            .then(res => {
                setProds(res.data.data);
            })
            .catch(error => {
                console.log('Error fetching data:', error);
                setCommonStat(prev => ({
                    ...prev,
                    error: "정보를 불러오는 중 에러가 발생했습니다. : " + error
                }));
            })
            .finally(() => {
                setCommonStat(prev => ({
                    ...prev,
                    loading: false
                }));
            })
    }, []);

    if (commonStat.loading) {
        return (
            <div className={'Loading'}>
                <h1>로딩 중 입니다. 기다리세요. </h1>
            </div>
        )
    }

    if (commonStat.error) {
        return (
            <>
                <h2 className={"section-title"}>상품 정보</h2>
                <div className={"divider"} />
                <h3>{commonStat.error}</h3>
            </>
        );
    }

    return (
        <>
            <h2 className={"section-title"}>상품 정보</h2>
            <div className={"divider"} />
            <button style={{ marginLeft: "auto", display: "block" }}>
                <Link to={"/product/insert"}>상품등록</Link>
            </button>
            <div className={"grid"}>
                {prods && prods.map((prod) => {
                    return (
                        <Link to={`/product/${prod.prodSeqNo}`} key={prod.prodSeqNo}>
                            <div className={"card"}>
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
        </>
    );
}

export default ProductList;