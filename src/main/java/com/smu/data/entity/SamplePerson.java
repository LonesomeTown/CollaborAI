package com.smu.data.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.validation.constraints.Email;

@Data
public class SamplePerson implements Serializable {
    @MongoId
    private ObjectId id;
    private String firstName;
    private String lastName;
    @Email
    private String email;
    private String phone;
    private LocalDate dateOfBirth;
    private String occupation;
    private String role;
    private boolean important;

}
