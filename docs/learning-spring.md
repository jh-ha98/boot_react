# API Server 학습 정리
스프링 프로젝트로 API 서버를 만들면서 정리한 학습내용입니다.
- [메인](../README.md)

## 목차
- [Java](#java)
- [사용된 패키지](#사용된-패키지)
- [API 서버 계층구조](#api-서버-계층구조)
- [REST API](#rest-api)
- [Gradle](#gradle)
- [JPA](#jpa)
- [Generic](#generic)
- [SpringBoot Validation](#springBoot-validation)

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
java -jar -Dspring.profiles.active=prod [파일명].jar &
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
    - 스프링진영 대표 ORM.
- `org.postgresql:postgresql` 
    - postgresql 데이터베이스 드라이버.
- `org.springframework.boot:spring-boot-starter-validation`
    - 스프링 유효성 검사 라이브러리.

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

## Generic
클래스에서 사용할 타입을 외부에서 선언하는 것
선언할 때 파라미터가 구체적인 타입으로 결정
- 사용하는 이유: 잘못된 타입이 사용될 수 있는 문제를 컴파일 과정에서 제거할 수 있다.
### 사용법
- public class 클래스명&lt;T&gt; {...}
- public interface 인터페이스명&lt;T&gt; {...}
### 자주사용하는 타입인자
|타입인자|설명|
|-|-|
|&lt;T&gt;|Type|
|&lt;E&gt;|Element|
|&lt;K&gt;|Key|
|&lt;N&gt;|Number|
|&lt;V&gt;|Value|
|&lt;R&gt;|Result|

## SpringBoot Validation
Validation : 데이터 검사

Bean Validation : validator가 어떠한 비즈니스적 로직에 대한 검증이 아닌, 그 클래스로 생성된 객체 자체의 필드에 대한 유효성 여부를 검증합니다.

```
@Valid, @Validated 차이
- @Valid는 Java 에서 지원해주는 어노테이션
- @Validated는 Spring에서 지원해주는 어노테이션
- @Validated는 @Valid의 기능을 포함하고, 유효성을 검토할 그룹을 지정할 수 있는 기능을 추가로 가집니다.
```

1. build.gradle에 dependency 추가
    ```java
    implementation 'org.springframeworboot:spring-boot-starter-validation'
    ```
2. Contoller에서 유효성 검사를 적용할 API의 Request객체 앞에 @validated 어노테이션을 추가
- BindingResult 객체 : 검증 오류가 발생할 경우 오류 내용을 보관하는 스프링 프레임워크에서 제공하는 객체

- BindingResult객체를 사용하면 데이터 검증 결과를 담고 있습니다.
- service에서 try~ catch의 범용적인 메시지를 하나하나 지정할 필요없게 되고 알수없는 에러 처리를 하지 않아도 에러 메시지를 관리하기 편리하게 됩니다.

- 주의) BindingResult는 검증할 대상 다음에 와야합니다.

    ```java
     /** 회원 가입 */
    @PostMapping("/member/sign-up")
    public ResponseEntity<MessageBox<MemberInfo>> signUp(@RequestBody @Validated MemberSignUpForm form,
      BindingResult bindingResult) {
    if (bindingResult.hasErrors())
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessageBox.failed(bindingResult));

    MessageBox<MemberInfo> result = memberService.signUp(form);
    MessageBoxValid valid = (MessageBoxValid) result.getValid();
    if (valid == MessageBoxValid.FALSE)
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);

    return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    ```
3. Request를 핸들링할 객체를 정의할 때 Validation 어노테이션을 통해 필요한 유효성 검사를 적용
    ```java
    package hjh.api.domain.dto.member;

    import javax.validation.constraints.NotEmpty;

    import lombok.Getter;
    import lombok.ToString;

    @Getter
    @ToString
    public class MemberSignUpForm {

    @NotEmpty(message = "아이디를 입력해주세요.")
    private String loginId;
  
    @NotEmpty(message = "비밀번호를 입력해주세요.")
    private String password;
  
    @NotEmpty(message = "이메일을 입력해주세요.")
    private String email;
    }
    ```