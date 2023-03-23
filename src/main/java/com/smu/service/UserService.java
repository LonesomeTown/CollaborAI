package com.smu.service;

import com.smu.data.entity.User;

import java.util.Optional;

import com.smu.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

public interface UserService {
    User save(User entity);

    Optional<User> get(ObjectId id);

    User update(User entity);

    void delete(ObjectId id);

    Page<User> list(Pageable pageable);

    int count();
}
