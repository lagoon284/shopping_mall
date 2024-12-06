import React, { useEffect, useState } from "react";
import axios from "axios";

function UserInfo() {
    const [users, setUsers] = useState('');

    useEffect(() => {
        axios.get('http://localhost:8080/api/user/allUserSelect')
            .then(res => {
                console.log(res.data.data);
                setUsers(res.data.data);
            })
            .catch(error => {
                console.log('Error fetching data:', error);
            })
    }, []);

    return (
        <div className="UserInfo">
            <h2>회원 정보</h2>
            회원데이터 :
            <pre>{JSON.stringify(users, null, 4)}</pre>
        </div>
    );
}

export default UserInfo;