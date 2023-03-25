package com.smu.service.impl;

import com.smu.data.entity.Message;
import com.smu.data.entity.User;
import com.smu.repository.MessageRepository;
import com.smu.service.MessageService;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.stream.Stream;

/**
 * MessageServiceImpl
 *
 * @author T.W 3/24/23
 */
@Service
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;

    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public Message getById(ObjectId id) {
        return messageRepository.findById(id.toString()).orElseThrow();
    }

    @Override
    public Stream<Message> findAllByTopicSince(String topic, Instant since) {
        return messageRepository.findMessagesByTopicEqualsAndCreateTimeAfter(topic, since).stream();
    }

    @Override
    public void save(Message message) {
        messageRepository.save(message);
    }

    @Override
    public void delete(Message message) {
        messageRepository.delete(message);
    }
}
