import {useEffect, useState} from "react";
import axios from "axios";
import {Link} from "react-router-dom";

function ProductInfos() {
    const [prods, setProds] = useState('');

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

            <Link to={`/api/product/${2}`}>2번 상품만 보기</Link>
            <p></p>
            상품데이터 :
            <pre>{JSON.stringify(prods, null, 4)}</pre>
        </div>
    );
}

export default ProductInfos;