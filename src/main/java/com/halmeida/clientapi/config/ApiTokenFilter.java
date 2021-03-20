package com.halmeida.clientapi.config;

import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ApiTokenFilter implements Filter {

    private final AppProperties appProperties;

    public ApiTokenFilter(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        // if no key provided to the app this function is disabled
        final String appApiKey = appProperties.getApiKey();
        if (appApiKey == null || appApiKey.isBlank()) {
            chain.doFilter(req, res);
            return;
        }

        // Check if the provided app keys matches
        final HttpServletRequest request = (HttpServletRequest) req;
        final String apiKey = request.getHeader("api_key");

        if (apiKey == null || !apiKey.equals(appApiKey)) {
            final HttpServletResponse response = (HttpServletResponse) res;
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }
        chain.doFilter(req, res);
    }
}
