- 실행할때 주의점...!!
	- 인텔리 기반이라서 인텔리에서 하시거나 이클립스로 변환 하시는걸 추천합니다.
 	- react 실행은 아직 연동하지 않아서 터미널에서
	  'shopping_mall\shopping\src\main\front_react'
	  경로로 가셔서
	  npm start
	  해주셔야 리엑트가 실행됩니다.
	- 자바 에플리케이션 실행 따로, react 실행 따로해주셔야 합니다. (순서는 상관 없습니다.)
	- 모르겠으면 직접 물어보시는게 편하실 겁니다...

- 사용할 스택
	- Spring boot
	- openJDK 17
	- h2 - data base
	- mybatis 사용.
	- React & typeScript

   
- new project setting
	- Spring boot 3.2.2
		- Spring Boot DevTools
		- Lombok
		- Spring web
		- H2 Database
		
	- 최종 dependencies
		- dependencies {
			implementation 'org.springframework.boot:spring-boot-starter-web'
			implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
			implementation 'org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.3'
			implementation 'ch.qos.logback:logback-classic'
			implementation 'org.springframework.boot:spring-boot-starter-jdbc'
			implementation 'org.slf4j:slf4j-api'
			implementation 'org.springframework.boot:spring-boot-starter-aop' // AOP 추가
			implementation 'org.springframework.boot:spring-boot-starter-validation'		// validation 의존성 추가.
		
			implementation 'io.jsonwebtoken:jjwt:0.9.1'
			implementation 'com.fasterxml.jackson.core:jackson-databind'
			implementation 'javax.xml.bind:jaxb-api:2.3.1'
			implementation 'org.glassfish.jaxb:jaxb-runtime:2.3.1'
		
		
			compileOnly 'org.projectlombok:lombok'
		
			runtimeOnly 'com.h2database:h2'
		
			annotationProcessor 'org.projectlombok:lombok'
		
			providedRuntime 'org.springframework.boot:spring-boot-starter-tomcat'
		
			testImplementation 'org.springframework.boot:spring-boot-starter-test'
			testRuntimeOnly 'org.junit.platform:junit-platform-launcher'
		}


- 백앤드
	- 회원 - 일단 insert로 구현 추후 회원가입 만들기, 로그인도 일단 추후.. 프론트 연결할 때의 기준으로 백앤드 구현 해놓을 것.
	  
	- 배송지 - 주문을 시켰을 때 어떤 주소로 어떻게 보낼지.
	  
	- 상품 - 상품을 불러올 수 있는 api로 구현할 것. 이후 프론트 스택에서 api로 백앤드와 통신해서 상품을 뿌려줄 예정 (json 방식으로 ㅇㅇ).
	
	- 주문 - 상품 정보와 회원 정보를 기반으로 주문할 기능.


- 프론트
	- React 사용.


- 발생했던 오류
	- new project 하고 gradle load 시 자바 버전과 맞지 않아 gradle-wrapper.properties 파일의 distributionUrl 값에 gradle 버전을 8.8로 낮춰줌.
	  
	- 컨트롤러 클레스를 만들 때 Controller라고 이름을 지정하면 안됨.
	
	- @insert 어노테이션을 찾을 수 없다고 나와서 확인해보니
	  'org.mybatis.spring.boot:mybatis-spring-boot-starter:2.2.0'
	  의존성이 빠져있었음 (MyBatis 관련)
	  
	- spring boot 버전을 3.2.2로 mybatis 버전을 3.0.3으로 바꿔줌
	  이후 Could not move temporary workspace 에러가 발생,
	  gradle-wrapper.properties 파일에 distributionUrl 값을 조정함.
	  8.5버전으로 변경.
	  

- 참고 사이트 모음
	- 백앤드와 프로트의 분리 개념(REST API) - https://code0123.tistory.com/175
