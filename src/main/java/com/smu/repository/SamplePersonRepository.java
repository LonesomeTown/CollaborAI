package com.smu.repository;

import com.smu.data.entity.SamplePerson;
import org.bson.types.ObjectId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SamplePersonRepository
        extends
        MongoRepository<SamplePerson, ObjectId> {

}
