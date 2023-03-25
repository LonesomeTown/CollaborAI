package com.smu.apis;

import com.smu.data.entity.ApiKey;
import com.smu.data.entity.ApiSetting;
import com.smu.data.enums.ApiTypes;
import com.smu.repository.ApiKeyRepository;
import com.smu.service.ApiSettingService;
import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.image.CreateImageRequest;
import com.theokanning.openai.image.Image;
import com.theokanning.openai.service.OpenAiService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * TextCompletionApi
 *
 * @author T.W 2/23/23
 */
@Service
@Slf4j
public class OpenApi {
    private final ApiKeyRepository apiKeyRepository;
    private final ApiSettingService apiSettingService;

    public OpenApi(ApiKeyRepository apiKeyRepository, ApiSettingService apiSettingService) {
        this.apiKeyRepository = apiKeyRepository;
        this.apiSettingService = apiSettingService;
    }

    public String getCompletionText(String userInput) {
        Optional<ApiSetting> apiSetting = apiSettingService.findByName(ApiTypes.IMAGE_GENERATION.name());
        String accessKey = getAccessKey();
        if (StringUtils.isEmpty(accessKey)) {
            return "";
        }
        OpenAiService openAiService = new OpenAiService(accessKey);
        log.info("Start requesting Text-Completion API, input text is: {}", userInput);
        CompletionRequest completionRequest = CompletionRequest.builder()
                .prompt(userInput)
                .model(apiSetting.isPresent()?apiSetting.get().getModel():"gpt-3.5-turbo")
                .temperature(apiSetting.isPresent()?apiSetting.get().getTemperature():0.5)
                .build();
        List<CompletionChoice> choices = openAiService.createCompletion(completionRequest).getChoices();
        log.info("End requesting Text-Completion API, response is: {}", choices.toString());
        return choices.get(0).getText().replace("\n\n", "");
    }

    public String getChatGPTResponse(String userInput) {
        Optional<ApiSetting> apiSetting = apiSettingService.findByName(ApiTypes.IMAGE_GENERATION.name());
        String accessKey = getAccessKey();
        if (StringUtils.isEmpty(accessKey)) {
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
                .model(apiSetting.isPresent()?apiSetting.get().getModel():"gpt-3.5-turbo")
                .temperature(apiSetting.isPresent()?apiSetting.get().getTemperature():0.5)
                .maxTokens(apiSetting.isPresent()?apiSetting.get().getMaxToken():500)
                .build();
        List<ChatCompletionChoice> choices = openAiService.createChatCompletion(completionRequest).getChoices();
        log.info("End requesting Chat-Completion API, response is: {}", choices.toString());
        return choices.get(0).getMessage().getContent().replace("\n\n", "");
    }

    public String getImageGeneratorResponse(String userInput) {
        Optional<ApiSetting> apiSetting = apiSettingService.findByName(ApiTypes.IMAGE_GENERATION.name());
        String accessKey = getAccessKey();
        if (StringUtils.isEmpty(accessKey)) {
            return "";
        }
        OpenAiService openAiService = new OpenAiService(accessKey);
        log.info("Start requesting Image Generation API, input text is: {}", userInput);
        String responseFormat = apiSetting.isPresent() ? apiSetting.get().getResponseFormat() : "url";
        CreateImageRequest createImageRequest = CreateImageRequest.builder()
                .prompt(userInput)
                .size(apiSetting.isPresent() ? apiSetting.get().getImageSize() : "256x256")
                .responseFormat(responseFormat)
                .build();
        List<Image> data = openAiService.createImage(createImageRequest).getData();
        log.info("End requesting Image-Generation API, response is: {}", data.toString());
        return "b64_json".equals(responseFormat) ? data.get(0).getB64Json() : data.get(0).getUrl();
    }


    private String getAccessKey() {
        ApiKey apiKey = apiKeyRepository.findAccessKeyByIsActive(true);
        if (null == apiKey) {
            return "";
        }
        return apiKey.getAccessKey();
    }
}
