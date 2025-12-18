package com.vasmax.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Data;

@Data
@Serdeable
public class VkUser {
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("avatar")
    private String avatar;
    @JsonProperty("sex")
    private Integer sex;
    @JsonProperty("varified")
    private Boolean varified;
    @JsonProperty("birthday")
    private String birthday;
}
