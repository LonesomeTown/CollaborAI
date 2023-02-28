package com.smu.service;

import com.smu.data.entity.Person;

import java.util.Optional;

import com.smu.repository.SamplePersonRepository;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface SamplePersonService {

    Optional<Person> get(ObjectId id);

    Person update(Person entity);

    void delete(ObjectId id);

    Page<Person> list(Pageable pageable);

    int count();
}
