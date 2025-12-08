package com.vasmax.auth;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.annotation.QueryValue;
import lombok.extern.slf4j.Slf4j;

@Controller("/authRedirect")
@Slf4j
public class AuthController {
    @Get
    @Produces(MediaType.TEXT_PLAIN)
    String authRedirect(@QueryValue String payload) {
        log.info(payload);
        return "Hello World";
    }
}
