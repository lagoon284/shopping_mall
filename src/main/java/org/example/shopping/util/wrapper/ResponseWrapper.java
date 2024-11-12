package org.example.shopping.util.wrapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.shopping.util.api.ApiRes;
import org.springframework.core.MethodParameter;
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
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {

        Class<?> type = returnType.getParameterType();      // 파라미터의 타입을 저장. 이후 대상 클레스들에서 상곡, 구현 되었는지 비교하게 됨.

        // type이 ResponseEntity 클래스에서 상속/구현 되었는지? isAssignnableFrom은 instanceof와 비슷하지만 instanceof는 인스턴스 화 되었는지가 다름.
        if (ResponseEntity.class.isAssignableFrom(type)) {
            try {
                ParameterizedType parameterizedType = (ParameterizedType) returnType.getGenericParameterType();
                type = (Class<?>) parameterizedType.getActualTypeArguments()[0];
            } catch (ClassCastException | ArrayIndexOutOfBoundsException ex) {
                return false;
            }
        }

        // type이 ApiRes 클래스에서 상속/구현 되었는지?
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

        // 만든 커스텀 클래스로 빌더.
        ApiRes<?> apiRes = ApiRes.builder()
                // controller 경로.
                .path(path)
                // return 하는 객체...?
                .data(body)
                .build();

        // mapping json converter 가공을 거쳤는지? (그냥 String이라면 false, 객체로 받아 json으로 가공을 해준다면 true)
        if (MappingJackson2HttpMessageConverter.class.isAssignableFrom(selectedConverterType)) {
            return apiRes;
        }

        try {
            response.getHeaders().set("Content-Type", "application/json");
            return objectMapper.writeValueAsString(apiRes);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
