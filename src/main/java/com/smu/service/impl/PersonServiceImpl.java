package com.smu.service.impl;

import com.smu.data.entity.Person;
import com.smu.data.entity.PersonDto;
import com.smu.data.entity.User;
import com.smu.repository.PersonRepository;
import com.smu.service.PersonService;
import com.smu.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository repository;
    private final UserService userService;

    public PersonServiceImpl(PersonRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    @Override
    public void save(PersonDto entity) {
        User user = this.convert(entity);
        User save = this.userService.save(user);
        entity.setUserId(save.getId());
        Person person = this.convertToPerson(entity);
        this.repository.save(person);
    }

    public PersonDto get(ObjectId id) {
        Optional<Person> personDtoOptional = repository.findById(id);
        if (personDtoOptional.isPresent()) {
            Person person = personDtoOptional.get();
            return convert(person);
        }
        return null;
    }

    public PersonDto update(PersonDto personDto) {
        User user = this.convert(personDto);
        User save = this.userService.save(user);
        personDto.setUserId(save.getId());
        Person person = this.convertToPerson(personDto);
        return this.convert(repository.save(person));
    }

    public void delete(ObjectId id) {
        repository.deleteById(id);
    }

    public List<PersonDto> list() {
        List<Person> all = repository.findAll();
        List<PersonDto> personDtos = new ArrayList<>();
        all.forEach(p -> personDtos.add(this.convert(p)));
        return personDtos;
    }

    public int count() {
        return (int) repository.count();
    }

    @Override
    public PersonDto getByUserId(ObjectId userId) {
        return this.convert(this.repository.findFirstByUserId(userId));
    }

    private PersonDto convert(Person person) {
        if (null == person) {
            return null;
        }
        PersonDto personDto = new PersonDto();
        BeanUtils.copyProperties(person, personDto);
        ObjectId userId = person.getUserId();
        if(null != userId){
            Optional<User> byId = userService.get(userId);
            if (byId.isPresent()) {
                User user = byId.get();
                personDto.setRoles(user.getRoles());
                personDto.setActive(user.isActive());
            }
        }
        return personDto;
    }

    private User convert(PersonDto entity){
        User user = new User();
        if(null != entity.getUserId()){
            user.setId(entity.getUserId());
        }
        user.setActive(true);
        user.setName(entity.getFirstName() + " " + entity.getLastName());
        user.setRoles(entity.getRoles());
        user.setUsername(entity.getEmail());
        if(StringUtils.isEmpty(entity.getHashedPassword())){
            user.setHashedPassword(entity.getFirstName() + entity.getLastName());
        }else {
            user.setHashedPassword(entity.getHashedPassword());
        }
        user.setActive(true);
        //TODO user.setProfilePicture();
        return user;
    }

    private Person convertToPerson(PersonDto entity){
        Person person = new Person();
        if(null != entity.getId()){
            person.setId(entity.getId());
        }
        person.setAccessKey(entity.getAccessKey());
        person.setEmail(entity.getEmail());
        person.setDateOfBirth(entity.getDateOfBirth());
        person.setFirstName(entity.getFirstName());
        person.setPhone(entity.getPhone());
        person.setLastName(entity.getLastName());
        person.setSponsor(entity.isSponsor());
        person.setUserId(entity.getUserId());
        return person;
    }

}
