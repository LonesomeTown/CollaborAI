package com.smu.apis;

import com.smu.data.entity.ApiKey;
import com.smu.repository.ApiKeyRepository;
import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * TextCompletionApi
 *
 * @author T.W 2/23/23
 */
@Service
@Slf4j
public class ChatGptApi {
    private final ApiKeyRepository apiKeyRepository;
    @Value("${api.completion-model}")
    private String model;
    @Value("${api.completion-temperature}")
    private double temperature;

    public ChatGptApi(ApiKeyRepository apiKeyRepository) {
        this.apiKeyRepository = apiKeyRepository;
    }

    public String getCompletionText(String userInput) {
        String accessKey = getAccessKey();
        if(StringUtils.isEmpty(accessKey)){
            return "";
        }
        OpenAiService openAiService = new OpenAiService(accessKey);
        log.info("Start requesting Text-Completion API, input text is: {}", userInput);
        CompletionRequest completionRequest = CompletionRequest.builder()
                .prompt(userInput)
                .model(model)
                .temperature(temperature)
                .build();
        List<CompletionChoice> choices = openAiService.createCompletion(completionRequest).getChoices();
        log.info("End requesting Text-Completion API, response is: {}", choices.toString());
        return choices.get(0).getText().replace("\n\n", "");
    }

    public String getChatGPTResponse(String userInput){
        String accessKey = getAccessKey();
        if(StringUtils.isEmpty(accessKey)){
            return "";
        }
        OpenAiService openAiService = new OpenAiService(accessKey);
        log.info("Start requesting ChatGPT API, input text is: {}", userInput);
        List<ChatMessage> chatMessages = new ArrayList<>();
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setRole(ChatMessageRole.USER.value());
        chatMessage.setContent(userInput);
        chatMessages.add(chatMessage);
        ChatCompletionRequest completionRequest = ChatCompletionRequest.builder()
                .messages(chatMessages)
                .model("gpt-3.5-turbo")
                .temperature(temperature)
                .build();
        List<ChatCompletionChoice> choices = openAiService.createChatCompletion(completionRequest).getChoices();
        log.info("End requesting Chat-Completion API, response is: {}", choices.toString());
        return choices.get(0).getMessage().getContent().replace("\n\n", "");
    }


    private String getAccessKey(){
        ApiKey apiKey = apiKeyRepository.findAccessKeyByIsActive(true);
        if (null == apiKey) {
            return "";
        }
        return apiKey.getAccessKey();
    }
}
