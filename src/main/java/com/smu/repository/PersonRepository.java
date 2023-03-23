package com.smu.repository;

import com.smu.data.entity.Person;
import com.smu.data.entity.PersonDto;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PersonRepository
        extends
        MongoRepository<Person, ObjectId> {
    Person findFirstByUserId(ObjectId id);

}
