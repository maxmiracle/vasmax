package com.vasmax.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Data;

@Data
@Serdeable
public class VkUserInfo {
    @JsonProperty("user")
    private VkUser user;
}
