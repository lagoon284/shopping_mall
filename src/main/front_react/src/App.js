import {useEffect, useState} from "react";
import axios from "axios";

function App() {
  const [hello, setHello] = useState('');

  useEffect(() => {
    axios.get('http://localhost:8080/api/user/allUserSelect')
        .then(res => {
          console.log(res.data);
          setHello(res.data);
        })
        .catch(error => {
            console.log('Error fetching data:', error);
        })

  }, []);


  return (
      <div className="App">
          hello, react!!<br/>
          <p></p>
          회원데이터 : {JSON.stringify(hello)}
      </div>
  );
}

export default App;
