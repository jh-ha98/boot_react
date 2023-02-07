# API Server 학습 정리
스프링 프로젝트로 API 서버를 만들면서 정리한 학습내용입니다.

## Java
객체지향형 언어.
### JDK (Java Development Kit)
- 자바언어를 이용한 개발에 필요한 툴모음집.
- 자바 컴파일러가 포함되어있다.
- 개발 환경을 vscode로 작성하면서 microsoft사의 open jdk를 사용하였다. (https://www.microsoft.com/openjdk)
- 개발의 편의성을 위해 환경변수 설정을 해주었다. 환경변수 설정방법은 구글에 검색해보자.
### Type
- `Optional`: null이 올 수 있는 값을 감싸는 Wrapper 클래스 데이터의 null 값을 한번 더 체크함으로서 더 안정적인 코드를 작성 가능합니다.
### 명령어
```bash
# [파일명].jar 파일을 실행하는 명령어
java -jar [파일명].jar 
# [파일명].jar 파일을 실행할때 application-prod.properties 파일을 우선적으로 읽어서 실행합니다.
# '&' 키워드는 백그라운드로 명령어를 실행합니다.
java -jar -Dspring.profile.active=prod [파일명].jar &
```

## 사용된 패키지
- `org.springframework.boot:spring-boot-starter-web` 
    - 스프링 웹서비스를 만들기위한 기본적인 패키지입니다.
- `org.springframework.boot:spring-boot-starter-test` 
    - 스프링 테스트 코드를 작성하기위한 패키지입니다.
- `org.springframework.boot:spring-boot-devtools` 
    - 개발시 코드의 변화를 감지하여 서버를 자동 실행합니다. (live server)
- `org.projectlombok:lombok` 
    - 다양한 어노테이션으로 코드 작성의 도움을 줍니다.
- `org.springframework.boot:spring-boot-starter-data-jpa` 
    - 스프링진영 대표 OOP.
- `org.postgresql:postgresql` 
    - postgresql 데이터베이스 드라이버.

## API 서버 계층구조
- `controller`
    - http 요청과 응답을 담당.
- `service`
    - 핵심 비즈니스 로직을 담당.
- `repository`
    - 데이터베이스에 접근하는 객체.
    - 데이터베이스에 매칭되는 Entity를 통해 받아온 정보를 데이터베이스(postgre sql)에 저장하고 조회하는 기능.
- `domain`
    - 비즈니스 객체을 담는 폴더.

## REST API
API란 코드를 기반으로하는 인터페이스. 정해진 규약(사용법)에 의해 사용되며 원하는 결과를 얻을수 있다.
- Method
    - `GET`: SELECT
    - `POST`: INSERT
    - `PUT`: UPDATE(전체 수정)
    - `PATCH`: UPDATE(일부 수정)
    - `DELETE`: DELETE

## Gradle
gradle은 빌드 자동화 툴이고 각종 명령어를 통해 빌드할 수 있습니다.  
Groovy 라는 언어로 작성된다. 정확히는 Groovy DSL로 작성한다.  
DSL이란 Domain Specific Language의 약어로 특정 분야에 최적화된 프로그래밍 언어를 뜻한다.
- gradlew
	- gradlew(gradle wrapper) 새로운 환경에서 프로젝트를 설정할 때 gradle을 설치하지 않고 바로 빌드할 수 있게 해주는 역할을 한다.
	- 프로젝트를 만든 시점의 gradle버전이 유지되기에 gradlew를 사용해야 합니다
- 명령어 정리
	```bash
	# 프로젝트 실행
	./gradlew bootRun
	# 프로젝트 빌드
	./gradlew build
	# 프로젝트 패키징
	./gradlew bootJar
	# 빌드파일을 초기화 합니다
	./gradlew clean
	```

## JPA
자바진영 데이터베이스를 다루기위한 ORM 입니다
### CascadeType
종류
- All: 모두
- REMOVE: 삭제
- PERSIST: 영속

`ex) CascadeType.REMOVE: 부모 엔티티만 삭제하면 연관된 자식 엔티티도 삭제`

### Auditing
AuditingListener를 제공해 특정한 이벤트에 대해 자동으로 반응하여 등록일, 수정일, 등록자, 수정자와 같은 데이터를 입력
- `@CreatedDate`: 생성 시간
- `@LastModifiedDate`: 수정 시간
- `@CreatedBy`: 생성자
- `@LastModifiedBy`: 수정자