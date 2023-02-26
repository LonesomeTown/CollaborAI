package com.smu.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

/**
 * ApiKey
 *
 * @author T.W 2/23/23
 */
@Data
public class ApiKey {
    @JsonIgnore
    private String accessKey;
    private LocalDateTime createDate;
    private LocalDateTime expireTime;
    /**
     * The total number of calls that can be made
     */
    private Long totalUsage;
    /**
     * The left number of calls that can be made
     */
    private Long leftUsage;
    private ObjectId sponsorId;
    /**
     * Weather this access key is in used or not
     */
    private Boolean isActive;

}
