import React, { useEffect, useState } from "react";
import axios from "axios";
import {useParams} from "react-router-dom";

import { ProdDetailType } from "../../interfaces/ProdInterface";

function ProductDetail() {
    const [ prod, setProd ] = useState<ProdDetailType | null >(null);

    const { prodSeqNo } = useParams();
    // 숫자로 변환.
    const seqNo: number = Number(prodSeqNo);

    // 로딩 상태 관리
    const [ loading, setLoading ] = useState<boolean>(true);
    // error 핸들러
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        if (seqNo <= 0) return;
        const fetchProdInfo = async (seqNo: number) => {
            setLoading(true);
            setError(null);
            try {
                const res = await axios.get(`http://localhost:8080/api/product/prodNo/${seqNo}`);
                setProd(res.data.data);
            } catch (err) {
                setError("정보를 불러오는 중 에러가 발생했습니다.");
            } finally {
                setLoading(false);
            }
        };
        fetchProdInfo(seqNo);
    }, [prodSeqNo]);

    if (loading) {
        return (
            <div className={'Loading'}>
                <h1>로딩 중 입니다. 기다리세요. </h1>
            </div>
        )
    }

    if (error) {
        return (
            <>
                <h2 className={"section-title"}>상품상세 정보</h2>
                <div className={"divider"} />
                <h3>{error}</h3>
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