import React, {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import axios from "axios";

import {BoardDetailType} from "../../interfaces/BoardInterface";

export default function BoardDetail() {
    // 객체 데이터
    const [ board, setBoard ] = useState<BoardDetailType | null>(null);

    // seqNo 받기
    const { seqNo } = useParams<{ seqNo: string }>();

    console.log(seqNo);
    // 숫자로 변환
    // const seqNo = Number(boardSeqNo);

    // 로딩 상태 관리
    const [ loading, setLoading ] = useState<boolean>(true);

    // error 핸들러
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        if(!seqNo) return;

        const fetchBoardInfo = async (seqNo: string) => {
            setLoading(true);
            setError(null);

            try {
                const res = await axios.get(`http://localhost:8080/api/board/seq/${seqNo}`);
                setBoard(res.data.data);
            } catch (err) {
                setError("정보를 불러오는 중 에러가 발생했습니다.");
            } finally {
                setLoading(false);
            }
        };
        fetchBoardInfo(seqNo);
    }, [seqNo]);

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
                <h2 className={"section-title"}>게시글 상세 정보</h2>
                <div className={"divider"} />
                <h3>{error}</h3>
            </>
        );
    }

    if (!board) {
        return (
            <>
                <h2 className={"section-title"}>게시글 상세 정보</h2>
                <div className={"divider"}/>
                <h3>해당 게시글은 존재하지 않습니다.</h3>
            </>
        )
    }

    return (
        <>
            <h2 className={"section-title"}>게시글 상세 정보</h2>
            <div className={"divider"}/>
            글 번호 : {board.seqNo}<br/>
            제목 : {board.title}<br/>
            내용 : {board.content}<br/>
            작성자 : {board.writer}<br/>
            조회수 : {board.viewCnt}<br/>
            작성날짜 : {board.updDate ? (<> {board.updDate} <small> (수정됨)</small> </>) : (board.regDate)}
        </>
    );
}