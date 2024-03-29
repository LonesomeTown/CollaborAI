package com.smu.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.validation.constraints.Email;
import java.time.LocalDate;

/**
 * Person
 *
 * @author T.W 3/9/23
 */
@Data
public class Person {
    @MongoId
    private ObjectId id;
    @JsonIgnore
    private ObjectId userId;
    private String firstName;
    private String lastName;
    @Email
    private String email;
    private String phone;
    private LocalDate dateOfBirth;
    private String accessKey;
    private boolean isSponsor;
}
