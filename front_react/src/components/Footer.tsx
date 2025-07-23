import {Link, useNavigate} from "react-router-dom";

export default function Footer() {
    // const navigate = useNavigate();

    return(
        <>
            <footer>
                <div>

                </div>
                <div style={{display: 'flex', justifyContent: 'space-between'}}>
                    <span>Email: tjrgh1016@gmail.com</span>
                    <span>GitHub: <a href="https://github.com/lagoon284">github.com/lagoon284</a></span>
                </div>
                <div style={{textAlign: "center"}}>
                    <Link to={"/"}>
                        Home
                    </Link>
                    &nbsp; | &nbsp;
                    <a href="https://github.com/lagoon284/shopping_mall">Source Code</a>
                </div>
                <p/>
                <div style={{textAlign: "center"}}>
                    © 2025 Seokho Choi
                    &nbsp; | &nbsp;
                    Shopping Mall (개인 개발 프로젝트)
                    &nbsp; | &nbsp;
                    Powered by 'React + TypeScript'
                    &nbsp; | &nbsp;
                    All rights reversed. (Copy 쌉가능)
                </div>
            </footer>
        </>
    );
}