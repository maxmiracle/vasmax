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

    private final VkClientLowLevel vkClientLowLevel;

    //@Value("${micronaut.http.services.vk.client-id}")
    private final String clientId;  //"54368087";

    AuthController(VkClient vkClient,
                   VkClientLowLevel vkClientLowLevel,
                   @Value("${micronaut.http.services.vk.client-id}") String clientId) {
        this.vkClient = vkClient;
        this.vkClientLowLevel = vkClientLowLevel;
        this.clientId = clientId;
    }

    @Get("/token11")
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

    @Get("/auth11")
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
        String redirectUri = "https://maxmiracle.ru/token";
        log.info("RedirectAuth code: {}, codeVerifier: {}, device_id: {}, state: {}, redirectUri: {}", code, codeVerifier, device_id, state, redirectUri);
        String body = "grant_type=authorization_code&" +
                "code_verifier=" + codeVerifier.orElse("codeVerifier") + "&" +
                "code=" + code + "&" +
                "client_id=" + clientId + "&" +
                "device_id=" + device_id + "&" +
                "state=" + state + "&" +
                "redirect_uri=" + URLEncoder.encode(redirectUri, StandardCharsets.UTF_8);
        log.info("body: {}", body);
        return Mono.from(vkClientLowLevel.auth( body))
                .doOnNext(log::info)
                .map(GetTokenResponse::new);
    }

}
