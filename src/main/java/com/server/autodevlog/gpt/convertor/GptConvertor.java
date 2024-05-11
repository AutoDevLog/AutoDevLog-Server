package com.server.autodevlog.gpt.convertor;

public class GptConvertor {
    // gpt api 응답 바디 내용중"\n" 삭제
    public static String deleteChangeLine(String target) {
        return target.replace("\n","\n");
    }
}
