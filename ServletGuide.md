# Servlet 완벽 가이드

## 목차
1. 서블릿 생명주기 메서드
2. ServletConfig
3. HttpServletRequest
4. HttpServletResponse
5. ServletContext
6. HttpSession
7. Filter
8. Listener

## 1. 서블릿 생명주기 메서드
| 메서드 | 설명 | 호출 시점 |
|--------|------|-----------|
| init(ServletConfig config) | 서블릿 초기화 작업 수행. 서블릿당 한 번만 실행됨 | 서블릿 인스턴스 생성 후 최초 한 번 |
| service(ServletRequest req, ServletResponse res) | 클라이언트의 요청을 처리하는 메인 메서드. HTTP 메서드에 따라 doGet, doPost 등을 호출 | 클라이언트 요청마다 |
| destroy() | 서블릿 종료 전 정리 작업 수행 | 서블릿 종료 시 한 번 |
| doGet(HttpServletRequest req, HttpServletResponse resp) | HTTP GET 요청 처리 | GET 요청마다 |
| doPost(HttpServletRequest req, HttpServletResponse resp) | HTTP POST 요청 처리 | POST 요청마다 |

## 2. ServletConfig
| 메서드 | 설명 | 예제 |
|--------|------|------|
| getInitParameter(String name) | 서블릿의 초기화 파라미터 값을 반환 | `String value = config.getInitParameter("paramName");` |
| getInitParameterNames() | 모든 초기화 파라미터 이름을 Enumeration으로 반환 | `Enumeration<String> names = config.getInitParameterNames();` |
| getServletName() | 서블릿의 이름을 반환 | `String servletName = config.getServletName();` |
| getServletContext() | ServletContext 객체를 반환 | `ServletContext context = config.getServletContext();` |

## 3. HttpServletRequest
| 메서드 | 설명 | 예제 |
|--------|------|------|
| getParameter(String name) | 요청 파라미터 값을 반환 | `req.getParameter("username");` |
| getParameterNames() | 요청 파라미터 이름의 Enumeration 반환 | `Enumeration<String> params = req.getParameterNames();` |
| getParameterValues(String name) | 동일한 이름의 파라미터가 여러 개일 경우 배열로 반환 | `String[] values = req.getParameterValues("hobby");` |
| getHeader(String name) | 요청 헤더 값을 반환 | `req.getHeader("User-Agent");` |
| getCookies() | 요청에 포함된 쿠키 배열을 반환 | `Cookie[] cookies = req.getCookies();` |
| getSession() | 현재 요청과 연결된 세션을 반환 | `HttpSession session = req.getSession();` |

## 4. HttpServletResponse
| 메서드 | 설명 | 예제 |
|--------|------|------|
| setContentType(String type) | 응답의 MIME 타입을 설정 | `resp.setContentType("text/html");` |
| setCharacterEncoding(String charset) | 응답의 문자 인코딩을 설정 | `resp.setCharacterEncoding("UTF-8");` |
| addHeader(String name, String value) | 응답 헤더를 추가 | `resp.addHeader("Cache-Control", "no-cache");` |
| sendRedirect(String location) | 클라이언트를 지정된 URL로 리다이렉트 | `resp.sendRedirect("/home");` |

## 5. ServletContext
| 메서드 | 설명 | 예제 |
|--------|------|------|
| getAttribute(String name) | 전역 속성을 반환 | `Object counter = context.getAttribute("counter");` |
| setAttribute(String name, Object object) | 전역 속성을 저장 | `context.setAttribute("counter", 1);` |
| getInitParameter(String name) | 컨텍스트 초기화 파라미터 값을 반환 | `String value = context.getInitParameter("dbUrl");` |

## 6. HttpSession
| 메서드 | 설명 | 예제 |
|--------|------|------|
| setAttribute(String name, Object value) | 세션 속성 저장 | `session.setAttribute("userId", "john123");` |
| getAttribute(String name) | 세션 속성 반환 | `String userId = (String) session.getAttribute("userId");` |
| invalidate() | 세션 무효화 | `session.invalidate();` |

## 7. Filter
필터는 요청과 응답을 가로채서 전처리/후처리를 수행하는 컴포넌트입니다.

| 메서드 | 설명 | 호출 시점 |
|--------|------|-----------|
| init(FilterConfig filterConfig) | 필터 초기화 작업 수행 | 필터 인스턴스 생성 시 |
| doFilter(ServletRequest request, ServletResponse response, FilterChain chain) | 실제 필터링 작업 수행 | 매 요청/응답 시 |
| destroy() | 필터 종료 시 정리 작업 | 필터 종료 시 |

필터 설정 예시:
```java
@WebFilter(urlPatterns = "/*")
public class MyFilter implements Filter {
   public void doFilter(ServletRequest request, ServletResponse response, 
                       FilterChain chain) throws IOException, ServletException {
       // 전처리
       chain.doFilter(request, response);  // 다음 필터 또는 서블릿으로 전달
       // 후처리
   }
}
```

## 8. Listener
리스너는 서블릿 컨테이너의 이벤트를 감지하고 처리하는 컴포넌트입니다.

### 리스너 종류 및 메서드
| 리스너 종류 | 주요 메서드 | 설명 |
|------------|-------------|------|
| ServletContextListener | contextInitialized()<br>contextDestroyed() | 웹 애플리케이션의 시작과 종료 이벤트 처리 |
| HttpSessionListener | sessionCreated()<br>sessionDestroyed() | 세션의 생성과 소멸 이벤트 처리 |
| ServletRequestListener | requestInitialized()<br>requestDestroyed() | 요청의 시작과 종료 이벤트 처리 |

### 리스너 구현 예제
```java
@WebListener
public class MyContextListener implements ServletContextListener {
   @Override
   public void contextInitialized(ServletContextEvent sce) {
       // 웹 애플리케이션 시작 시 실행할 코드
       System.out.println("웹 애플리케이션이 시작되었습니다.");
   }
   
   @Override
   public void contextDestroyed(ServletContextEvent sce) {
       // 웹 애플리케이션 종료 시 실행할 코드
       System.out.println("웹 애플리케이션이 종료되었습니다.");
   }
}
```
## 주요 특징
1. 이벤트 기반 동작
- 특정 이벤트 발생 시 자동으로 해당 메서드 호출
- 컨테이너가 생명주기 관리
2.용도
- 애플리케이션 초기화/정리 작업
- 세션 관리 및 모니터링
- 요청 로깅
- 리소스 관리
3. 설정 방법
- @WebListener 어노테이션 사용
- web.xml에 설정




  
