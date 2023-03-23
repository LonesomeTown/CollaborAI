package com.smu.service;

import com.smu.data.entity.ApiSetting;

import java.util.Optional;

/**
 * ApiSettingService
 *
 * @author T.W 3/22/23
 */
public interface ApiSettingService {
    Optional<ApiSetting> findByName(String apiName);
    ApiSetting save(ApiSetting apiSetting);
    ApiSetting update(ApiSetting apiSetting);
}
