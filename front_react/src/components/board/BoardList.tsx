import React, {useEffect, useState} from "react";
import axios from "axios";
import {Link, useNavigate} from "react-router-dom";

import {CommonType} from "../../interfaces/CommonInterface";
import { BoardListType } from "../../interfaces/BoardInterface";

function BoardList() {
    const navigate = useNavigate();

    const [boards, setBoards] = useState<BoardListType[]>([]);

    // 로딩 상태 관리
    const [ commonStat, setCommonStat ] = useState<CommonType>({
        loading: true,
        error: ""
    });

    useEffect(() => {
        const fetchBoardList = async () => {
            await axios.get('http://localhost:8080/api/board/getList')
                .then(res => {
                    setBoards(res.data.data);
                })
                .catch(error => {
                    console.log('Error fetching data:', error);
                    setCommonStat(prev => ({
                        ...prev,
                        error: "데이터를 불러오는 중 오류가 발생했습니다: " + error.message
                    }));
                })
                .finally(() => {
                    setCommonStat(prev => ({
                        ...prev,
                        loading: false
                    }));
                });
        };
        fetchBoardList();
    }, []);

    function handleRowClick(seqNo: number) {
        navigate(`/board/${seqNo}`);
    }

    if (commonStat.loading) {
        return (
            <>
                <div className="container">
                    <header className="page-header">
                        <h1>게시판</h1>
                    </header>
                    <div className="section">
                        <p>데이터를 불러오는 중입니다...</p>
                    </div>
                </div>
            </>
        );
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
                        <h1>게시판</h1>
                        <p>전체 게시글 목록을 관리합니다.</p>
                    </div>
                    {/* 게시글 등록 버튼을 헤더 오른쪽으로 이동 */}
                    <Link to={"/board/boardReg"} className="btn btn-primary">
                        게시글 등록
                    </Link>
                </header>

                <div className="section">
                    <table>
                        <thead>
                        <tr>
                            <th>번호</th>
                            <th>제목</th>
                            <th>작성자</th>
                            <th>조회수</th>
                            <th>작성일</th>
                        </tr>
                        </thead>
                        <tbody>
                        {/* 게시글이 없을 경우 */}
                        {boards.length === 0 ? (
                            <tr>
                                {/* colSpan을 실제 컬럼 수에 맞게 수정 */}
                                <td colSpan={5} className="text-center">게시글이 없습니다.</td>
                            </tr>
                        ) : (
                            // boards 배열을 map으로 돌려 각 게시글을 행(tr)으로 렌더링
                            boards.map((board) => (
                                <tr className="link-tr"
                                    onClick={() => handleRowClick(board.seqNo)}
                                    key={board.seqNo}>
                                    <td className="text-center">{board.seqNo}</td>
                                    <td>
                                        {board.title}
                                        {/* 댓글 수가 0보다 크면 댓글 수를 함께 표시 */}
                                        {board.commentCnt > 0 &&
                                            <span className="comment-count"> [{board.commentCnt}]</span>}
                                    </td>
                                    <td>{board.writer}</td>
                                    <td className="text-center">{board.viewCnt}</td>
                                    <td className="text-center">{board.updDate ? board.updDate : board.regDate}</td>
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

export default BoardList;