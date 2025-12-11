package com.vasmax.auth;

import io.micronaut.core.annotation.Introspected;
import lombok.Data;

@Introspected
@Data
public class AuthResponse {
    private String access_token;
    private String refresh_token;
    private String id_token;
    private String token_type;
    private String scope;
    private Long expires_in;
    private String user_id;
    private String state;
}
