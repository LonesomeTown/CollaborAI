package com.smu.repository;

import com.smu.data.entity.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.Instant;
import java.util.List;

/**
 * MessageRepository
 *
 * @author T.W 3/24/23
 */
public interface MessageRepository extends MongoRepository<Message,String> {
    List<Message> findMessagesByTopicEqualsAndCreateTimeAfter(String topic, Instant createTime);
}
