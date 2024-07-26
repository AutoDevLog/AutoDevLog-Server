package com.server.autodevlog.gpt.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ChatGptRequestDto {
    private String model;
    private List<Message> messages = new ArrayList<>();
    private final double temperature = 0.2;

    private static final String language = "language : Korean";
    private static final String responseType = "Responding in Text type";
    private static final String blogStyle = "write to post troubleshooting blog, citation official documentation";
    private static final String exampleCode = "present example code and solution for the requested issue";
    private static final String writingStyle =  "글을 작성할때 평서체를 사용해서 글을 작성해줘";
    private static final String writingFormat = "Format: \" #이슈 정의 \"\n, \" #issue example code \" \n,\"#원인 추론 \" \n,\"#해결 방법\"\n, \"#solution example code \" ";

    private static final String issue = "start keyword(issue): ";
    private static final String inference = "middle keyword(inference): ";
    private static final String solution = "end keyword(solution): ";


    @Builder
    public ChatGptRequestDto(String model, UserRequestDto dto) {
        this.model = model;
        addSystemMessages();
        addUserMessages(dto);
    }

    private void addSystemMessages(){
        messages.add(Message.createSystemMessage(language));
        messages.add(Message.createSystemMessage(responseType));
        messages.add(Message.createSystemMessage(blogStyle));
        messages.add(Message.createSystemMessage(exampleCode));
        messages.add(Message.createSystemMessage(writingStyle));
        messages.add(Message.createSystemMessage(writingFormat));
    }

    private void addUserMessages(UserRequestDto dto){
        messages.add(Message.createUserMessage(issue,dto.getIssue()));
        messages.add(Message.createUserMessage(inference, dto.getInference()));
        messages.add(Message.createUserMessage(solution, dto.getSolution()));
    }
}