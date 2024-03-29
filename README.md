# next
  1. 기능 모델링
  2. 데이터 베이스 모델링
  3. semantic 구조
  4. style, javascirpt 분리
  5. html 구현
  6. javascript 간결하게
  7. 서버 연결
  8. 기능 심화
  9. aws ci/cd, s3 
# 구현
<img width="50%" src="https://github.com/hiyigh/project1/assets/112844031/9734bfcb-6ed8-48d9-8bca-a436c42e0f48">

<img width="50%" src="https://github.com/hiyigh/project1/assets/112844031/58c115c1-5c3f-4da7-bab2-1b4b060b74b6">

<img width="50%" src="https://github.com/hiyigh/project1/assets/112844031/b275fd34-2b57-4241-82e0-40f2e172ec59">

# 목표
- 기초적인 crud 이외에 다른 기능들을 추가

# 기간
- 1/17 
- 1/31 결제api 
- 2/1 chat
- 2/3 동작 확인
- 2/10 ec2 서버 실행
- 2/15 비회원, 회원, 관리자 버전 확인
# 환경설정
- java 11 -> java 17
- spirng web : spring mvc
- spring boot
- lombok : annotation

- spring security 6
- oauth2 client
- 
- mariadb 
    
# front
- bootstrap
- Ajax (jquery) 
- html, javascript
- thymeleaf
# back
- java
- spring boot

# 구현
- 로그인,회원가입,oauth2
  - 회원 프로필 추가 
- 이미지 슬라이드 -> javascript -> slick
- 검색어 필터링
  - 조건별로 필터링 할 수 있도록
- 댓글
- 게시물
- 쇼핑 -> 일단 결제까지 
  - 결제
  - 장바구니    
- 문의하기

#
- spring boot 기본 경로 설정 문제 -> spring thyemleaf prefix 
- putty ec2 에서 오류
    ->의존성 호환 문제?
    - ec2 에서 서버를 실행 시키려했는데 not def found erorr, class not found exception 에러 발생 -> 클래스를 찾지 못해서 발생하는 문제-> 이것저것 찾아보다 1. 의존성 충돌 2. jar 파일 문제 3. 라이브러리 충돌 문제
      - 1. spring boot version 변경 -> 변경된 파일 설정, 경로 설정 문제를 해결해야 했음
        2. jar 파일 문제 -> 아직 모름
        3. 외부 라이브러리가 저장된 장소를 들어가보니 이전에 저장된 라이브러리와 새로 업데이터한 라이브러리가 공존 -> 예전 라이브러리 삭제
        4. proccess 문제? port kill
      - 해결 했지만 확실히 어떤 부분이 왜 문제였는지 알지 못함 -> linux , network 부분
- 페이지 접근 권한 문제  -> spring security 에 requestMatcher 경로 설정 문제 -> url 변경
  
- 기본적으로 html, thymeleaf 로 로딩
  
- 데이터 업데이트시 일부만 변경
  - comment, category 재귀로 서버에서 랜더링 후 데이터를 전달하는 방식 or 브라우저 랜더링하는 방식 -> 화면 변환이 필요한지 필요하지 않는지에 대한 생각을 충분히 해볼 것
  
- localhost:8080 sign in -> spring security 의존성에 의한 로그인 화면 -> exclude annotation 사용
  
- security -> http.csrf().disable(); 설정 -> 403 forbidden 

- slick slide -> home 화면에서 sidebar 반응 안함.. -> th:replace
- 
- 데이터베이스 데이터가 null 값으로 저장-> BeanPropertyRowMapper 값을 가져와서 "setter" 를 통해 값을 할당해준다

- jdbcTemplate queryForObject 에서 값이 없을 경우 -> EmptyResultDataAccessException

# 
사전에 계획없이 살을 붙여 코드를 작성했다. 완성 뒤 수정하거나 구조를 변경하려고 할 때 어려움이 많다. 
