package com.smu.service.impl;

import com.smu.data.entity.ApiSetting;
import com.smu.repository.ApiSettingRepository;
import com.smu.service.ApiSettingService;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * ApiSettingServiceImpl
 *
 * @author T.W 3/22/23
 */
@Service
public class ApiSettingServiceImpl implements ApiSettingService {
    private final ApiSettingRepository apiSettingRepository;

    public ApiSettingServiceImpl(ApiSettingRepository apiSettingRepository) {
        this.apiSettingRepository = apiSettingRepository;
    }

    @Override
    public Optional<ApiSetting> findByName(String apiName) {
        return apiSettingRepository.findById(apiName);
    }

    @Override
    public ApiSetting save(ApiSetting apiSetting) {
        return apiSettingRepository.save(apiSetting);
    }

    @Override
    public ApiSetting update(ApiSetting apiSetting) {
        return apiSettingRepository.save(apiSetting);
    }
}
