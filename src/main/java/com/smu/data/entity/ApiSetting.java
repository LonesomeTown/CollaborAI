package com.smu.data.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.MongoId;

/**
 * ApiSetting
 *
 * @author T.W 3/22/23
 */
@Data
public class ApiSetting {
    @MongoId
    private String apiName;
    private String model;
    private Double temperature;
    private Integer maxToken;
    private String imageSize;
    private String responseFormat;
}
