package org.example.shopping.util.common;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;


@Slf4j
public class ValidationUtil {

    public static boolean validateObject(Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);      // private 필드 접근 허용.

            try {
                Object value = field.get(obj);
                if (value == null) {
                    return false;           // null 또는 빈 문자열인 경우
                } else if (value instanceof String && ((String) value).isEmpty()) {
                    return false;           // 빈 문자열 체크.
                } else if (value instanceof Number && ((Number) value).doubleValue() <= 0) {
                    return false;           // 숫자 타입일 경우 0 이하 체크.
                }
                // 이후 비슷한 소스로 여러 타입 지정 가능.
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;                        // 모든 필드가 유효한 경우.
    }
}
