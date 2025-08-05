// axios 인스턴스 설정 파일 (e.g., api.js)
import axios, {AxiosError} from 'axios';

const ApiClient = axios.create({
    baseURL: 'http://localhost:8080/api', // 백엔드 주소
});

// 응답 인터셉터 설정
export const setupAxiosInterceptors = (onNewToken: (token: string) => void) => {
    ApiClient.interceptors.response.use(
        (response) => {
            // 응답 헤더에 새 토큰이 있는지 확인
            const newAccessToken = response.headers['new-access-token'];
            console.log('헤더 받았냐? : ', newAccessToken);

            if (newAccessToken) {
                console.log('새로운 Access Token을 발급받았습니다. 저장합니다.');
                // localStorage에 새 토큰 저장
                onNewToken(newAccessToken);
            }

            return response;
        },
        (error: AxiosError) => {
            // if (error.response?.status === 401) {
            //     console.log('setupAxiosInterceptors 로그아웃');
            //     onForceLogout();     매개변수로 로그아웃 줘야함.
            // }

            return Promise.reject(error);
        }
    );
}

// 요청 인터셉터도 추가 (localStorage에서 토큰 꺼내서 헤더에 넣기)
/*ApiClient.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem('seokho_jwt');

        if (token && token !== 'null') {
            config.headers['Authorization'] = `seokhoAccAuth ${token}`;
        }

        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);*/

export default ApiClient;