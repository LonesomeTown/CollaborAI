package com.smu.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.smu.data.enums.Role;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;
import javax.validation.constraints.Email;

@Data
public class PersonDto implements Serializable {
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
    private Set<Role> roles;
    private boolean isSponsor;
    private boolean isActive;
    private byte[] profilePicture;
    @JsonIgnore
    private String hashedPassword;
}
