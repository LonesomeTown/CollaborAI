package com.smu.apis;

import com.smu.data.entity.ApiKey;
import com.smu.repository.ApiKeyRepository;
import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TextCompletionApi
 *
 * @author T.W 2/23/23
 */
@Service
public class TextCompletionApi {
    private final ApiKeyRepository apiKeyRepository;
    @Value("${api.completion-model}")
    private String model;
    @Value("${api.completion-temperature}")
    private double temperature;

    public TextCompletionApi(ApiKeyRepository apiKeyRepository) {
        this.apiKeyRepository = apiKeyRepository;
    }

    public String getCompletionText(String userInput) {
        ApiKey apiKey = apiKeyRepository.findAccessKeyByIsActive(true);
        if (null == apiKey) {
            return "";
        }
        String accessKey = apiKey.getAccessKey();
        OpenAiService openAiService = new OpenAiService(accessKey);
        CompletionRequest completionRequest = CompletionRequest.builder()
                .prompt(userInput)
                .model(model)
                .temperature(temperature)
                .build();
        List<CompletionChoice> choices = openAiService.createCompletion(completionRequest).getChoices();
        return choices.get(0).getText().replace("\n\n", "");
    }


}
