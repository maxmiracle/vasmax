package com.vasmax.auth;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.MediaType;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;

import java.net.URI;


@Singleton
public class VkClientLowLevel {
    private final HttpClient httpClient;

    public VkClientLowLevel(@Client(id="vk") HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public Publisher<String> auth(String body){
        URI uri = URI.create("/oauth2/auth");
        HttpRequest<?> request = HttpRequest.POST(uri, body)
                .header("Content-Type", MediaType.APPLICATION_FORM_URLENCODED);
        return httpClient.retrieve(request);
    }
}
