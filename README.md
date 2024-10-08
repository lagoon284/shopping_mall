- 사용할 스택
	- Spring boot
	- openJDK 17
	- h2 - data base
	- mybatis 사용.

   
- new project setting
	- Spring boot 3.3.4
		- Spring Boot DevTools
		- Lombok
		- Spring web
		- H2 Database
		
	- 최종 dependencies
		- dependencies {  
		    implementation 'org.springframework.boot:spring-boot-starter-web'  
		    implementation 'org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.3'  
		    implementation 'org.springframework.boot:spring-boot-starter-jdbc'  
		    implementation 'org.apache.logging.log4j:log4j-slf4j-impl:2.17.2'  
		    implementation 'org.apache.logging.log4j:log4j-api:2.17.2'  
		    implementation 'org.apache.logging.log4j:log4j-core:2.17.2'  
		    implementation group: 'org.bgee.log4jdbc-log4j2', name: 'log4jdbc-log4j2-jdbc4.1', version: '1.16'  
		  
		    developmentOnly 'org.springframework.boot:spring-boot-devtools'  
		  
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
	- 아직 미정. (api 방식이기 때문에 아무거나 상관없긴 함.)


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
