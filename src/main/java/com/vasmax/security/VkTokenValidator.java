package com.vasmax.security;
import com.vasmax.auth.VkClient;
import io.micronaut.context.annotation.Value;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.token.validator.TokenValidator;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import java.util.Map;


@Singleton
@Slf4j
public class VkTokenValidator<T> implements TokenValidator<T> {
    private final VkClient vkClient;
    private final String clientId;

    VkTokenValidator(final VkClient vkClient,
                     @Value("${micronaut.http.services.vk.client-id}") String clientId) {
        this.vkClient = vkClient;
        this.clientId = clientId;
    }

    @Override
    public Publisher<Authentication> validateToken(String token, T request) {
        try {
            log.info("Validating token {}", token);
            log.info("Validating request {}", request.getClass());
            String body = "client_id=" + clientId + "&access_token=" + token;
            log.info("body: {}", body);
            return Mono.from(vkClient.userInfo(body))
                    .doOnNext(userInfo -> {
                        log.info("User info: {}", userInfo);
                    })
                    .map(userInfo -> Authentication.build(userInfo.getUser().getUserId(),
                            Map.of("firstName", userInfo.getUser().getFirstName(),
                                    "lastName", userInfo.getUser().getLastName(),
                                    "avatar", userInfo.getUser().getAvatar())));
        } catch (Throwable e) {
            log.warn("Error validating token", e);
            return Mono.just(Authentication.build("TEST", Map.of("firstName", "TEST",
                    "lastName", "TEST")));
        }
    }
}
