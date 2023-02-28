package com.smu.service;

import com.smu.apis.TextCompletionApi;
import com.smu.data.constant.ApiTypes;
import com.vaadin.collaborationengine.MessageManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * AsyncService
 *
 * @author T.W 2/28/23
 */
@Service
@Slf4j
public class AsyncService {
    private final TextCompletionApi textCompletionApi;

    public AsyncService(TextCompletionApi textCompletionApi) {
        this.textCompletionApi = textCompletionApi;
    }

    @Async
    public void generateAutoReply(MessageManager messageManager, String incomingMsg, String tabName) {
        String reply = "";
        if (ApiTypes.TEXT_COMPLETION.equals(tabName)) {
            reply = textCompletionApi.getCompletionText(incomingMsg);
        }
        if (StringUtils.isNotEmpty(reply)) {
            try {
                messageManager.submit(reply);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }
}
