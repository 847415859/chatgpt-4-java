package com.tk;

import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.image.CreateImageRequest;
import com.theokanning.openai.image.Image;
import com.theokanning.openai.image.ImageResult;
import okhttp3.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Date : 2023/03/27 17:57
 * @Auther : tiankun
 */
public class SendClient {

    public static void main(String[] args) throws IOException {
        // sendMsg();
        // createImage();
        createImage2();
    }

    /**
     * 发送消息
     */
    public static void sendMsg() {
        // 消息列表
        List<ChatMessage> list = new ArrayList<>();

        // 给chatGPT定义一个身份，是一个助手
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setRole("system");
        chatMessage.setContent("You are a helpful assistant.");
        list.add(chatMessage);

        // 定义一个用户身份，content是用户写的内容
        ChatMessage userMessage = new ChatMessage();
        userMessage.setRole("user");
        userMessage.setContent("SpringMVC");
        list.add(userMessage);

        ChatCompletionRequest request = ChatCompletionRequest.builder()
                .messages(list)
                .model("gpt-3.5-turbo")
                .build();
        OpenAiService service = new OpenAiService("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");

        // chatCompletion 对象就是chatGPT响应的数据了
        ChatCompletionResult chatCompletion = service.createChatCompletion(request);
        System.out.println("chatCompletion.toString() = " + chatCompletion.toString());
    }

    /**
     * 发送消息
     */
    public static void createImage() throws UnsupportedEncodingException {
        CreateImageRequest createImageRequest = CreateImageRequest.builder()
                .user("tk")
                .prompt("生产刘亦菲照片")
                .size("512×512")
                .n(1)
                .responseFormat("url")
                .build();

        String token = "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";
        OpenAiService service = new OpenAiService(new String(token.getBytes(),"UTF-8"), Duration.ofMinutes(2));
        // chatCompletion 对象就是chatGPT响应的数据了
        ImageResult imageResult = service.createImage(createImageRequest);
        List<Image> imageList = imageResult.getData();
        for (Image image : imageList) {
            System.out.println("=====================");
            System.out.println("image.getUrl() = " + image.getUrl());
            System.out.println("image.getB64Json() = " + image.getB64Json());
            System.out.println("=====================");
        }
        // System.out.println("chatCompletion.toString() = " + chatCompletion.toString());
    }


    public static void createImage2() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\r\n    \"prompt\": \"SpringCloud Architecture Picture\",\r\n    \"size\": \"1024x1024\",\r\n    \"response_format\": \"url\",\r\n    \"n\": 1\r\n}");
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/images/generations")
                .method("POST", body)
                .addHeader("Authorization", "Bearer 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")
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        System.out.println("response.body() = " + new String(response.body().bytes()));
    }
}
