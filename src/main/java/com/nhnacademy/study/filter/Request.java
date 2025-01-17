package com.nhnacademy.study.filter;

import jakarta.servlet.Servlet;

import java.util.HashMap;
import java.util.Map;

public class Request  {
    private final String path; //요청 경로는 생성 후 변경되면 안 되는 중요한 정보라서 final
    private final Map<String,Object> data = new HashMap<>(); //필터 체인을 통과하면서 필요한 정보를 저장하고 전달할 수 있음
    private String characterEncoding;
    private Object session;

    protected Request(String path){
        this.path = path;
    }

    public String getPath(){
        return path;
    }

    public void put(String key, Object value){
        data.put(key, value);
    }

    public Object get(String key){
        return data.get(key);
    }
    public void setCharacterEncoding(String encoding) {
        this.characterEncoding = encoding;
    }
    public void setSession(Object session){
        this.session= session;
    }

    public Object getSession(){
        return  session;
    }
}
