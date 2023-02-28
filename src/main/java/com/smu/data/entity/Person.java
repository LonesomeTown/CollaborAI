package com.smu.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.validation.constraints.Email;

@Data
public class Person implements Serializable {
    @MongoId
    private ObjectId id;
    @JsonIgnore
    private ObjectId userId;
    private String accessKey;
    private String firstName;
    private String lastName;
    @Email
    private String email;
    private String phone;
    private LocalDate dateOfBirth;
    private String role;
    private boolean isSponsor;
}
