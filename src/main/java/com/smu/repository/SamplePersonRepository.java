package com.smu.repository;

import com.smu.data.entity.Person;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SamplePersonRepository
        extends
        MongoRepository<Person, ObjectId> {

}
