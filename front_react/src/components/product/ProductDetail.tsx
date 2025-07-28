import React, { useEffect, useState } from "react";
import axios from "axios";
import {useParams} from "react-router-dom";

import { CommonType } from "../../interfaces/CommonInterface";
import { ProdDetailType } from "../../interfaces/ProdInterface";

function ProductDetail() {
    const { prodSeqNo } = useParams();

    const [ prod, setProd ] = useState<ProdDetailType | null >(null);

    // 로딩 상태 관리
    const [ commonStat, setCommonStat ] = useState<CommonType>({
        loading: true,
        error: ""
    });

    useEffect(() => {
        if (Number(prodSeqNo) <= 0) return;
        const fetchProdInfo = async (seqNo: number) => {
            try {
                const res = await axios.get(`http://localhost:8080/api/product/prodNo/${seqNo}`);
                setProd(res.data.data);
            } catch (err) {
                setCommonStat(prev => ({
                    ...prev,
                    error: "정보를 불러오는 중 에러가 발생했습니다. : " + err
                }));
            } finally {
                setCommonStat(prev => ({
                    ...prev,
                    loading: false
                }));
            }
        };
        fetchProdInfo(Number(prodSeqNo));
    }, [prodSeqNo]);

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
                <h2 className={"section-title"}>상품상세 정보</h2>
                <div className={"divider"} />
                <h3>{commonStat.error}</h3>
            </>
        );
    }

    if (!prod) {
        return (
            <>
                <h2 className={"section-title"}>상품상세 정보</h2>
                <div className={"divider"}/>
                <h2>해당 상품은 존재하지 않습니다.</h2>
            </>
        )
    }

    return (
        <>
            <h2 className={"section-title"}>상품상세 정보</h2>
            <div className={"divider"}/>
            상품 번호 : {prod.prodSeqNo}<br/>
            상품이름 : {prod.prodName}<br/>
            상품가격 : {prod.price}<br/>
            판매사 : {prod.provider}<br/>
            상품 정보 : {prod.info}
        </>
    );
}

export default ProductDetail;