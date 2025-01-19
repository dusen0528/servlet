# Servlet 3.0 Annotation

## Annotation 이란?
- 코드에 달아주는 특별한 **정보표시** 또는 **주석**
- @기호로 시작하며 프로그램에 특별한 지시나 설정을 알려주는 역할
- 쉽게 말해서 "이 코드는 이런 용도로 사용 될 것이다" 라고 알려주는 표시

**예시**
```java
@WebServlet("/login")  // "이 클래스는 로그인 기능을 처리하는 서블릿이야"라고 알려주는 표시
public class LoginServlet extends HttpServlet {
    // 코드
}

@Override  // "이 메소드는 부모 클래스의 메소드를 덮어쓰는 거야"라고 알려주는 표시
public void doGet() {
    // 코드
}
```

### Annotation 동작방식

```java
@WebServlet("/login")  // 이 어노테이션을 예로 들어보겠습니다
public class LoginServlet extends HttpServlet {
    // 코드
}
```
처리 과정 :
1. 서버가 시작될 때 자바는 @WebServlet이 붙은 클래스들을 찾음
2. 찾은 클래스의 정보를 읽어 "/login"URL과 이 서블릿을 연결
3. 실제로 "/login" 요청이 오면 이 서블릿이 실행되도록 설정

처리 방식
- 리플렉션을 사용
```java
// 예: 내부적으로 이런 식으로 동작
Class<?> clazz = LoginServlet.class;
WebServlet annotation = clazz.getAnnotation(WebServlet.class);
String urlPattern = annotation.value();  // "/login" 값을 가져옴
```

- @WebServlet 
```
 <servlet>
        <servlet-name>fileUploadServlet</servlet-name>
        <servlet-class>com.nhnacademy.study.FileUploadServlet</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>fileUploadServlet</servlet-name>
        <url-pattern>/file/fileUpload</url-pattern>
    </servlet-mapping>
```
- 이 부분을 제거
```java
@Slf4j
@WebServlet(name = "fileUploadServlet", urlPatterns = "/file/fileUpload")
public class FileUploadServlet extends HttpServlet {
    //...
}
```
다음과 같이 추가 

- @WebInitParam 
```
<servlet>
        <servlet-name>loginServlet</servlet-name>
        <servlet-class>com.nhnacademy.study.LoginServlet</servlet-class>
        <init-param>
            <param-name>id</param-name>
            <param-value>admin</param-value>
        </init-param>
        <init-param>
            <param-name>pwd</param-name>
            <param-value>1234</param-value>
        </init-param>
    </servlet>
    <servlet-mapping>
        <servlet-name>loginServlet</servlet-name>
        <url-pattern>/login</url-pattern>
    </servlet-mapping>

```
- 이부분을 제거
```java
@WebServlet(
        name = "loginServlet",
        urlPatterns = "/login",
        initParams = {
                @WebInitParam(name="id",value = "admin"),
                @WebInitParam(name="pwd",value = "1234"),
        }
)
public class LoginServlet extends HttpServlet {
    //..
}
```
- 다음과 같이 추가 

- @WebFilter 
```
<filter>, <filter-mapping> 제거

CharacterEncodingFilter
```
- 이부분을 제거

```java
@WebFilter(
        filterName = "characterEncodingFilter",
        urlPatterns = "/*",
        initParams = {
                @WebInitParam(name = "encoding", value = "UTF-8")
        }
)

public class CharacterEncodingFilter implements Filter {
    // ..
}
```
- 다음과 같이 추가

- @WebListener 
```
   <listener>
        <listener-class>com.nhnacademy.study.listener.ServletContextListener</listener-class>
    </listener>
```
- 이부분을 제거

```java
@Slf4j
@WebListener
public class ServletContextListener implements jakarta.servlet.ServletContextListener {

}
```
- 다음과 같이 추가

---

## <context-param>
- <context-param>은 웹 애플리케이션의 전역 설정 파라미터를 정의하는 부분
- web.xml 파일에서 사용되며, 웹 애플리케이션 전체에서 사용할 수 있는 값들을 저장

예시로 
```
<context-param>
    <param-name>url</param-name>
    <param-value>https://nhnacademy.com</param-value>
</context-param>
<context-param>
    <param-name>counterFileName</param-name>
    <param-value>counter.dat</param-value>
</context-param>
```

이렇게 설정된 파라미터들은 
1. 웹 애플리케이션이 시작될 때 로드
2. 애플리케이션 전체에서 접근 가능 
3. 모든 서블릿과 JSP에서 공유 가능

- 서블릿에서 이 값을 가져오는 방법
```java
String url = getServletContext().getInitParameter("url");
```


## ServletContainerInitializer 구현
jakarta.servlet.ServletContainerInitializer 파일 생성
- resources/META-INF/services 폴더 하위에 
  - jakarta.servlet.ServletContainerInitializer 파일 생성 
  - 파일 내용 : 구현 클래스 FQCN (Fully Qualified Class Name)  - 클래스가 속해있는 페키지명을 모두 포함한 이름.

```java
package com.nhnacademy.study;

import jakarta.servlet.ServletContainerInitializer;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.HandlesTypes;

import java.util.Set;

@HandlesTypes(value = {
        jakarta.servlet.http.HttpServlet.class,
        jakarta.servlet.Filter.class,
        jakarta.servlet.ServletContextListener.class,
        jakarta.servlet.http.HttpSessionListener.class
})

public class WebAppInitializer implements ServletContainerInitializer {
    @Override
    public void onStartup(Set<Class<?>> set, ServletContext servletContext) throws ServletException {
        servletContext.setInitParameter("url","https://nhnacademy.com");
        servletContext.setInitParameter("counterFileName","counter.dat");

    }
}
```
**파일 위치와 내용**
- 위치: /study/src/main/resources/META-INF/services/jakarta.servlet.ServletContainerInitializer
- 내용: com.nhnacademy.study.WebAppInitializer

**동작 순서**
- 서버가 시작될 때 가장 먼저 META-INF/services 폴더에서 jakarta.servlet.ServletContainerInitializer 파일을 찾음
- 이 파일 안에 있는 FQCN(com.nhnacademy.study.WebAppInitializer)을 읽음
- 해당 클래스가 ServletContainerInitializer 인터페이스를 구현했기 때문에 
- onStartup 메서드를 자동으로 실행시켜 초기 설정을 함 
  - url 파라미터 설정 
  - counterFileName 파라미터 설정

이를 통해 web.xml 에서 context-param 제거 가능

```
<context-param>
    <param-name>url</param-name>
    <param-value>https://nhnacademy.com</param-value>
</context-param>
<context-param>
    <param-name>counterFileName</param-name>
    <param-value>counter.dat</param-value>
</context-param>
```

---
# 실습 web.xml 에서 남은 servlet, filter, listener 설정을 모두 annotation 으로 변경하기
@WebServlet

@WebInitParam

@WebFilter

@WebListener

@MultipartConfig