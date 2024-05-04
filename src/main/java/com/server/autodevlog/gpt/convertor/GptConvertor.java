package com.server.autodevlog.gpt.convertor;

public class GptConvertor {
    public static String deleteChangeLine(String target){
        return target.replace("\n",""); // gpt api 응답 바디 내용중"\n" 삭제
    }
}
