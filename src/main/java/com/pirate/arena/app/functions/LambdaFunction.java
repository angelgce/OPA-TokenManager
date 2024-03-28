package com.pirate.arena.app.functions;

import com.pirate.arena.app.request.RequestAmazon;
import com.pirate.arena.app.services.ServiceToken;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
@RequiredArgsConstructor
public class LambdaFunction {

    private final ServiceToken serviceToken;

    @Bean
    public Function<RequestAmazon, String> validateToken() {
        return value -> serviceToken.isTokenValid(value);
    }

}
