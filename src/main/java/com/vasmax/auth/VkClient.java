package com.vasmax.auth;

import com.vasmax.security.VkUserInfo;
import io.micronaut.core.async.annotation.SingleResult;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;
import org.reactivestreams.Publisher;

@Client("vk")
public interface VkClient {
    @Post(uri = "/oauth2/auth",
            produces = MediaType.APPLICATION_FORM_URLENCODED, // на входе форма с параметрами
            consumes = MediaType.APPLICATION_JSON)
    @SingleResult
    Publisher<String> auth(@Body String body);

    @Post(uri = "/oauth2/user_info",
            produces = MediaType.APPLICATION_FORM_URLENCODED, // на входе форма с параметрами
            consumes = MediaType.APPLICATION_JSON)
    @SingleResult
    Publisher<VkUserInfo> userInfo(@Body String body);
}
