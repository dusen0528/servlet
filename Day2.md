# 쿠키와 세션 이해하기 쉬운 가이드

## 🍪 쿠키(Cookie)란?

쿠키는 웹사이트가 여러분의 브라우저에 저장하는 작은 데이터 조각입니다. 마치 쇼핑몰에서 받는 영수증처럼, 나중에 다시 방문했을 때 웹사이트가 여러분을 기억할 수 있게 해줍니다.

### 쿠키의 주요 특징
- 브라우저에 저장됨 (여러분의 컴퓨터에 저장)
- 이름과 값이 한 쌍으로 저장 (예: username=john)
- 유효기간 설정 가능
- 최대 4KB까지 저장 가능

### 쿠키 사용 예시
```java
// 쿠키 만들기
Cookie cookie = new Cookie("username", "john");
cookie.setMaxAge(7 * 24 * 60 * 60); // 7일 동안 유효
response.addCookie(cookie);

// 쿠키 읽기
Cookie cookie = CookieUtils.getCookie(request, "username");
String username = cookie.getValue(); // "john"
```
---
# 🎫 세션(Session)이란?

세션은 웹사이트가 서버에 저장하는 정보입니다. 마치 도서관 회원증처럼, 방문자마다 고유한 식별자를 부여하고 관련 정보를 서버에 저장합니다.

## 세션의 주요 특징

- 서버에 저장됨 (웹사이트 서버에 저장)
- 브라우저가 닫히면 자동으로 삭제됨  
- 쿠키보다 보안성이 높음
- 저장 용량이 쿠키보다 큼

## 세션 사용 예시
```java
// 세션 시작하기
HttpSession session = request.getSession();
session.setAttribute("userId", "12345");

// 세션에서 정보 가져오기
String userId = (String) session.getAttribute("userId");

// 세션 종료하기
session.invalidate();
```

- 일정 기간 동안 보관 가능 (만료기간)
- 다음에 마트에 갈 때 영수증을 보여주면 구매 이력 확인 가능

## 🎫 세션의 이해 (도서관 회원증 비유)
- 도서관(서버)이 회원 정보를 보관
- 회원증(세션ID)만 사용자가 가지고 다님
- 실제 정보는 도서관(서버)에 안전하게 보관
- 회원증을 잃어버리면 새로 발급받아야 함(새 세션 생성)

## 쿠키vs세션 차이점 

| 구분 | 쿠키 | 세션 |
|------|------|------|
| 저장 위치 | 사용자 브라우저에 저장 | 서버에 저장 (사용자는 세션ID만 가짐) |
| 보안성 | 사용자가 수정할 수 있음 (위조 가능) | 서버에서 관리하므로 안전 |
| 용량 제한 | 브라우저당 최대 4KB | 서버 용량에 따라 유동적 |
| 생명주기 | 만료일자를 지정 (예: 7일, 30일 등) | 브라우저 종료시 자동 삭제 (또는 서버에서 지정한 시간) |

# 💡 Optional과 Null 처리

자바에서 null을 안전하게 처리하는 방법입니다.

## Optional.of vs Optional.ofNullable
```java
// null이 절대 아닌 경우
Optional.of("hello");        // 안전!
Optional.of(null);          // 에러 발생!

// null일 수도 있는 경우
Optional.ofNullable("hello"); // 안전!
Optional.ofNullable(null);    // 안전! (에러 없음)
```

### Null일수도 있는 경우를 고려해서 반드시 ofNullable이 좋을까?
**Optional.of**
- Optional.of를 이용해서 코드의 의도를 명확하게 전달 (이 값은 절대 Null이 아니어야한다)
- Null이 들어오면 NullPointerException을 발생시켜 문제를 조기에 발견할 수 있고
- Null 체크를 하지 않기에 성능상 약간 더 유리하다

**Optional.ofNullable**
- null을 허용하기에 더 유연
- 예외가 발생하지 않고 빈 Optional을 반환해 더 안전
- 외부 데이터 다룰 때 유용

따라서 상황에 따라 적절한 것을 선택하는 것이 중요
- null이 절대 있으면 안되는 경우 : of
- null일 수도 있는 경우 : ofNullable
