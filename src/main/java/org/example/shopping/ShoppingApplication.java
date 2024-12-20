package org.example.shopping;

import jakarta.annotation.PostConstruct;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.example.shopping")
public class ShoppingApplication {

	@Value("${common}")
	private String common;

	@Value("${test}")
	private String test;

	public static void main(String[] args) {
		SpringApplication.run(ShoppingApplication.class, args);
	}

	// 의존성 주입 타이밍에서 실행해줌. 어떤 properties 파일이 컴파일 됐는지 확인하기 위해 사용함.
	@PostConstruct
	private void start() {
		System.out.println("common = " + common);
		System.out.println("test = " + test);
	}

}
