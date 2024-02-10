# 목표
- 다른 기능들을 추가 (dependency,ajax, mvc model,oauth2,aws,db)
# 기간
- 1/17 
- 1/31 결제api 
- 2/1 chat
- 2/3 동작 확인
- 2/10 ec2 서버 실행
# 환경설정
- java 11 : 안정적
- spirng web : spring mvc framework 사용
- spring boot : 편리, 간편
- lombok : 코드 작성에 편리 (annotation)

- spring security : 사용자 데이터 보호 기능
- oauth2 client : oauth2 사용하기 위해서 (구글, 네이버 등)
  
- thymleaf : html 기반 사용이 편리하다.
- jackson : json 으로 데이터를 주고 받기 위해서
- mariadb : 무료, mysql 과 작동법이 유사
    
# front
- bootstrap : 기존의 css 형식 사용 
- Ajax (jquery) : 부분 데이터 업데이트
- html, javascript
- thymeleaf
# back
- java
- spring boot

# 구현
- 로그인,회원가입,oauth2
  - 회원 프로필 추가 
- 이미지 슬라이드 -> javascript

- 검색어 필터링
  - 조건별로 필터링 할 수 있도록
- 댓글
  
- 게시물
- 쇼핑 -> 일단 결제까지 
  - 결제
  - 장바구니
    
- 문의하기


#
- ec2 에서 서버를 실행 시키려했는데 not def found erorr, class not found exception 에러 발생함 -> 클래스를 찾지 못해서 발생하는 문제-> 이것저것 찾아보다 1. 의존성 충돌 2. jar 파일 문제 3. 라이브러리 충돌 문제
  - 1. spring boot version 변경 -> 변경된 파일 설정, 경로 설정 문제를 해결해야 했음
    2. jar 파일 문제 -> 아직 모름
    3. 외부 라이브러리가 저장된 장소를 들어가보니 이전에 저장된 라이브러리와 새로 업데이터한 라이브러리가 공존 -> 예전 라이브러리 삭제
    4. proccess 문제? port kill
  - 해결 했지만 확실히 어떤 부분이 왜 문제였는지 알지 못함 -> linux , network 부분
- 페이지 접근 권한 문제  -> spring security 에 requestMatcher 경로 설정 문제 -> url 변경
  
- 기본적으로 html, thymeleaf 로 로딩
- 데이터 업데이트시 일부만 변경
  - comment, category 재귀로 서버에서 랜더링 후 데이터를 전달하는 방식 or 브라우저 랜더링하는 방식
- complier version error 17 -> 11
- localhost:8080 sign in ?? -> spring security 의존성에 의한 로그인 화면 -> exclude
- spring security form login & oauth2 login
- security -> http.csrf().disable(); 설정 -> 403 forbidden

- 기능 추가
- slick slide -> home 화면에서 sidebar 반응 안함..
- 데이터베이스 데이터가 null 값으로 저장된다.-> BeanPropertyRowMapper 값을 가져와서 setter 를 통해 값을 할당해준다 -> setter 가 없었다.

- jdbcTemplate queryForObject 에서 값이 없을 경우 -> EmptyResultDataAccessException
- thymeleaf ${} 는 문자열로 랜더링 
- putty ec2 에서 오류가 나는데 뭐가 문제인지 알 수가 없음
  - 의존성 호환 문제?
