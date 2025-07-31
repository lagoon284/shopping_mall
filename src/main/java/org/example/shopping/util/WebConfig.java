package org.example.shopping.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // 필터에서 헤더 추가 시 중요한 설정 (CORS)
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowCredentials(true)
                // 헤더에 접근할 수 있게.. 이거 안하면 어디서든 다 헤더를 볼 수 잇다고 함...
                .exposedHeaders("new-access-token");
    }
}
