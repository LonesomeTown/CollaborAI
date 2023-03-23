package com.smu.repository;

import com.smu.data.entity.ApiSetting;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * ApiSettingRepository
 *
 * @author T.W 3/22/23
 */
public interface ApiSettingRepository extends MongoRepository<ApiSetting,String> {
}
