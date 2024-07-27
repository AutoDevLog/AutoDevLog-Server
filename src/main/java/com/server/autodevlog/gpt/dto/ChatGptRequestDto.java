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
    private static final String selfAsk = "When writing an article, use the self-ask technique using the example provided below.";

    private static final String issue = "start keyword(issue): ";
    private static final String inference = "middle keyword(inference): ";
    private static final String solution = "end keyword(solution): ";


    @Builder
    public ChatGptRequestDto(String model, UserRequestDto dto) {
        this.model = model;
        addSystemMessages();
        addSelfAskMessages();
        addUserMessages(dto);
        System.out.println(this); // 테스트
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

    private void addSelfAskMessages() {
        String selfAskStart = "self-Ask Example :";
        String selfAsk1 = "given issue: java,ArrayNPE, Q: Why does Java's ArrayNPE problem occur?";
        String selfAskAnswer1 = "A: Java's ArrayNPE problem occurs when accessing an uninitialized ArrayList, etc.";
        String selfAsk2 = "Q: Please provide example code for this issue.";
        String selfAskAnswer2 = "A: ```java\n" +
                "public class Example {\n" +
                "    private ArrayList<String> list;\n" +
                "\n" +
                "    public void addItem(String item) {\n" +
                "        list.add(item); // 여기서 NPE 발생 가능\n" +
                "    }\n" +
                "\n" +
                "    public static void main(String[] args) {\n" +
                "        Example example = new Example();\n" +
                "        example.addItem(\"Hello\");\n" +
                "    }\n" +
                "}\n" +
                "```";
        String selfAsk3 = "given inference: List field not initialized, Q: What should I do to solve Java's ArrayNPE problem?";
        String selfAskAnswer3 = "A: Java's ArrayNPE occurs when accessing an uninitialized List field.";

        String selfAsk4 = "given solution: This can be solved by initializing the List field, Q: How to solve ArrayNPE issue in Java?";
        String selfAskAnswer4 = "A: When defining a class, the List field requires initialization measures such as new ArrayList<>() at the time of instance creation.";

        String selfAsk5= "Q: Please provide code that applies the solution to the example code.";
        String selfAskAnswer5 = "A: ```java\n" +
                "import java.util.ArrayList;\n" +
                "\n" +
                "public class Example {\n" +
                "    private ArrayList<String> list = new ArrayList<>(); // 초기화\n" +
                "\n" +
                "    public void addItem(String item) {\n" +
                "        list.add(item); // NPE 발생하지 않음\n" +
                "    }\n" +
                "\n" +
                "    public static void main(String[] args) {\n" +
                "        Example example = new Example();\n" +
                "        example.addItem(\"Hello\");\n" +
                "    }\n" +
                "}\n" +
                "```";
        String selfAskEnd = "self-Ask Example End. Please create a response according to the format for the issue, inference, and solution presented in this logical structure.";

        messages.add(Message.createSelfAskMessage(selfAskStart));
        messages.add(Message.createSelfAskMessage(selfAsk1));
        messages.add(Message.createSelfAnswerMessage(selfAskAnswer1));
        messages.add(Message.createSelfAskMessage(selfAsk2));
        messages.add(Message.createSelfAnswerMessage(selfAskAnswer2));
        messages.add(Message.createSelfAskMessage(selfAsk3));
        messages.add(Message.createSelfAnswerMessage(selfAskAnswer3));
        messages.add(Message.createSelfAskMessage(selfAsk4));
        messages.add(Message.createSelfAnswerMessage(selfAskAnswer4));
        messages.add(Message.createSelfAskMessage(selfAsk5));
        messages.add(Message.createSelfAnswerMessage(selfAskAnswer5));
        messages.add(Message.createSelfAskMessage(selfAskEnd));
    }

    @Override
    public String toString() {
        return "ChatGptRequestDto{" +
                "model='" + model + '\'' +
                ", messages=" + messages +
                ", temperature=" + temperature +
                '}';
    }
}