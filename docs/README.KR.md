### <center>nesonnechek</center>

<center>txt-epub 간 전자책 변환 API</center>

## 소개

사용자가 작성한 txt 파일을 epub 파일로 변환해주는 기능을 제공하는 REST API입니다.

## 사용 기술

-   Java 11
-   Spring Boot 2.7.3
-   Lombok 6.5.1
-   Junit 5.8.2
-   [epublib](https://github.com/psiegman/epublib)
-   [embedded-redis](https://github.com/ozimov/embedded-redis)

## 설치

### 리포지토리 클론

```
git clone https://github.com/shacomiro/nesonnechek.git
```

### Gradle 라이브러리 설치

```
gradlew build
```

## 사용법

### api-app

```
java -jar ./app-api/build/libs/app-api-X.X.X.jar --spring.config.additional-location=./config/ext-config.yaml --spring.profiles.active={프로파일명}
```

[API 문서](./index.html)

#### txt-epub 변환 문법

```
*BT*책-제목   //'책-제목'을 전자책의 제목으로 설정
*BA*책-저자   //'책-저자'를 전차잭의 저자로 설정
*ST*섹션-제목 //'섹션-제목'을 섹션 제목으로 설정
본문-내용     //아무런 문법이 없는 경우 '본문-내용'을 문단으로 설정
```

### api-batch

```
java -jar ./app-api/build/libs/app-batch-X.X.X.jar --spring.config.additional-location=./config/ext-config.yaml --spring.profiles.active={프로파일명} --job.name=deleteExpiredEbookJob time(long)={작업시작시간}
```

> 주의사항 : `작업시작시간`은 `YYYYMMDDHHmm` 형식이며, 숫자여야만 합니다.

### 외부 설정

애플리케이션에서 기본으로 제공하는 프로파일은 아래 목록과 같습니다.

-   `prod`: 배포 환경에 사용되는 프로파일.
-   `dev`: 개발 환경에서 사용되는 프로파일.
-   `local`: 추가 환경 설정 없이 구동하기 위한 프로파일.

데이터베이스와 외부 저장소 접근에 사용되는 민감 정보를 보호하기 위해 일부 설정은 추가적인 외부 설정을 요구합니다.

별도의 추가 설정 없이 로컬 환경에서 애플리케이션을 구동하고 싶다면 `local` 프로파일을 사용하세요. 해당 프로파일에서는 H2 Database와 Embedded Redis가 사용됩니다.

```
ext-config:
  rds:
    secret:
      url: 데이터베이스-url
      username: 데이터베이스-유저네임
      password: 데이터베이스-비밀번호
    config:
      time-zone: 데이터베이스-타임존
      encoding: UTF-8
  redis:
    secret:
      port: 레디스-포트
      host: 레디스-호스트
  aws:
    s3:
      access-key: s3-엑세스키
      secret-key: s3-시크릿키
      service-endpoint: s3-서비스-엔드포인트
      region: s3-리전
      bucket: s3-버킷
  jwt:
    secret:
      key: 최소-512비트-(64문자)-길이-이상의-문자열을-비밀키로-사용하는-것이-매우-권장됩니다
    issuer: 발급자-이름
    valid-milliseconds:
      access: 액세스-밀리초
      refresh: 리프레시-밀리초
  ebook:
    content-directory: 콘텐츠-파일-저장-디렉터리-경로
    ebook-directory: 전자책-파일-저장-디렉터리-경로
```

## 로드맵

-   [ ] 신규 기능
    -   [ ] 더 많은 전자책 문법 요소
    -   [ ] 이미지 추가
    -   [ ] API키 인증 방식 추가
    -   [ ] API 호출 빈도 제한 기능 추가
-   [ ] 구조 개선
    -   [x] 모듈화 적용
    -   [ ] 공통(common) 모듈 제거
-   [ ] 성능 개선
    -   [ ] 파일 I/O 최적화
-   [ ] 지속적 통합(CI)
    -   [x] 자동 빌드 및 테스트 워크플로 구현
    -   [x] 자동 릴리즈 워크플로 구현
    -   [ ]
-   [ ] 지속적 배포(CD)
    -   [ ] 도커 컴포즈를 이용한 배포 적용
    -   [ ] 자동 배포 워크플로 구현
