package com.smu.service;

import com.smu.data.entity.Message;
import com.smu.data.entity.User;
import org.bson.types.ObjectId;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

/**
 * MessageService
 *
 * @author T.W 3/24/23
 */
public interface MessageService {
    Message getById(ObjectId id);
    Stream<Message> findAllByTopicSince(String topic, Instant since);
    void save(Message message);
    void delete(Message message);
}
