package com.smu.service;

import com.smu.data.entity.Message;
import com.smu.data.entity.User;
import com.vaadin.collaborationengine.CollaborationMessage;
import com.vaadin.collaborationengine.CollaborationMessagePersister;
import com.vaadin.collaborationengine.UserInfo;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.bson.types.ObjectId;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.stream.Stream;

@SpringComponent
public class MyMessagePersister implements CollaborationMessagePersister {

    private final MessageService messageService; 
    private final UserService userService; 

    public MyMessagePersister(MessageService messageService,
            UserService userService) {
        this.messageService = messageService;
        this.userService = userService;
    }

    @Override
    public Stream<CollaborationMessage> fetchMessages(FetchQuery query) {
        return messageService
                .findAllByTopicSince(query.getTopicId(), query.getSince())
                .map(messageEntity -> {
                    User author = messageEntity.getSender();
                    UserInfo userInfo = new UserInfo(author.getId().toString(),
                            author.getName(), "");

                    return new CollaborationMessage(userInfo,
                            messageEntity.getText(), messageEntity.getCreateTime());
                });
    }

    @Override
    public void persistMessage(PersistRequest request) {
        CollaborationMessage message = request.getMessage();
        Message messageEntity = new Message();
        if("Visitor".equals(message.getUser().getName())||null == message.getUser().getId()){
            return;
        }
        Optional<User> user = userService.get(new ObjectId(message.getUser().getId()));
        messageEntity.setTopic(request.getTopicId());
        messageEntity.setText(message.getText());
        user.ifPresent(messageEntity::setSender);

        messageEntity.setCreateTime(Instant.now());
        messageService.save(messageEntity);
    }
}