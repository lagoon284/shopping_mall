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
                    return true;           // null 또는 빈 문자열인 경우
                } else if (value instanceof String && ((String) value).isEmpty()) {
                    return true;           // 빈 문자열 체크.
                } else if (value instanceof Number && ((Number) value).doubleValue() <= 0) {
                    return true;           // 숫자 타입일 경우 0 이하 체크.
                }
                // 이후 비슷한 소스로 여러 타입 지정 가능.
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return true;
            }
        }
        return false;                        // 모든 필드가 유효한 경우.
    }

    // 객체 병합 메서드.
    public static <T, U> void mergeObject(T objectA, U objectB) {
        // A 클래스의 모든 필드에 대해 반복.
        for (Field fieldA : objectA.getClass().getDeclaredFields()) {
            try {
                // 필드의 이름 가져오기.
                String fieldName = fieldA.getName();

                // B 클래스에서 동일한 이름의 필드 찾기.
                Field fieldB = null;
                for (Field declaredField : objectB.getClass().getDeclaredFields()) {
                    if (declaredField.getName().equals(fieldName)) {
                        fieldB = declaredField;
                        break;
                    }
                }
                // 필드가 존재하는 경우에만 처리.
                if (fieldB != null) {
                    fieldA.setAccessible(true);
                    fieldB.setAccessible(true);

                    // A 객체의 값이 null이면 B 객체의 값을 사용.
                    if (fieldA.get(objectA) == null) {
                        fieldA.set(objectA, fieldB.get(objectB));
                    } else if (fieldA.get(objectA) instanceof Integer) {
                        if ((Integer) fieldA.get(objectA) == 0) {
                            fieldA.set(objectA, fieldB.get(objectB));
                        }
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
