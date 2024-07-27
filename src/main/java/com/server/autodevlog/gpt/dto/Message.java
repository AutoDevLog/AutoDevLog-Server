package com.server.autodevlog.gpt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private String role;
    private String content;

    public static Message createSystemMessage(String format){
        return new Message("system",format);
    }

    public static Message createUserMessage(String format,String content){
        return new Message("user",format + content);
    }

    public static Message createSelfAskMessage(String content) {
        return new Message("user",content);
    }
    public static Message createSelfAnswerMessage(String content) {
        return new Message("user",content);
    }

    @Override
    public String toString() {
        return "Message{" +
                "role='" + role + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
