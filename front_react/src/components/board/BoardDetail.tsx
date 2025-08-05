import React, {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import axios from "axios";
import {useAuth} from "../../contexts/AuthContext";

import {CommonType} from "../../interfaces/CommonInterface";
import {BoardDetailType} from "../../interfaces/BoardInterface";

export default function BoardDetail() {
    const { user } = useAuth();
    // seqNo 받기
    const { seqNo } = useParams<{ seqNo: string | undefined }>();
    // 객체 데이터
    const [ board, setBoard ] = useState<BoardDetailType | null>(null);

    const [ commonStat, setCommonStat ] = useState<CommonType>({
        loading: true,
        error: ""
    });

    useEffect(() => {
        if(!seqNo || Number(seqNo) <= 0) return;

        const KEY = 'viewedPosts';
        // 조회 이력 로드
        const viewedPosts: string[] = (() => {
            try {
                return JSON.parse(localStorage.getItem(KEY) || '[]');
            } catch {
                return [];
            }
        })();

        const viewed = viewedPosts.includes(seqNo);

        // 게시글 정보 불러오는 함수
        const fetchBoardInfo = async (seqNo: number) => {
            try {
                const res = await axios.get(`http://localhost:8080/api/board/seq/${seqNo}`);
                setBoard(res.data.data);
                return res.data.data;
            } catch (err) {
                setCommonStat(prev => ({
                    ...prev,
                    error: "정보를 불러오는 중 에러가 발생했습니다."
                }));
            }
        };

        // 조회 이력 관리 소스.
        // LocalStorage 이용하여 본 게시글 ID 배열에 저장.
        // (단, 기기가 바뀌거나 사용자가 로컬스토리지를 지우면 다시 집계됨. 로그인 계정이 바뀌어도 같은 기기로는 1회만 집계)
        const viewedPostPlus = async (boardData: BoardDetailType ,seqNo: string)=> {
            try {
                // 조회수를 증가시키는 api
                const res = await axios.get(`http://localhost:8080/api/board/viewedPostPlus/${seqNo}`);

                if (res.data.statCode === 'success') {
                    // 로컬스토리지에 저장.
                    viewedPosts.push(seqNo);
                    localStorage.setItem(KEY, JSON.stringify(viewedPosts));

                    // 바로 setBoard (viewCnt만 수동 증가)
                    setBoard({
                        ...boardData,
                        viewCnt: boardData.viewCnt + 1
                    })
                    return;
                } /*else {
                    console.log(commonStat.error + res.data);
                }*/
            } catch (e) {
                console.log('조회수 증가 API 호출 중 에러:', e);
                setCommonStat(prev => ({
                    ...prev,
                    error: '조회수 증가 API 호출 중 에러:' + e
                }));
            } finally {
                setCommonStat(prev => ({
                    ...prev,
                    loading: false
                }))
            }
        }

        (async () => {
            const boardData = await fetchBoardInfo(Number(seqNo));

            if (!boardData) {
                setCommonStat(prev => ({
                    ...prev,
                    loading: false
                }))
                return;
            }
            const isOwner = user?.id === boardData.writerId;

            if (!isOwner && !viewed) {
                await viewedPostPlus(boardData, seqNo);
                setCommonStat(prev => ({
                    ...prev,
                    loading: false
                }))
                return;
            }

            setBoard(boardData);
            setCommonStat(prev => ({
                ...prev,
                loading: false
            }))
        })();
    }, [seqNo, user?.id]);

    if (commonStat.loading) {
        return (
            <div className={'Loading'}>
                <h1>로딩 중 입니다. 기다리세요. </h1>
            </div>
        )
    }

    if (commonStat.error) {
        return (
            <>
                <h2 className={"section-title"}>게시글 상세 정보</h2>
                <div className={"divider"} />
                <h3>{commonStat.error}</h3>
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