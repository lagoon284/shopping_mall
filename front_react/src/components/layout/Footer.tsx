import {Link} from "react-router-dom";

function Footer() {

    return(
        <>
            <footer className="footer">
                {/* 상단 링크 영역 */}
                <div className="footer-links">
                    <Link to={"/"}>Home</Link>
                    {/* 외부 링크는 target="_blank"를 추가해 새 탭에서 열리도록 합니다. */}
                    <a href="https://github.com/lagoon284" target="_blank" rel="noopener noreferrer">GitHub</a>
                    <a href="https://github.com/lagoon284/shopping_mall" target="_blank" rel="noopener noreferrer">Source Code</a>
                </div>

                {/* 하단 정보 영역 */}
                <div className="footer-info">
                    <p>
                        <span>Email: tjrgh1016@gmail.com</span>
                        <span>© 2025 Seokho Choi. All rights reversed. (Copy 쌉가능)</span>
                    </p>
                    <p>
                        Shopping Mall (개인 개발 프로젝트) &nbsp; | &nbsp; Powered by 'React + TypeScript'
                    </p>
                </div>
            </footer>
        </>
    );
}

export default Footer;