![image](https://github.com/user-attachments/assets/e808f9e1-8ab4-446c-9075-07867d47398d)# servlet

## 웹 서버
- 웹 브라우저의 HTTP 요청을 받아 웹 페이지(HTML 문서)를 반환하는 프로그램
- 주요 기능:
 - HTTP/HTTPS 프로토콜 통신 처리
 - HTML 문서, 이미지 파일, JavaScript 객체 제공
- 대표적인 웹 서버:
 - Apache
 - Nginx

## 웹 콘텐츠 종류
- 정적 웹 콘텐츠:
 - 웹 서버에 미리 저장된 콘텐츠
 - 모든 사용자에게 동일한 내용 전달
 - 예: HTML 파일, 이미지
- 동적 웹 콘텐츠:
 - 사용자 요인(시간, 위치, 장치 등)에 따라 변경되는 콘텐츠 
 - 사용자별로 다른 콘텐츠 제공 가능
 - 서버 측 처리 필요
   

## CGI (Common Gateway Interface)
- 웹 서버가 외부 프로그램을 실행할 수 있게 하는 인터페이스 규격
![image](https://github.com/user-attachments/assets/77c72a8c-29e8-47bc-8702-e8923b85deb2)

- 주요 특징:
 - 동적 콘텐츠 생성 가능
 - 언어 독립적 (C++, Java, PHP, Python 등)
 - 표준 입출력 사용
 - 환경 변수로 요청 데이터 전달

### CGI 장단점
#### 장점:
- 플랫폼, 언어 독립적
- 구조가 단순
- 구현이 쉬움

#### 단점:
- 성능이 느림
- 요청마다 새 프로세스 생성
- 메모리 캐시 불가

## Fast CGI
- CGI의 성능 개선 버전
- 하나의 프로세스로 동작
- 대부분의 웹 서버가 지원

  
