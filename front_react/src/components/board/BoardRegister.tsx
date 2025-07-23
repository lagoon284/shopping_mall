import React, {useEffect, useState} from "react";
import { useNavigate } from 'react-router-dom';
import axios from "axios";

import {BoardInsFormDataType} from "../../interfaces/BoardInterface";
import {PropsType} from "../../interfaces/PropsInterface";

export default function BoardRegister(props: PropsType) {
    const navigate = useNavigate();

    // 로딩상태 관리
    const [ loading, setLoading ] = useState<boolean>(false);

    const [ formData, setFormData ] = useState<BoardInsFormDataType>({
        // 사용할 상태변수 초기화.
        title: "", content: "", writer: "", writerId: props.propLoginInfo.id,

        // 사용할 메세지 상태변수 초기화.
        titleMsg: "", contentMsg: "", writerMsg: "",

        // 유효성 상태변수 초기화.
        isTitle: false, isContent: false, isWriter: false,

        // 기타 적용 사항.
        isNotice: false, isSecret: false, isDraft: false,

        // 기타 상태 표시.
        status: 0, parentId: 0, depth: 0,

        // 첨부파일 및 이미지파일
        attachments: [], images: []
    });

    useEffect(() => {
        if(!props.propLoginInfo.id) {
            alert("로그인이 필요한 작업입니다.");
            navigate("/login");
        }
    }, [props]);

    if (loading) {
        return (
            <div className={'loading'}>
                <h1>로딩 중 입니다. 기다리세요. </h1>
            </div>
        )
    }

    return (
        <>
            <form /*onSubmit={(event) => onSumithandler(event)}*/>
                <label htmlFor={'title'}>게시글 제목</label>
                <input // onChange={}
                       // onBlur={}
                       type={'text'}
                       id={'title'}
                       value={formData.title}
                       placeholder={'게시글 제목을 적어주세요.'}
                       maxLength={50}
                />
                <br/>
                {/* idMsg 값이 있어야만(true)(false한 값이라면 태그는 렌더링 되지 않음.) && 를 기준으로 오른쪽 값을 반환함. */}
                {formData.titleMsg &&
                    <small style={{color: formData.isTitle ? "green" : "red"}}>{formData.title}</small>}<br/>
                <p/>

                <label htmlFor={'writer'}>작성자</label>
                <input // onBlur={}
                       type={'text'}
                       id={'writer'}
                       value={formData.writer}
                       placeholder={'작성자 닉네임을 적어주세요.'}
                       maxLength={20}
                />
                <br/>
                {/* idMsg 값이 있어야만(true)(false한 값이라면 태그는 렌더링 되지 않음.) && 를 기준으로 오른쪽 값을 반환함. */}
                {formData.writerMsg &&
                    <small style={{color: formData.isWriter ? "green" : "red"}}>{formData.writerMsg}</small>}<br/>
                <p/>

                <label htmlFor={'attachments'}>첨부파일</label>
                <input // onChange={}
                       type={'file'}
                       id={'attachments'}
                       placeholder={'첨부파일을 올려주세요'}
                />
                <br/>
                <p/>

                <label htmlFor={'content'}>내용</label>
                <textarea // onBlur={}
                          id={'content'}
                          name={'content'}
                          value={formData.content}
                          placeholder={'게시글 내용을 적어주세요.'}
                />
                <br/>
                {formData.contentMsg &&
                    <small style={{color: formData.isContent ? "green" : "red"}}>{formData.contentMsg}</small>}<br/>
                <p/>

            </form>
        </>
    )
}