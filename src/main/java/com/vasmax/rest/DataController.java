package com.vasmax.rest;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import lombok.extern.slf4j.Slf4j;

import java.security.Principal;

@Controller("/data")
@Slf4j
public class DataController {

    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Get("/hello")
    public String hello(Principal principal) {
        String name;
        if (principal instanceof Authentication) {
            name = ((Authentication) principal).getAttributes().get("firstName").toString();
        } else {
            name = principal.getName();
        }
        return  "{ \"text\": \"Здравствуйте, " + name + "!\"}";
    }

}
