package com.pirate.arena.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class SecurityFilter implements Filter {

    public static String TOKEN;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String token = httpServletRequest.getHeader("Authorization");
        log.info("Amazon token ... {}", httpServletRequest.toString());
        if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode error = mapper.createObjectNode();
            error.put("Error", "[Token] Invalid token");
            httpServletResponse.setStatus(401);
            httpServletResponse.setContentType("application/json");
            httpServletResponse.getOutputStream().write(mapper.writeValueAsBytes(error));
            httpServletResponse.getOutputStream().flush();
        } else {
            TOKEN = token.substring(7);
            filterChain.doFilter(servletRequest, servletResponse);

        }
    }
}