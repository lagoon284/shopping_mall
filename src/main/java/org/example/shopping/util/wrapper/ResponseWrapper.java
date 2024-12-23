package org.example.shopping.util.wrapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.io.JsonStringEncoder;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.shopping.util.api.ApiRes;
import org.springframework.boot.json.JsonParser;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.ParameterizedType;


// wrapper 클레스로 controller에서 리턴할때마다 감싸주는 친구.
@RestControllerAdvice(basePackages = "org.example.shopping" )         // 해당 경로에서 리턴하는 controller만 감싸줌.
@RequiredArgsConstructor                                                        // 필드의 의존성 주입을 위한 어노테이션.
public class ResponseWrapper implements ResponseBodyAdvice<Object> {

    private final ObjectMapper objectMapper;

    // wrapper 클래스로 감쌀지 말지 명확하게 결정하는 메소드.
    // ResponseEntity 타입 return 인지 / ApiRes 타입 return 인지 확인.
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {

        Class<?> type = returnType.getParameterType();      // 파라미터의 타입을 저장. 이후 대상 클레스들에서 상속, 구현 되었는지 비교하게 됨.

        // type이 ResponseEntity 클래스에서 상속/구현 되었는지? isAssignnableFrom은 instanceof와 비슷하지만 instanceof는 인스턴스 화 되었는지가 다름.
        if (ResponseEntity.class.isAssignableFrom(type)) {
            // ResponseEntity 타입일 경우, 실행.
            try {
                // ResponseEntity<T> 일때 T 타입이 뭔지 가져옴.
                ParameterizedType parameterizedType = (ParameterizedType) returnType.getGenericParameterType();
                type = (Class<?>) parameterizedType.getActualTypeArguments()[0];
            } catch (ClassCastException | ArrayIndexOutOfBoundsException ex) {
                return false;
            }
        }
        // type이 ApiRes 클래스에서 상속/구현 되었는지? / 해당 클래스로 변환이 가능한지??
        // return false가 되면 감싸지 않겠다는 의미.
        return !ApiRes.class.isAssignableFrom(type);
    }

    // 감싸줄때 어떨게 구성할지 짜는 메소드.
    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {

        // controller 경로를 넣어줄 예정으로 path 값 추가.
        String path = request.getURI().getPath();
        // 만든 커스텀 클래스로 빌더하기 위해 변수 생성.
        ApiRes<?> apiRes;

        // 컨트롤러의 리턴이 String 일 때, 문자열 그대로 리턴.
        if (body instanceof String) {
            return body;
        }

        if(returnType.getParameterType().equals(void.class)) {
            // return type 이 void 일 때,
            apiRes = ApiRes.builder()
                    .path(path)
                    .build();
        } else {
            // return type 이 void 가 아닐 때,
            apiRes = ApiRes.builder()
                    // controller 경로.
                    .path(path)
                    // return 하는 객체...?
                    .data(body)
                    .build();
        }

        return apiRes;

        // mapping json converter 가공을 거쳤는지? (그냥 String이라면 false, 객체로 받아 json으로 가공을 해준다면 true)
//        if (MappingJackson2HttpMessageConverter.class.isAssignableFrom(selectedConverterType)) {
//            return apiRes;
//        }

        // "success" 를 리턴하기위해 만든 String => JSON 변환기 였는데 리턴 타입을 void로 바꾸면서 위에 if절 로 바뀜.
        // 일단 보류...
//        try {
//            response.getHeaders().set("Content-Type", "application/json");
//            return objectMapper.writeValueAsString(apiRes);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
    }
}
