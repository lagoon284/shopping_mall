import React, {useEffect, useState} from "react";
import axios from "axios";
import {Link} from "react-router-dom";

function ProductInfos() {
    const [prods, setProds] = useState([]);

    useEffect(() => {
        axios.get('http://localhost:8080/api/product/infoProds')
            .then(res => {
                console.log(res.data.data);
                setProds(res.data.data);
            })
            .catch(error => {
                console.log('Error fetching data:', error);
            })
    }, []);

    return (
        <div className="ProductInfos">
            <h2>상품 정보</h2>

            <p></p>
            <h3>상품데이터 :</h3>
            <ul>
            {prods.map((prod, index) => {
                return (
                    <li key={prod.prodSeqNo}>
                        <Link to={`/api/product/${prod.prodSeqNo}`}>{prod.prodSeqNo}번 상품 상세보기!!</Link><br/>
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