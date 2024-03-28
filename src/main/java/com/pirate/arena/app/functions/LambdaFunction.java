package com.pirate.arena.app.functions;

import com.pirate.arena.app.SecurityFilter;
import com.pirate.arena.app.request.RequestToken;
import com.pirate.arena.app.services.ServiceToken;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

@Configuration
@RequiredArgsConstructor
public class LambdaFunction {

    private final ServiceToken serviceToken;

    @Bean
    public Filter getFilter() {
        return new SecurityFilter();
    }

    @Bean
    public Supplier<ResponseEntity<String>> validateToken() {
        return () -> serviceToken.isTokenValid(SecurityFilter.TOKEN);
    }


}
