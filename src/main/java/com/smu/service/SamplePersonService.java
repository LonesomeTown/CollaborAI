package com.smu.service;

import com.smu.data.entity.Person;
import java.util.Optional;

import com.smu.repository.SamplePersonRepository;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SamplePersonService {

    private final SamplePersonRepository repository;

    public SamplePersonService(SamplePersonRepository repository) {
        this.repository = repository;
    }

    public Optional<Person> get(ObjectId id) {
        return repository.findById(id);
    }

    public Person update(Person entity) {
        return repository.save(entity);
    }

    public void delete(ObjectId id) {
        repository.deleteById(id);
    }

    public Page<Person> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public int count() {
        return (int) repository.count();
    }

}
