package com.server.autodevlog.measurement.dto;

import com.server.autodevlog.gpt.dto.Message;
import com.server.autodevlog.gpt.dto.UserRequestDto;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MeasurementGptRequestDto {

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
    public MeasurementGptRequestDto(String model, MeasurementRequestDto dto) {
        this.model = model;
        addSystemMessages();
        addSelfAskMessages();
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

    private void addUserMessages(MeasurementRequestDto dto){
        messages.add(Message.createUserMessage(issue,dto.getIssue()));
        messages.add(Message.createUserMessage(inference, dto.getInference()));
        messages.add(Message.createUserMessage(solution, dto.getSolution()));
    }

    private void addSelfAskMessages() {
        final String selfAskStart = "Does it need follow up?";
        final String selfAskStartAnswer = "yes.";
        final String selfAskExample = "self-Ask Example: ";
        final String selfAsk1 = "Q: Why does Java's ArrayNPE problem occur?";
        final String selfAskAnswer1 = "A: NPE problems occur when accessing uninitialized fields. So Java's ArrayNPE problem occurs when accessing an uninitialized List field";
        final String selfAsk2 = "Q: Please provide example code for this issue.";
        final String selfAskAnswer2 = "A: ```java\n" +
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
                "```"
                +"\n" +
                "The 'ArrayList<String> list' field has been declared but not initialized.";
        final String selfAsk3 = "Q: How should I modify the code in the example above to resolve the ArrayNPE issue?";
        final String selfAskAnswer3 = "A: Java's ArrayNPE occurs when accessing an uninitialized List field." +"This can be solved by initializing the List field, which is an uninitialized field, with an instance of List's implementation class like 'ArrayList'";
        final String selfAsk4= "Q: Please provide code that applies the solution to the example code.";
        final String selfAskAnswer4 = "A: ```java\n" +
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
                "```\n" + "As in the example above, you can prevent the ArrayNPE problem from occurring by initializing the List field through new ArrayList<>();.";
        final String selfAskEnd = "self-Ask Example End.";

        messages.add(Message.createSelfAskMessage(selfAskStart));
        messages.add(Message.createSelfAnswerMessage(selfAskStartAnswer));
        messages.add(Message.createSelfAskMessage(selfAskExample));
        messages.add(Message.createSelfAskMessage(selfAsk1));
        messages.add(Message.createSelfAnswerMessage(selfAskAnswer1));
        messages.add(Message.createSelfAskMessage(selfAsk2));
        messages.add(Message.createSelfAnswerMessage(selfAskAnswer2));
        messages.add(Message.createSelfAskMessage(selfAsk3));
        messages.add(Message.createSelfAnswerMessage(selfAskAnswer3));
        messages.add(Message.createSelfAskMessage(selfAsk4));
        messages.add(Message.createSelfAnswerMessage(selfAskAnswer4));
        messages.add(Message.createSelfAskMessage(selfAskEnd));
    }
}
