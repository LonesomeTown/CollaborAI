package com.smu.repository;

import com.smu.data.entity.ApiKey;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * ApiKeyRepository
 *
 * @author T.W 2/23/23
 */
public interface ApiKeyRepository extends MongoRepository<ApiKey,String> {
    ApiKey findByIsActive(Boolean isActive);

    ApiKey findAccessKeyByIsActive(Boolean isActive);
}
