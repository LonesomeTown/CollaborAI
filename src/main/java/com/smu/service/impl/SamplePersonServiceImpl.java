package com.smu.service.impl;

import com.smu.data.entity.Person;
import com.smu.repository.SamplePersonRepository;
import com.smu.service.SamplePersonService;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SamplePersonServiceImpl implements SamplePersonService {

    private final SamplePersonRepository repository;

    public SamplePersonServiceImpl(SamplePersonRepository repository) {
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
