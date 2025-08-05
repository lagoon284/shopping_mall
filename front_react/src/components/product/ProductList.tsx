import React, {useEffect, useState} from "react";
import ApiClient from "../util/ApiClient";
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
        const fetchProductInfo = async () => {
            await ApiClient.get('/auth/product/infoProds', {
            })
                .then(res => {
                    setProds(res.data.data);
                })
                .catch(error => {
                    if (error.status === 401) {
                        setCommonStat(prev => ({
                            ...prev,
                            error: '접근 권한이 없습니다.'
                        }))
                    } else {
                        console.log('Error fetching data:', error);
                        setCommonStat(prev => ({
                            ...prev,
                            error: '데이터를 불러오는 중 오류가 발생했습니다. : ' + error.message
                        }))
                    }
                })
                .finally(() => {
                    setCommonStat(prev => ({
                        ...prev,
                        loading: false
                    }));
                });
        };
        fetchProductInfo();
    }, []);

    if (commonStat.loading) {
        return (
            <>
                <div className="container">
                    <header className="page-header">
                        <h1>상품 정보</h1>
                    </header>
                    <div className="section">
                        <p>데이터를 불러오는 중입니다...</p>
                    </div>
                </div>
            </>
        )
    }

    if (commonStat.error) {
        return (
            <>
                <div className="container">
                    <header className="page-header">
                        <h1>오류 발생</h1>
                    </header>
                    <div className="section">
                        <p style={{color: 'red'}}>{commonStat.error}</p>
                    </div>
                </div>
            </>
        );
    }

    return (
        <>
            <div className="container">
                <header className="page-header flex-between">
                    <div>
                        <h1>상품 정보</h1>
                        <p>전체 상품 목록을 관리합니다.</p>
                    </div>
                    {/* 상품 등록 버튼을 헤더 오른쪽으로 이동시키고 스타일 적용 */}
                    <Link to={"/product/insert"} className="btn btn-primary">
                        상품 등록
                    </Link>
                </header>

                <div className="section">
                    <table>
                        <thead>
                        <tr>
                            <th>상품 번호</th>
                            <th>상품 이름</th>
                            <th>가격</th>
                            <th>판매사</th>
                            <th>관리</th>
                        </tr>
                        </thead>
                        <tbody>
                            {/* 상품이 없을 경우 메시지 표시 */}
                            {(!prods || prods.length === 0) ? (
                                <tr>
                                    <td colSpan={5} className="text-center">등록된 상품이 없습니다.</td>
                                </tr>
                            ) : (
                                // prods 배열을 map으로 돌려 각 상품을 행(tr)으로 렌더링
                                prods.map((prod) => (
                                    <tr key={prod.prodSeqNo}>
                                        <td className="text-center">{prod.prodSeqNo}</td>
                                        <td>{prod.prodName}</td>
                                        {/* 가격에 콤마를 추가하여 가독성 높임 */}
                                        <td className="text-right">{prod.price.toLocaleString()}원</td>
                                        <td>{prod.provider}</td>
                                        <td className="text-center">
                                            <Link to={`/product/${prod.prodSeqNo}`} className="btn btn-sm btn-secondary">
                                                상세
                                            </Link>
                                        </td>
                                    </tr>
                                ))
                            )}
                        </tbody>
                    </table>
                </div>
            </div>
        </>
    );
}

export default ProductList;