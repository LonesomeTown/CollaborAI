package com.smu.service;

import com.smu.data.entity.Person;
import com.smu.data.entity.PersonDto;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface PersonService {

    void save(PersonDto entity);

    PersonDto get(ObjectId id);

    PersonDto update(PersonDto entity);

    void delete(ObjectId id);

    List<PersonDto> list();

    int count();

    PersonDto getByUserId(ObjectId userId);
}
