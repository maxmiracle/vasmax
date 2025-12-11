package com.vasmax.auth;

import io.micronaut.context.annotation.Value;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Controller
@Slf4j
public class AuthController {

    private final VkClient vkClient;

    //@Value("${micronaut.http.services.vk.client-id}")
    private final String clientId;  //"54368087";

    AuthController(VkClient vkClient,
                   @Value("${micronaut.http.services.vk.client-id}") String clientId) {
        this.vkClient = vkClient;
        this.clientId = clientId;
    }

    @Get("/tokenAcquire")
    public HttpResponse<?> getToken(HttpRequest<?> request) {
        request.getCookies().forEach((name, cookie) -> {
            log.info("Cookie name: {} = {}", name, cookie.getValue());
        });
        request.getParameters().forEach((name, value) -> {
            log.info("Parameter name: {} = {}", name, value);
        });
        request.getHeaders().forEach((name, value) -> {
            log.info("Header name: {} = {}", name, value);
        });
        return HttpResponse.ok("ok");
    }

    @Get("/auth")
    public Mono<GetTokenResponse> auth(HttpRequest<?> request) {
        request.getCookies().forEach((name, cookie) -> {
            log.info("Cookie name: {} = {}", name, cookie.getValue());
        });
        request.getParameters().forEach((name, value) -> {
            log.info("Parameter name: {} = {}", name, value);
        });
        request.getHeaders().forEach((name, value) -> {
            log.info("Header name: {} = {}", name, value);
        });

            String code = request.getParameters().get("code");
            String state = request.getParameters().get("state");
            String device_id = request.getParameters().get("device_id");
            Optional<String> codeVerifier = request.getCookies().get("vkid_sdk:codeVerifier",  String.class);
        String redirectUri = "https://maxmiracle.ru/auth";
        log.info("RedirectAuth code: {}, codeVerifier: {}, device_id: {}, state: {}, redirectUri: {}", code, codeVerifier, device_id, state, redirectUri);
        String body = "grant_type=authorization_code&" +
                "code_verifier=" + codeVerifier.orElse("codeVerifier") + "&" +
                "redirect_uri=" + URLEncoder.encode(redirectUri, StandardCharsets.UTF_8) + "&" +
                "code=" + code + "&" +
                "client_id=" + clientId + "&" +
                "device_id=" + device_id + "&" +
                "state=" + state;
        log.info("body: {}", body);
        return Mono.from(vkClient.auth( body))
                .doOnNext(log::info)
                .map(GetTokenResponse::new);
    }

}
