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
            {prods.map((el, index) => {
                return (
                    <li>
                        <Link to={`/api/product/${el.prodSeqNo}`}>{el.prodSeqNo}번 상품 상세보기!!</Link><br/>
                        상품 번호 : {el.prodSeqNo}<br/>
                        상품이름 : {el.prodName}<br/>
                        상품가격 : {el.price}<br/>
                        판매사 : {el.provider}<br/>
                        상품 정보 : {el.info}
                        <p></p>
                    </li>
                );
            })}
            </ul>
        </div>
    );
}

export default ProductInfos;