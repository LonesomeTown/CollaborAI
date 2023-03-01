package com.smu.apis;

import com.smu.data.entity.ApiKey;
import com.smu.repository.ApiKeyRepository;
import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
        ApiKey apiKey = apiKeyRepository.findAccessKeyByIsActive(true);
        if (null == apiKey) {
            return "";
        }
        String accessKey = apiKey.getAccessKey();
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


}
