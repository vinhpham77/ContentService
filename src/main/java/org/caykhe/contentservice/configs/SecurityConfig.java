package org.caykhe.contentservice.configs;

import lombok.RequiredArgsConstructor;
import org.caykhe.contentservice.security.CustomAccessDeniedHandler;
import org.caykhe.contentservice.security.JwtAuthenticationEntryPoint;
import org.caykhe.contentservice.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter authenticationFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration corsConfiguration = new CorsConfiguration();
                    corsConfiguration.addAllowedOrigin("http://localhost:8000");
                    corsConfiguration.addAllowedMethod("*");
                    corsConfiguration.addAllowedHeader("*");
                    corsConfiguration.setAllowCredentials(true);
                    return corsConfiguration;
                }))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/auth/signup", "/auth/signin", "/auth/refresh-token").permitAll()
                        .requestMatchers("/users/{username}", "/users/stats/{username}", "/users/{username}/tags", "/users/{follower}/followings", "/users/{followed}/followers").permitAll()
                        .requestMatchers("/posts/get{query}", "/posts/postDetails/{id}",
                                "/posts/postsSameAuthor/{authorName}", "/posts/{id}", "/posts/number",
                                "/posts/by/{username}", "/posts/search", "posts/totalPost/{username}").permitAll()
                        .requestMatchers("/tags").permitAll()
                        .requestMatchers("/series/get", "/series/search", "/series/by/{username}", "/series/totalSeries/{username}", "/series/detail/{seriesId}", "/series/all", "/series/{id}").permitAll()
                        .requestMatchers("/votes/byPostId", "/votes", "/votes/{id}").permitAll()
                        .requestMatchers("/bookmarks/getPost{query}", "/bookmarks/getSeries{query}", "/bookmarks/{id}").permitAll()
                        .requestMatchers("/follows/{id}", "/follows/totalFollower/{followed}").permitAll()
                        .requestMatchers("/comments/{targetId}/{type}/get{query}").permitAll()
                        .requestMatchers("/images/{imageName}").permitAll()
                        .anyRequest().authenticated())

                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                )
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(customAccessDeniedHandler)
                )
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
