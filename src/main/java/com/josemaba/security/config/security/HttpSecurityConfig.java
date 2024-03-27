package com.josemaba.security.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.rsocket.RSocketSecurity.AuthorizePayloadsSpec.Access;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

import com.josemaba.security.config.security.filter.JwtAuthenticationFilter;
import com.josemaba.security.config.security.handler.CustomAccessDeniedHandler;
import com.josemaba.security.config.security.handler.CustomAuthenticationEntryPoints;
import com.josemaba.security.persistence.util.RoleEnum;

@Configuration
@EnableWebSecurity
//@EnableMethodSecurity(prePostEnabled = true)
public class HttpSecurityConfig {

    @Autowired
    private AuthenticationProvider daoAuthProvider;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Bean
    SecurityFilterChain securituFilterChain(HttpSecurity http) throws Exception {
        SecurityFilterChain filterChain =  http
            .csrf( csrfConfig -> csrfConfig.disable())
            .sessionManagement(sessMagConfig -> sessMagConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) 
            .authenticationProvider(daoAuthProvider)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests(authReqConfig -> buildRequestMatchers(authReqConfig))
            .exceptionHandling(exceptionHandlingConfig -> {
                exceptionHandlingConfig.authenticationEntryPoint(authenticationEntryPoint);
                exceptionHandlingConfig.accessDeniedHandler(accessDeniedHandler);
            })
            .build();
        return filterChain;
    }

    //@SuppressWarnings("unused")
    private static void buildRequestMatchers(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authReqConfig) {

        // Autorizacion de endpoints de productos
        authReqConfig.requestMatchers(HttpMethod.GET,"/products")
            .hasAnyRole(RoleEnum.ADMINISTRATOR.name(), RoleEnum.ASSISTANT_ADMINISTRATOR.name());

        //authReqConfig.requestMatchers(HttpMethod.GET,"/products/{productId}")
        authReqConfig.requestMatchers(RegexRequestMatcher.regexMatcher(HttpMethod.GET,"/products/[0-9]*"))
            .hasAnyRole(RoleEnum.ADMINISTRATOR.name(), RoleEnum.ASSISTANT_ADMINISTRATOR.name());

        authReqConfig.requestMatchers(HttpMethod.POST,"/products")
            .hasRole(RoleEnum.ADMINISTRATOR.name());

        authReqConfig.requestMatchers(HttpMethod.PUT,"/products/{productId}")
            .hasAnyRole(RoleEnum.ADMINISTRATOR.name(), RoleEnum.ASSISTANT_ADMINISTRATOR.name());


        authReqConfig.requestMatchers(HttpMethod.PUT,"/products/{productId}/disabled")
            .hasRole(RoleEnum.ADMINISTRATOR.name());

        // Autorizacion de endpoints de categorias
        authReqConfig.requestMatchers(HttpMethod.GET,"/categories")
            .hasAnyRole(RoleEnum.ADMINISTRATOR.name(), RoleEnum.ASSISTANT_ADMINISTRATOR.name());

        authReqConfig.requestMatchers(HttpMethod.GET,"/categories/{categoryId}")
            .hasAnyRole(RoleEnum.ADMINISTRATOR.name(), RoleEnum.ASSISTANT_ADMINISTRATOR.name());

        authReqConfig.requestMatchers(HttpMethod.POST,"/categories")
            .hasRole(RoleEnum.ADMINISTRATOR.name());

        authReqConfig.requestMatchers(HttpMethod.PUT,"/categories/{categoryId}")
            .hasAnyRole(RoleEnum.ADMINISTRATOR.name(), RoleEnum.ASSISTANT_ADMINISTRATOR.name());

        authReqConfig.requestMatchers(HttpMethod.PUT,"/categories/{categoryId}/disabled")
            .hasRole(RoleEnum.ADMINISTRATOR.name());

        authReqConfig.requestMatchers(HttpMethod.PUT,"/auth/profile")
            .hasAnyRole(RoleEnum.ADMINISTRATOR.name(), RoleEnum.ASSISTANT_ADMINISTRATOR.name(), RoleEnum.CUSTOMER.name());
            
        // Autorizacion de endpoints publicos
        authReqConfig.requestMatchers(HttpMethod.POST,"/customers").permitAll();
        authReqConfig.requestMatchers(HttpMethod.POST,"/auth/authenticate").permitAll();
        authReqConfig.requestMatchers(HttpMethod.GET,"/auth/validate-token").permitAll();
        authReqConfig.anyRequest().authenticated();

    }
    
    @SuppressWarnings("unused")
    private static void buildRequestMatchersV2(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authReqConfig) {
            
        // Autorizacion de endpoints publicos
        authReqConfig.requestMatchers(HttpMethod.POST,"/customers").permitAll();
        authReqConfig.requestMatchers(HttpMethod.POST,"/auth/authenticate").permitAll();
        authReqConfig.requestMatchers(HttpMethod.GET,"/auth/validate-token").permitAll();
        authReqConfig.anyRequest().authenticated();

    }
}
