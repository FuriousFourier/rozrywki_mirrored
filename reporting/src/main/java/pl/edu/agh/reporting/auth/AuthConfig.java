package pl.edu.agh.reporting.auth;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class AuthConfig {
    @Getter
    @Value(value = "${com.auth0.domain}")
    private String domain;

    @Getter
    @Value(value = "${com.auth0.clientId}")
    private String clientId;

    @Getter
    @Value(value = "${com.auth0.clientSecret}")
    private String clientSecret;

    @Getter
    @Value(value = "${com.auth0.protectedEndpoints}")
    private String[] protectedEndpoints;

    @Bean
    public FilterRegistrationBean filterRegistration() {
        final FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new Auth0Filter());
        registration.addUrlPatterns(protectedEndpoints);
        registration.setName(Auth0Filter.class.getSimpleName());
        return registration;
    }
}
