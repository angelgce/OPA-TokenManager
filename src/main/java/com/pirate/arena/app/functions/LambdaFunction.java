package com.pirate.arena.app.functions;

import com.pirate.arena.app.request.RequestToken;
import com.pirate.arena.app.services.ServiceToken;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;

@Configuration
@RequiredArgsConstructor
public class LambdaFunction {

    private final ServiceToken serviceToken;

    @Bean
    public Function<RequestToken, ResponseEntity<Map<String, Boolean>>> validateToken() {
        return value -> ResponseEntity.ok()
                .body(Collections.singletonMap("data", serviceToken.isTokenValid(value)));
    }


}
