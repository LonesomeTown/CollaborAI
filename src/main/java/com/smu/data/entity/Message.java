package com.smu.data.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.Instant;
import java.time.LocalDateTime;

/**
 * Message
 *
 * @author T.W 3/24/23
 */
@Data
public class Message {
    @MongoId
    private ObjectId id;
    private String topic;
    private Instant createTime;
    private User sender;
    private String text;

}
