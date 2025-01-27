# servlet

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
![image](https://github.com/user-attachments/assets/86ee3276-a615-4b1d-857f-1b173d333fd2)


## Java CGI
![image](https://github.com/user-attachments/assets/e1a39c23-f777-44f7-9c6b-94f79fc5b526)
- .class 형태로 컴파일된 Java는 컴파일 방식으로 실행이 불가능
- JVM은 Java 실행은 가능하지만 웹서버 <-> Java Application Server간 토신이 불가능

따라서 웹서버와 Java Application 사이에서 서로 통신할 수 있도록 JCGI가 있어야함
![image](https://github.com/user-attachments/assets/623f58c7-c7c4-4e21-b913-21ccc3ce2006)

### Java CGI 문제점
- 별도의 스크립트가 필요
  - jcgi
  - Java Runtime Interpereter 실행
  - main class wlwjd
- 환경변수를 명시적으로 Java 프로그램에 넘겨줘야함
  
## Fast CGI
- FastCGI의 주목적은 웹 서버와 CGI 프로그램 통신 시 발생하는 부하를 줄임으로써 서버가 한번에 더 많은 웹페이지 요청을 관리할 수 있게 하는 것
  - 하나의 큰 프로세스로 동작
  - 대부분 웹서버가 Fast CGI 제공
     - Apache
     - Nginx
     - IIS
 ![image](https://github.com/user-attachments/assets/4218856e-0460-40e7-8ef9-493c6a50dd14)

 ### Java는 WAS인 Tomcat 사용

 ---

# 서블릿(Servlet)

## 서블릿이란?
- Java를 사용하여 동적 웹 콘텐츠를 생성하는 서버 측 프로그램
- Java로 구현한 CGI 프로그램과 유사
- Servlet 인터페이스를 구현하여 만듦

## CGI 단점 해결
- CGI의 프로세스 생성 문제를 멀티 스레드 방식으로 해결
- 스레드 생성과 관리는 컨테이너가 담당

## 서블릿 컨테이너
- 웹 서버의 컴포넌트로 서블릿과 상호작용
- 주요 기능:
 - 서블릿 생명주기 관리
 - URL과 서블릿 매핑
 - 접근 권한 관리
 - 서블릿 객체 생성 및 관리
 - 요청/응답 객체 생성 및 관리
 - 보안, 병행성, 트랜잭션 등 관리

## 실습 - HelloServlet 구현
### 주요 구성:
1. HelloServlet 클래스 생성
  - HttpServlet 상속
  - doGet() 메소드 오버라이드
  - UTF-8 인코딩으로 HTML 응답 생성

2. web.xml 설정
  - 서블릿 등록
  - URL 매핑 설정
  - 기본 설정: /hello 경로로 매핑

3. Tomcat 서버 설정
  - Tomcat 10 설치
  - 서버 설정
  - war exploded 아티팩트 설정
  - 기본 포트: 8080

 ---

프로젝트 src/main/webapp/WEB-INF/web.xml  파일 수정 
```
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="https://jakarta.ee/xml/ns/jakartaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="https://jakarta.ee/xml/ns/jakartaee https://jakarta.ee/xml/ns/jakartaee/web-app_5_0.xsd"
         version="5.0">

<servlet>
    <servlet-name>helloServlet</servlet-name>  <!-- 서블릿의 이름 지정 -->
    <servlet-class>com.nhnacademy.study.HelloServlet</servlet-class>  <!-- 실제 서블릿 클래스의 전체 경로 -->
</servlet>
<servlet-mapping>
    <servlet-name>helloServlet</servlet-name>  <!-- 위에서 지정한 서블릿 이름 -->
    <url-pattern>/hello</url-pattern>  <!-- 브라우저에서 접근할 URL 패턴 -->
</servlet-mapping>
</web-app>
```


- 이 설정은 "/hello" URL로 요청이 오면 com.nhnacademy.study.HelloServlet 클래스를 실행하라는 의미

톰캣 설정 
tomcat → Deployment → artifact → hello:war exploded

Application Context : / ← 변경
![image](https://github.com/user-attachments/assets/7117cd69-fcd1-4c0a-a7b1-44c69e834d89)

artifact → hello:war exploded

artifact는 프로젝트를 배포 가능한 형태로 만든 것을 의미
war exploded는 WAR 파일을 압축 해제한 디렉토리 구조로 배포한다는 의미
hello:는 모듈의 이름이며, 실제 프로젝트 이름과 다를 수 있음 (IntelliJ가 자동으로 설정)


Application Context: /

웹 애플리케이션의 기본 URL 경로를 의미
/로 설정하면 http://localhost:8080/ 이 기본 경로
만약 /study로 설정하면 http://localhost:8080/study/hello 로 접근해야 함



따라서 현재 설정은:

1. http://localhost:8080/hello 로 요청이 오면
2. com.nhnacademy.study.HelloServlet 클래스의 doGet() 메서드가 실행되어
3. 응답을 생성

![image](https://github.com/user-attachments/assets/3fb10264-fb91-48a3-a721-7dc14039f669)

---
# Servlet 개발
## Servlet Interface
```
public interface Servlet {
    public void init(ServletConfig config) throws ServletException;

    public void service(ServletRequest req, ServletResponse res)
      throws ServletException, IOException;

    public void destroy();

    public ServletConfig getServletConfig();

    public String getServletInfo();
}
```

## Servlet Lifecycle
- 서블릿의 생명주기는 서블릿 인스턴스가 생성되고 소멸되기까지의 전체 과정을 의미하며, 서블릿 컨테이너에 의해 관리됩니다.

### 2.1 초기화 단계 (Initialization)
```java
public class LifecycleServlet extends HttpServlet {
   @Override
   public void init() throws ServletException {
       System.out.println("1. 서블릿 초기화 - 최초 1회만 실행");
       // 데이터베이스 연결
       // 설정 파일 로드
       // 초기 데이터 준비
   }
}
```
- 서블릿 인스턴스 생성 후 최초 1회만 실행
- 초기화 작업 수행 (DB 연결, 설정 파일 로드 등)
- ServletConfig 객체를 통해 초기 파라미터 접근 가능

### 2.2 서비스 단계 (Service)
```
public class LifecycleServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        System.out.println("2. 요청 처리 - 요청마다 실행");
        String method = req.getMethod();
        
        if (method.equals("GET")) {
            doGet(req, resp);
        } else if (method.equals("POST")) {
            doPost(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.println("<h1>GET 요청 처리</h1>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        // POST 요청 처리 로직
    }
}
```

- 클라이언트 요청마다 새로운 스레드에서 실행
- HTTP 메서드(GET, POST 등)에 따라 적절한 메서드 호출
- 멀티스레드로 동작하므로 동시 요청 처리 가능

### 2.3 소멸 단계 (Destruction)
```
public class LifecycleServlet extends HttpServlet {
    @Override
    public void destroy() {
        System.out.println("3. 서블릿 종료 - 종료 시 1회 실행");
        // 데이터베이스 연결 종료
        // 리소스 해제
        // 임시 파일 삭제
    }
}
```
- 서블릿이 종료되기 전에 1회 실행
- 리소스 정리 작업 수행
- 웹 애플리케이션 종료나 재배포시 실행

## 생명주기 특징

- init(): 서블릿 인스턴스 생성 후 1회 실행
- service(): 클라이언트 요청마다 새 스레드에서 실행
- destroy(): 서블릿 종료 시 1회 실행
- 서블릿은 싱글톤 패턴으로 관리됨
- 멀티스레드 환경에서 안전하게 코딩 필요

## 실행 순서

1. 클라이언트의 최초 요청 → init() 실행
2. 요청 발생 → service() → doGet() 또는 doPost() 실행
3. 서버 종료/재배포 → destroy() 실행


