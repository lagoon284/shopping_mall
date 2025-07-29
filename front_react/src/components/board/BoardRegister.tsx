import React, {useEffect, useState} from "react";
import { useNavigate } from 'react-router-dom';
import axios from "axios";

import {CommonType} from "../../interfaces/CommonInterface";
import {BoardInsFormDataType} from "../../interfaces/BoardInterface";
import {PropsType} from "../../interfaces/PropsInterface";

export default function BoardRegister(props: PropsType) {
    const navigate = useNavigate();

    // 로딩상태 관리
    const [ commonStat, setCommonStat ] = useState<CommonType>({
        loading: true,
        error: ""
    })

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
        attachments: null, images: null
    });

    const onChangeEventHandler = (event : React.ChangeEvent<HTMLInputElement> | React.ChangeEvent<HTMLTextAreaElement>, strVal: string) => {
        const currentVal: string = event.currentTarget.value;

        // updateFormData 매개변수 초기화.
        let key : string = "";
        let value : string | number | boolean = "";
        let msgKey : string = "";
        let msg : string = "";
        let flagKey : string = "";
        let flag : boolean = false;


        if (strVal === 'title') {
            key = "title";
            value = currentVal;
            msgKey = "titleMsg";
            msg = "제목을 입력해주세요.";
            flagKey = "isTitle";
            flag = (currentVal !== null && currentVal !== "");
        }

        if (strVal === 'writer') {
            key = "writer";
            value = currentVal;
            msgKey = "writerMsg";
            msg = "작성자 이름(닉네임)을 입력해주세요.";
            flagKey = "isWriter";
            flag = (currentVal !== null && currentVal !== "");
        }

        if (strVal === 'content') {
            key = "content";
            value = currentVal;
            msgKey = "contentMsg";
            msg = "글 본문(내용)을 입력해주세요.";
            flagKey = "isContent";
            flag = (currentVal !== null && currentVal !== "");
        }

        updateFormData(key, value, msgKey, msg, flagKey, flag);
    }

    const updateFormData = (key : string, value : string | number | boolean, msgKey : string, msg : string, flagKey : string, flag : boolean) => {
        setFormData(prev => ({
            ...prev,
            [key] : value,
            [msgKey] : msg,
            [flagKey] : flag
        }))
    }

    const onchangeFileHandler = (event: React.ChangeEvent<HTMLInputElement>) => {
        const files = event.target.files;
        if (files && files.length > 0) {
            setFormData((prev) => ({
                ...prev,
                attachments: Array.from(files)
            }))
        } else {
            setFormData((prev) => ({
                ...prev,
                attachments: null
            }))
        }
    }

    const onSubmitHandler = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();

        if (formData.isTitle && formData.isWriter && formData.isContent) {
            const reqData = {
                title: formData.title,
                content: formData.content,
                writer: formData.writer,
                writerId: formData.writerId
            }

            const fetchRegBoard = async () => {
                await axios.post('http://localhost:8080/api/board/insert', reqData)
                    .then(res => {
                        if (res.data.statCode === 'success') {
                            // console.log("if inner : " + res.data.statCode);
                            alert("글 등록이 완료되었습니다. 게시판 화면으로 이동합니다.");
                            navigate('/board/getList');
                        }
                    })
                    .catch(err => {
                        console.log('Error fetching data:', err);
                        setCommonStat(prev => ({
                            ...prev,
                            error: 'Error fetching data : ' + err
                        }))
                    })
            }

            fetchRegBoard();
        } else {
            alert("입력된 정보를 재확인해주시기 바랍니다.");
        }
    }

    useEffect(() => {
        if(!props.propLoginInfo.id || props.propLoginInfo.id === "") {
            alert("로그인이 필요한 작업입니다.");
            props.setUserInfo(null);
            navigate("/login");
        }

        setCommonStat((Prev) => ({
            ...Prev,
            loading: false
        }))
    }, [props.propLoginInfo.id]);

    if (commonStat.loading) {
        return (
            <div className={'loading'}>
                <h1>로딩 중 입니다. 기다리세요. </h1>
            </div>
        )
    }

    if (commonStat.error) {
        return (
            <div className={'Error'}>
                <h1>error message : {commonStat.error}</h1>
            </div>
        )
    }

    return (
        <>
            <form onSubmit={(event) => onSubmitHandler(event)}>
                <label htmlFor={'title'}>게시글 제목</label>
                <input onChange={(event) => onChangeEventHandler(event, 'title')}
                    // onBlur={}
                       type={'text'}
                       id={'title'}
                       value={formData.title}
                       placeholder={'게시글 제목을 적어주세요.'}
                       maxLength={50}
                />
                <br/>
                {/* idMsg 값이 있어야만(true)(false한 값이라면 태그는 렌더링 되지 않음.) && 를 기준으로 오른쪽 값을 반환함. */}
                {!formData.isTitle &&
                    <small style={{color: formData.isTitle ? "green" : "red"}}>{formData.titleMsg}</small>}<br/>
                <p/>

                <label htmlFor={'writer'}>작성자</label>
                <input onChange={(event) => onChangeEventHandler(event, 'writer')}
                       type={'text'}
                       id={'writer'}
                       value={formData.writer}
                       placeholder={'작성자 닉네임을 적어주세요.'}
                       maxLength={20}
                />
                <br/>
                {/* idMsg 값이 있어야만(true)(false한 값이라면 태그는 렌더링 되지 않음.) && 를 기준으로 오른쪽 값을 반환함. */}
                {!formData.isWriter &&
                    <small style={{color: formData.isWriter ? "green" : "red"}}>{formData.writerMsg}</small>}<br/>
                <p/>

                <label htmlFor={'attachments'}>첨부파일</label>
                <input onChange={(event) => onchangeFileHandler(event)}
                       type={'file'}
                       id={'attachments'}
                       placeholder={'첨부파일을 올려주세요'}
                />
                <br/>
                <p/>

                <label htmlFor={'content'}>내용</label>
                <textarea onChange={(event) => onChangeEventHandler(event, 'content')}
                          id={'content'}
                          name={'content'}
                          value={formData.content}
                          placeholder={'게시글 내용을 적어주세요.'}
                />
                <br/>
                {!formData.isContent &&
                    <small style={{color: formData.isContent ? "green" : "red"}}>{formData.contentMsg}</small>}<br/>
                <p/>
                <button type={'submit'}
                        disabled={!(formData.isTitle && formData.isWriter && formData.isContent)}>등록하기
                </button>

            </form>
        </>
    )
}