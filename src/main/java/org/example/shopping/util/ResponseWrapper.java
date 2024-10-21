package org.example.shopping.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.shopping.model.api.ApiRes;
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

@RestControllerAdvice(basePackages = "org.example.shopping.controller")
@RequiredArgsConstructor
public class ResponseWrapper implements ResponseBodyAdvice<Object> {

    private final ObjectMapper objectMapper;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {

        Class<?> type = returnType.getParameterType();

        if (ResponseEntity.class.isAssignableFrom(type)) {
            try {
                ParameterizedType parameterizedType = (ParameterizedType) returnType.getGenericParameterType();
                type = (Class<?>) parameterizedType.getActualTypeArguments()[0];
            } catch (ClassCastException | ArrayIndexOutOfBoundsException ex) {
                return false;
            }
        }
        if (ApiRes.class.isAssignableFrom(type)) {
            return false;
        }
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {

        String path = request.getURI().getPath();

        ApiRes<?> apiRes = ApiRes.builder()
                .path(path)
                .data(body)
                .build();

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
